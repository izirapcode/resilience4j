package izi.blog.retryservice.controller;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import izi.blog.retryservice.service.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Duration;
import java.util.function.Supplier;

@RestController
public class RetryController {
    private final Service service;
    public RetryController(Service service) {
        this.service = service;
    }

    @GetMapping("/fetch-data")
    public String fetchData() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(2))
                .build();

        RetryRegistry registry = RetryRegistry.of(config);
        Retry retry = registry.retry("dependencyService");

        Supplier<String> retryableCall = Retry.decorateSupplier(retry, service::makeCall);

        String serviceResponse = retryableCall.get();
        System.out.println(serviceResponse);
        return serviceResponse;
    }
}
