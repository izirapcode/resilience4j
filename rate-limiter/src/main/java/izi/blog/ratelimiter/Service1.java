package izi.blog.ratelimiter;

import io.github.resilience4j.core.functions.CheckedRunnable;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;

import java.time.Duration;

@org.springframework.stereotype.Service
public class Service1 {
    private final RateLimiter rateLimiter;
    public Service1() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofSeconds(5))
                .limitForPeriod(10)
                .timeoutDuration(Duration.ofSeconds(50))
                .build();

        RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(config);

        this.rateLimiter = rateLimiterRegistry
                .rateLimiter("name2", config);


        CheckedRunnable restrictedCall = RateLimiter
                .decorateCheckedRunnable(rateLimiter, Counter::doSomething);

        for (int i = 0; i < 100; i++) {
            try {
                restrictedCall.run();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

/*        for (int i = 0; i < 100; i++) {
            try {
                Counter.doSomething();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }*/

    }
}
