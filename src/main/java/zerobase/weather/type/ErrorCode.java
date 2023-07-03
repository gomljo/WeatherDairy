package zerobase.weather.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    DATE_NOT_FOUND("요청에 날짜가 누락되었습니다."),
    DIARY_TEXT_NOT_FOUND("일기의 내용이 없습니다");

    private final String description;
}
