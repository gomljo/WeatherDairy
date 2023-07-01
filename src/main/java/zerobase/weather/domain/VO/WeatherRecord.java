package zerobase.weather.domain.VO;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "weather_record")
public class WeatherRecord {

    @Id
    private LocalDate date;

    private String weather;

    private String icon;
    private Double temperature;

    @Override
    public String toString() {
        return "WeatherRecord{" +
                "date=" + date +
                ", weather='" + weather + '\'' +
                ", icon='" + icon + '\'' +
                ", temperature=" + temperature +
                '}';
    }
}
