package mcizi.blog.workerservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkerServiceController {

    @Value("${wait-time-seconds}")
    private int waitTimeSeconds;

    @GetMapping("/wait")
    public String waitAndReturn() throws InterruptedException {
        Thread.sleep(waitTimeSeconds * 1000L);
        return "Waited for " + waitTimeSeconds + " seconds";
    }
}