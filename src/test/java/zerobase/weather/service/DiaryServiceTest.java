package zerobase.weather.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.domain.VO.WeatherRecord;
import zerobase.weather.dto.dairy.DiaryDto;
import zerobase.weather.repository.WeatherRecordRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@Transactional
class DiaryServiceTest {

    @Autowired
    private DiaryService diaryService;
    @Autowired
    private WeatherRecordRepository weatherRecordRepository;

    @Test
    void successOpenApiRequest() {
        // given

        // when
        String weatherString = diaryService.getWeatherString();
        // then
        assertNotNull(weatherString);

    }

    @Test
    void successSaveWeatherRecord() {
        // given
        // when
        diaryService.saveWeatherRecord();
        WeatherRecord weatherRecord = weatherRecordRepository.findByDate(LocalDate.now());
        // then
        assertEquals(weatherRecord.getDate(), LocalDate.now());
    }

    @Test
    @DisplayName("날씨 일기 내용과 날짜가 모두 있는 경우 - 저장 성공")
    void successCreateDiary() {
        // given
        diaryService.saveWeatherRecord();
        String diaryText = "fist dairy memo";
        LocalDate currentDate = LocalDate.now();
        // when
        DiaryDto diaryDto = diaryService.createDiary(currentDate, diaryText);
        // then
        assertEquals(diaryDto.getText(), diaryText);
        assertEquals(diaryDto.getRecordDate(), currentDate);

    }

    @Test
    @DisplayName("날씨 일기 내용만 있고 날짜 정보가 없는 경우 - 저장 실패")
    void failCreateDiary() {
        // given
        String diaryText = "fist dairy memo";
        LocalDate currentDate = LocalDate.now();
        // when
        DiaryDto diaryDto = diaryService.createDiary(currentDate, diaryText);
        // then
        assertEquals(diaryDto.getText(), diaryText);
        assertEquals(diaryDto.getRecordDate(), currentDate);
    }

    @Test
    @DisplayName("새벽 1시에 날씨 정보 업데이트 - 성공")
    void successUpdateWeatherData() {
        // given

        // when

        // then
    }


}