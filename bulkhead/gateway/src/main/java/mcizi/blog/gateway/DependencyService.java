package mcizi.blog.gateway;

import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Service
public class DependencyService {
    private final RestTemplate restTemplate;
    private final io.github.resilience4j.bulkhead.Bulkhead worker1Bulkhead;
    private final io.github.resilience4j.bulkhead.Bulkhead worker2Bulkhead;
    private final io.github.resilience4j.bulkhead.Bulkhead worker3Bulkhead;
    private final BulkheadRegistry bulkheadRegistry;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final PrintService printService;

    DependencyService(RestTemplate restTemplate, PrintService printService) {
        this.restTemplate = restTemplate;
        this.printService = printService;
        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(2)
                .maxWaitDuration(Duration.ofSeconds(30))
                .build();

        this.bulkheadRegistry = BulkheadRegistry.of(config);
        this.worker1Bulkhead = bulkheadRegistry.bulkhead("worker1");
        this.worker2Bulkhead = bulkheadRegistry.bulkhead("worker2");
        this.worker3Bulkhead = bulkheadRegistry.bulkhead("worker3");
        //scheduler.scheduleAtFixedRate(this::printBulkheadStatus, 0, 1, TimeUnit.SECONDS);
    }

    public String requestWorker1() {
        Supplier<String> worker1Supplier = () -> {
            printService.printRequest(Thread.currentThread().getId(), 1);
            return restTemplate.getForObject("http://worker-service-1:8080/wait", String.class);
        };
        Supplier<String> decoratedWorker1Supplier = io.github.resilience4j.bulkhead.Bulkhead.decorateSupplier(worker1Bulkhead, worker1Supplier);
        return decoratedWorker1Supplier.get();
    }

    public String requestWorker2() {
        Supplier<String> worker2Supplier = () -> {
            printService.printRequest(Thread.currentThread().getId(), 2);
            return restTemplate.getForObject("http://worker-service-2:8080/wait", String.class);
        };
        Supplier<String> decoratedWorker2Supplier = io.github.resilience4j.bulkhead.Bulkhead.decorateSupplier(worker2Bulkhead, worker2Supplier);
        return decoratedWorker2Supplier.get();
    }

    public String requestWorker3() {
        Supplier<String> worker3Supplier = () -> {
            printService.printRequest(Thread.currentThread().getId(), 3);
            return restTemplate.getForObject("http://worker-service-3:8080/wait", String.class);
        };
        Supplier<String> decoratedWorker3Supplier = Bulkhead.decorateSupplier(worker3Bulkhead, worker3Supplier);
        return decoratedWorker3Supplier.get();
    }

    private void printBulkheadStatus() {
        bulkheadRegistry.getAllBulkheads().forEach(bulkhead -> {
            int availableCalls = bulkhead.getMetrics().getAvailableConcurrentCalls();
            System.out.println("Bulkhead " + bulkhead.getName() + " has " + availableCalls + " threads left.");
        });
    }
}
