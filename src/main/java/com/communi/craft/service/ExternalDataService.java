package com.communi.craft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ExternalDataService
{
    private final WebClient webClient;
    private final String API_KEY = "123456789";

    public ExternalDataService()
    {
        this.webClient = WebClient.builder()
                .baseUrl("http://127.0.0.1:8081/external/api/v1")
                .build();
    }

    public String getExternalMaterials()
    {
        return webClient.get()
                .uri("/materials")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("API_KEY", API_KEY)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getExternalTools()
    {
        return webClient.get()
                .uri("/tools")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("API_KEY", API_KEY)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getExternalProjectIdeas()
    {
        return webClient.get()
                .uri("/project-ideas")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("API_KEY", API_KEY)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}