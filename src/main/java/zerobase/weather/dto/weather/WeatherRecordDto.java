package zerobase.weather.dto.weather;

import lombok.*;
import zerobase.weather.domain.VO.WeatherRecord;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherRecordDto {

    private String weather;
    private String iconId;
    private Double temperature;

    public static WeatherRecordDto fromEntity(WeatherRecord weatherRecord){
        return WeatherRecordDto.builder()
                .weather(weatherRecord.getWeather())
                .iconId(weatherRecord.getIcon())
                .temperature(weatherRecord.getTemperature())
                .build();
    }
}
