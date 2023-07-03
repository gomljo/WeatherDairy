package zerobase.weather.service;


import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import zerobase.weather.WeatherApplication;
import zerobase.weather.config.OpenApiUriRequestConfig;
import zerobase.weather.domain.VO.WeatherRecord;
import zerobase.weather.domain.entity.Diary;
import zerobase.weather.dto.dairy.DiaryChunkDto;
import zerobase.weather.dto.dairy.DiaryDto;
import zerobase.weather.exception.DiaryException;
import zerobase.weather.repository.DiaryRepository;
import zerobase.weather.repository.WeatherRecordRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static zerobase.weather.constant.JsonKey.*;
import static zerobase.weather.type.ErrorCode.DATE_NOT_FOUND;
import static zerobase.weather.type.ErrorCode.DIARY_TEXT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {
    private final WeatherRecordRepository weatherRecordRepository;
    private final OpenApiUriRequestConfig openApiUriRequestConfig;
    private final DiaryRepository diaryRepository;
    private static final Logger logger = LoggerFactory.getLogger(WeatherApplication.class);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public DiaryDto createDiary(LocalDate date, String diaryText) {
        logger.info("validate create diary API request parameters");
        validateCreateDiary(date, diaryText);

        logger.info("started to create diary");
        WeatherRecord currentWeatherRecord = getWeatherRecordFromApi(date);
        System.out.println(currentWeatherRecord);
        logger.info("end to create diary");
        return DiaryDto.fromEntity(
                diaryRepository.save(Diary.builder()
                        .weather(currentWeatherRecord.getWeather())
                        .icon(currentWeatherRecord.getIcon())
                        .temperature(
                                Double.parseDouble(
                                    currentWeatherRecord.getTemperature().toString()
                                )
                        )
                        .text(diaryText)
                        .date(currentWeatherRecord.getDate())
                        .build())
        );
    }
    private void validateDate(LocalDate date){
        if(date==null){
            logger.info("DATE_NOT_FOUND type exception occurred");
            throw new DiaryException(DATE_NOT_FOUND);
        }
    }
    private void validateDiaryText(String text){
        if(text==null){
            logger.info("DiaryException type exception occurred");
            throw new DiaryException(DIARY_TEXT_NOT_FOUND);
        }
    }

    private void validateCreateDiary(LocalDate date, String text){

       validateDate(date);
       validateDiaryText(text);
    }

    @Transactional(readOnly = true)
    public DiaryChunkDto readDiary(LocalDate date){
        logger.info("validate read diary API request parameters");
        validateReadDiary(date);
        return DiaryChunkDto.fromEntity(diaryRepository.findByDate(date));
    }

    private void validateReadDiary(LocalDate date){
       validateDate(date);
    }

    private void validateReadDiaries(LocalDate start, LocalDate end){
        validateDate(start);
        validateReadDiary(end);
    }

    @Transactional(readOnly = true)
    public DiaryChunkDto readDiaries(LocalDate start, LocalDate end){
        logger.info("validate create diary API request parameters");
        validateReadDiaries(start, end);
        return DiaryChunkDto.fromEntity(diaryRepository.findByDateBetween(start, end));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Scheduled(cron = "0 0 1 * * *")
    public void saveWeatherRecord() {
        String weatherString = getWeatherString();
        Map<String, Object> weatherMap = parseWeather(weatherString);

        weatherRecordRepository.save(
                WeatherRecord.builder()
                        .date(LocalDate.now())
                        .weather(weatherMap.get(MAIN.getJsonKey()).toString())
                        .icon(weatherMap.get(ICON.getJsonKey()).toString())
                        .temperature((Double) weatherMap.get(TEMP.getJsonKey()))
                        .build());
    }
    private void validateUpdateDiary(LocalDate date, String newDiaryText){
        validateDate(date);
        validateDiaryText(newDiaryText);
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateDiary(LocalDate date, String text) {
        logger.info("validate update diary API request parameters");
        validateUpdateDiary(date, text);
        logger.info("started to update diary");
        Diary firstDiaryByDate = diaryRepository.findFirstByDate(date);
        diaryRepository.save(
                Diary.builder()
                        .id(firstDiaryByDate.getId())
                        .weather(firstDiaryByDate.getWeather())
                        .temperature(firstDiaryByDate.getTemperature())
                        .date(firstDiaryByDate.getDate())
                        .icon(firstDiaryByDate.getIcon())
                        .text(text)
                        .build()
        );
        logger.info("end to update diary");
    }

    private void validateDeleteDiary(LocalDate date){
        validateDate(date);
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteDiary(LocalDate date) {
        logger.info("validate create diary API request parameters");
        validateDeleteDiary(date);
        diaryRepository.deleteAllByDate(date);
    }

    public String getWeatherString() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(openApiUriRequestConfig.getURI(), String.class);
    }

    public Map<String, Object> mapping(JSONObject weatherJson) {
        Map<String, Object> weatherMap = new HashMap<>();

        JSONObject mainData = (JSONObject) weatherJson.get(MAIN.getJsonKey());
        weatherMap.put(TEMP.getJsonKey(), mainData.get(TEMP.getJsonKey()));

        JSONArray weatherDataArray = (JSONArray) weatherJson.get(WEATHER.getJsonKey());
        JSONObject weatherData = (JSONObject) weatherDataArray.get(0);
        weatherMap.put(MAIN.getJsonKey(), weatherData.get(MAIN.getJsonKey()));
        weatherMap.put(ICON.getJsonKey(), weatherData.get(ICON.getJsonKey()));
        return weatherMap;
    }

    public Map<String, Object> parseWeather(String jsonString) {
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return mapping(jsonObject);
    }

    private WeatherRecord getWeatherRecordFromApi(LocalDate date){
        WeatherRecord weatherRecord = weatherRecordRepository.findByDate(date);

        if(weatherRecord==null){
            saveWeatherRecord();
            return weatherRecordRepository.findByDate(date);
        }
        return weatherRecord;
    }



}