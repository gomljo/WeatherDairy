package zerobase.weather.service;


import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import zerobase.weather.config.OpenApiUriRequestConfig;
import zerobase.weather.domain.VO.WeatherRecord;
import zerobase.weather.repository.WeatherRecordRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static zerobase.weather.constant.JsonKey.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {
    private final WeatherRecordRepository weatherRecordRepository;
    private final OpenApiUriRequestConfig openApiUriRequestConfig;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Scheduled(cron = "0 0 1 * * *")
    public void saveWeatherRecord(){
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

    public String getWeatherString(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(openApiUriRequestConfig.getURI(), String.class);
    }

    public Map<String, Object> mapping(JSONObject weatherJson){
        Map<String, Object> weatherMap = new HashMap<>();

        JSONObject mainData = (JSONObject) weatherJson.get(MAIN.getJsonKey());
        weatherMap.put(TEMP.getJsonKey(), mainData.get(TEMP.getJsonKey()));

        JSONArray weatherDataArray = (JSONArray) weatherJson.get(WEATHER.getJsonKey());
        JSONObject weatherData = (JSONObject) weatherDataArray.get(0);
        weatherMap.put(MAIN.getJsonKey(), weatherData.get(MAIN.getJsonKey()));
        weatherMap.put(ICON.getJsonKey(), weatherData.get(ICON.getJsonKey()));
        System.out.println(weatherMap);
        return weatherMap;
    }

    public Map<String, Object> parseWeather(String jsonString){
        JSONObject jsonObject;
        JSONParser jsonParser = new JSONParser();

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return mapping(jsonObject);
    }
}