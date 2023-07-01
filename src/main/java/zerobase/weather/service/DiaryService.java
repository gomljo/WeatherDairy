package zerobase.weather.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import zerobase.weather.config.OpenApiUriRequestConfig;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {
    private final OpenApiUriRequestConfig openApiUriRequestConfig;

    public String getWeatherString(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(openApiUriRequestConfig.getURI(), String.class);
    }

}