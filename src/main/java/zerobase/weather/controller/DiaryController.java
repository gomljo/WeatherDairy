package zerobase.weather.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zerobase.weather.dto.dairy.DiaryResponse;
import zerobase.weather.service.DiaryService;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping("/diary/create")
    public DiaryResponse.Response createDiary(
        @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestBody String diaryText
    )
    {
        return DiaryResponse.Response.from(
                diaryService.createDiary(date, diaryText)
        );
    }
}
