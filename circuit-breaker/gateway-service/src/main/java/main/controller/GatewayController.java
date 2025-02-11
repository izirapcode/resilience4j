package main.controller;

import main.service.GatewayService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final GatewayService gatewayService;

    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @GetMapping("/fetch-data")
    public String fetchData() {
        String service1Response = gatewayService.fetchFromService1(restTemplate);
        String service2Response = restTemplate.getForObject("http://data-service-2:8080/data", String.class);
        System.out.println("Service 1: " + service1Response + " | Service 2: " + service2Response);
        return "Service 1: " + service1Response + " | Service 2: " + service2Response;
    }
}
