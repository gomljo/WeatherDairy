package zerobase.weather.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class OpenApiUriRequestConfigTest {
    ApplicationContext context = new AnnotationConfigApplicationContext(OpenApiUriRequestConfig.class);
    OpenApiUriRequestConfig openApiUriRequestConfig = context.getBean(OpenApiUriRequestConfig.class);

    @Test
    void successGetProperties() {
        // given

        // when

        // then
        assertNotNull(openApiUriRequestConfig.getURI());
    }

}