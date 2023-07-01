package zerobase.weather.dto.dairy;

import lombok.*;
import zerobase.weather.domain.entity.Diary;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryDto {
    private int id;
    private String weather;
    private String iconId;
    private Double temperature;
    private String text;
    private LocalDate recordDate;

    public static DiaryDto fromEntity(Diary diary){
        return DiaryDto.builder()
                .id(diary.getId())
                .weather(diary.getWeather())
                .iconId(diary.getIcon())
                .temperature(diary.getTemperature())
                .text(diary.getText())
                .recordDate(diary.getDate())
                .build();
    }
}
