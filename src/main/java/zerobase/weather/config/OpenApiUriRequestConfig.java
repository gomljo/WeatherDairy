package zerobase.weather.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiUriRequestConfig {

    @Value("${openweathermap.siteurl}")
    private String siteUrl;

    @Value("${openweathermap.queryParamCity}")
    private String queryParamCity;

    @Value("${openweathermap.queryParamApiKey}")
    private String queryParamApikey;

    public String getURI(){
        return siteUrl+queryParamCity+queryParamApikey;
    }
}
