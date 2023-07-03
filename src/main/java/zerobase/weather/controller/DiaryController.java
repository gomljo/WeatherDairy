package zerobase.weather.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import zerobase.weather.dto.dairy.DiaryChunkDto;
import zerobase.weather.dto.dairy.DiaryResponse;
import zerobase.weather.service.DiaryService;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;
    @ApiOperation(value = "날짜와 일기를 받아서 DB에 저장", notes = "날짜 형식에 유의해주세요, 일기 값은 한 글자라도 있어야 합니다.")
    @PostMapping("/create/diary")
    DiaryResponse.Response createDiary(
        @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) @ApiParam(value = "날짜 yyyy-mm-dd", example = "2023-07-03") LocalDate date,
        @RequestBody @ApiParam(value = "일기", example = "첫 번째 일기") String diaryText
    )
    {
        return DiaryResponse.Response.from(
                diaryService.createDiary(date, diaryText)
        );
    }
    @ApiOperation(value = "특정 날짜의 날씨 일기를 DB에서 조회", notes = "날짜 형식에 유의해주세요")
    @GetMapping("/read/diary")
    DiaryChunkDto readDiary(@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
                            @ApiParam(value = "날짜 yyyy-mm-dd", example = "2023-07-03")
                            LocalDate date
    )
    {
        return diaryService.readDiary(date);
    }
    @ApiOperation(value = "특정 기간의 날씨 일기들을 DB에서 조회", notes = "날짜 형식에 유의해주세요")
    @GetMapping("/read/diaries")
    DiaryChunkDto readDiaryBetween(
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
            @ApiParam(value = "조회 기간 중 시작 날짜 yyyy-mm-dd", example = "2023-07-03") LocalDate start,
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
            @ApiParam(value = "조회 기간 중 종료 날짜 yyyy-mm-dd", example = "2023-07-10") LocalDate end
    )
    {
        return diaryService.readDiaries(start, end);
    }
    @ApiOperation(value = "특정 날짜의 날씨 일기를 수정하여 DB에 반영", notes = "날짜 형식에 유의해주세요, 일기 값은 한 글자라도 있어야 합니다.")
    @PutMapping("/diary/update")
    void updateDiary(
        @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
        @ApiParam(value = "날짜 yyyy-mm-dd", example = "2023-07-03")
        LocalDate date,
        @RequestBody String text
    )
    {
        diaryService.updateDiary(date,text);
    }
    @ApiOperation(value = "특정 날짜의 날씨를 DB에서 삭제", notes = "날짜 형식에 유의해주세요")
    @DeleteMapping("/delete/diary")
    void deleteDiary(@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
                     @ApiParam(value = "날짜 yyyy-mm-dd", example = "2023-07-03")
                     LocalDate date)
    {
        diaryService.deleteDiary(date);
    }

}
