package izi.blog.retryservice.service;

import org.springframework.web.client.RestTemplate;

@org.springframework.stereotype.Service
public class Service {
    private final RestTemplate restTemplate = new RestTemplate();

    public String makeCall() {
        try {
            return restTemplate.getForObject("http://dependency-service:8080/data", String.class);
        } catch (Exception e) {
            System.out.println("Exception occurred");
            throw e;
        }
    }
}
