package zerobase.weather.exception;

import lombok.*;
import zerobase.weather.type.ErrorCode;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DiaryException extends RuntimeException{

    private final ErrorCode errorCode;
    private final String description;

    public DiaryException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }

}
