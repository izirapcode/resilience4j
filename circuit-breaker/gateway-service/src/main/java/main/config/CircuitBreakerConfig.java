package main.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CircuitBreakerConfig {
    public CircuitBreakerConfig(CircuitBreakerRegistry circuitBreakerRegistry) {
        addStateChangeListener(circuitBreakerRegistry.circuitBreaker("dataService1"));
    }

    private void addStateChangeListener(CircuitBreaker circuitBreaker) {
        System.out.println("Adding state change listener to circuit breaker");
        circuitBreaker.getEventPublisher()
                .onStateTransition(this::logStateTransition);
    }

    private void logStateTransition(CircuitBreakerOnStateTransitionEvent event) {
        System.out.println("CircuitBreaker '" + event.getCircuitBreakerName() + "' changed state from "
                + event.getStateTransition().getFromState() + " to " + event.getStateTransition().getToState());
    }


}