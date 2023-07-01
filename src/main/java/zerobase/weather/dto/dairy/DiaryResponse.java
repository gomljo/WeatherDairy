package zerobase.weather.dto.dairy;

import lombok.*;

import java.time.LocalDate;

public class DiaryResponse {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        private int id;
        private String weather;
        private String iconId;
        private Double temperature;
        private String text;
        private LocalDate recordDate;
        public static Response from(DiaryDto diaryDto) {
            return Response.builder()
                    .id(diaryDto.getId())
                    .temperature(diaryDto.getTemperature())
                    .weather(diaryDto.getWeather())
                    .iconId(diaryDto.getIconId())
                    .text(diaryDto.getText())
                    .recordDate(diaryDto.getRecordDate())
                    .build();
        }
    }

}
