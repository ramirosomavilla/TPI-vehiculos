package serviciopruebas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;

@Configuration
public class RestTemplateConfig {
    @Value("${internal.token}")
    private String internalToken;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            request.getHeaders().add("X-Internal-Token", internalToken);
            return execution.execute(request, body);
        };
        restTemplate.setInterceptors(Collections.singletonList(interceptor));
        return restTemplate;
    }
}