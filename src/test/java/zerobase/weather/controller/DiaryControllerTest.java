package zerobase.weather.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.dto.dairy.DiaryChunkDto;
import zerobase.weather.dto.dairy.DiaryDto;
import zerobase.weather.dto.dairy.DiaryResponse;
import zerobase.weather.exception.DiaryException;
import zerobase.weather.type.ErrorCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static zerobase.weather.type.ErrorCode.DATE_NOT_FOUND;
import static zerobase.weather.type.ErrorCode.DIARY_TEXT_NOT_FOUND;

@SpringBootTest
@Transactional
class DiaryControllerTest {


    @Autowired
    DiaryController diaryController;
    @Test
    void successCreateDiary() {
        // given
        String diaryText = "fist dairy memo";
        LocalDate currentDate = LocalDate.now();
        // when
        DiaryResponse.Response diary1 = diaryController.createDiary(currentDate, diaryText);
        // then
        assertEquals(diary1.getText(), diaryText);
        assertEquals(diary1.getRecordDate(), currentDate);

    }

    @Test
    @DisplayName("일기 내용이 없다 - 날씨 일기 생성 실패 ")
    void failCreateDiary_NotFoundDiaryText() {
        // given
        String diaryText = null;
        LocalDate currentDate = LocalDate.now();
        // when
        DiaryException diaryException = assertThrows(DiaryException.class, () -> diaryController.createDiary(currentDate, diaryText));
        // then
        assertEquals(diaryException.getErrorCode(), DIARY_TEXT_NOT_FOUND);

    }

    @Test
    void failCreateDiary_NotFoundDate() {
        // given
        String diaryText = "fist dairy memo";
        LocalDate currentDate = null;
        // when
        DiaryException diaryException = assertThrows(DiaryException.class, () -> diaryController.createDiary(currentDate, diaryText));
        // then
        assertEquals(diaryException.getErrorCode(), DATE_NOT_FOUND);
    }

    @Test
    void successReadDiary() {
        // given
        String diaryText = "fist dairy memo";
        LocalDate currentDate = LocalDate.now();
        int numOfDiary = 3;
        for(int i=0; i< numOfDiary; i++){
            diaryController.createDiary(currentDate, diaryText);
        }
        // when
        DiaryChunkDto diaryChunkDto = diaryController.readDiary(currentDate);
        List<DiaryDto> diaryChunk = diaryChunkDto.getDiaryChunk();
        // then
        assertEquals(diaryChunk.size(), numOfDiary);
        for (int i=0; i< numOfDiary; i++){
            assertEquals(diaryChunk.get(i).getRecordDate(), currentDate);
        }
    }

    @Test
    void failReadDiary() {
        // given
        String diaryText = "fist dairy memo";
        LocalDate currentDate = LocalDate.now().minusDays(1);
        int numOfDiary = 1;
        for(int i=0; i< numOfDiary; i++){
            diaryController.createDiary(currentDate, diaryText);
        }
        // when
        DiaryChunkDto diaryChunkDto = diaryController.readDiary(LocalDate.now());
        List<DiaryDto> diaryChunk = diaryChunkDto.getDiaryChunk();
        // then
        assertEquals(diaryChunk.size(), 0);
    }

    @Test
    void successReadDiaries() {

        // given
        String diaryText = "diary";
        int[] numOfDiaryPerDay = {1,2,3};
        for (int lapse = 0; lapse < 3; lapse++) {
            for (int i = 0; i < numOfDiaryPerDay[lapse]; i++) {
                diaryController.createDiary(LocalDate.now().minusDays(lapse), diaryText);
            }
        }

        // when
        DiaryChunkDto diaryChunkDto = diaryController.readDiaryBetween(LocalDate.now().minusDays(2), LocalDate.now());
        List<DiaryDto> diaryDtos = diaryChunkDto.getDiaryChunk();
        // then
        System.out.println(diaryDtos);

    }


}