package mcizi.blog.gateway;

import io.github.resilience4j.bulkhead.BulkheadRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class PrintService {
    private final HashMap<Long, Integer> requests = new HashMap<>();
    private final HashSet<Long> finishedRequests = new HashSet<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void startScheduler() {
        scheduler.scheduleAtFixedRate(this::flush, 1, 1, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(this::header, 0, 10, TimeUnit.SECONDS);
    }

    public void printRequest(long id, int i) {
        requests.put(id, i);
    }

    public void printResponse(long id) {
        requests.remove(id);
        finishedRequests.add(id);
    }

    public void header() {
        Map<Thread, StackTraceElement[]> allThreads = Thread.getAllStackTraces();
        StringBuilder output = new StringBuilder("|");

        for (Thread thread : allThreads.keySet()) {
            output.append(thread.getId()).append("|");
        }

        System.out.println(output);

    }
    public void flush() {
        StringBuilder output = new StringBuilder("|");
        Map<Thread, StackTraceElement[]> allThreads = Thread.getAllStackTraces();
        for (Thread thread : allThreads.keySet()) {
            output.append(isProcessing(thread.getId())).append("|");
        }

        output.append("      Finished requests: ").append(finishedRequests);
        finishedRequests.clear();
        System.out.println(output);
    }


    private char isProcessing(long id) {
        if (requests.containsKey(id)) {
            return (char) (requests.get(id) + 48);
        }else {
            return ' ';
        }
    }

}
