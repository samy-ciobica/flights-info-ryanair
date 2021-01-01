package com.ryanair.flights.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
//@ConfigurationProperties(prefix = "app.service")
public class ApplicationConfiguration {

    @Value("${app.service.routesApiUrl}")
    private String routesApiUrl;

    @Value("${app.service.schedulesApiUrl}")
    private String schedulesApiUrl;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }


    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        return mapper;
    }

    public String getRoutesApiUrl() {
        return routesApiUrl;
    }

    public void setRoutesApiUrl(String routesApiUrl) {
        this.routesApiUrl = routesApiUrl;
    }

    public String getSchedulesApiUrl() {
        return schedulesApiUrl;
    }

    public void setSchedulesApiUrl(String schedulesApiUrl) {
        this.schedulesApiUrl = schedulesApiUrl;
    }

}