package zerobase.weather.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum JsonKey {
    MAIN("main"),
    TEMP("temp"),
    WEATHER("weather"),
    ICON("icon");

    private final String jsonKey;

}
