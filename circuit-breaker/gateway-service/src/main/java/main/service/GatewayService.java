package main.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GatewayService {

    @CircuitBreaker(name = "dataService1", fallbackMethod = "fallbackService1")
    public String fetchFromService1(RestTemplate restTemplate) {
        return restTemplate.getForObject("http://data-service-1:8080/data", String.class);
    }

    public String fallbackService1(Exception e) {
        return "Service 1 is unavailable (fallback)";
    }

}
