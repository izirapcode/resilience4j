package mcizi.blog.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private DependencyService dependencyService;
    private PrintService printService;


    Controller(DependencyService dependencyService, PrintService printService) {
        this.dependencyService = dependencyService;
        this.printService = printService;
    }

    @GetMapping("/request-worker-1")
    public String requestWorker1() {
        String result = dependencyService.requestWorker1();
        printService.printResponse(Thread.currentThread().getId());
        return result;
    }

    @GetMapping("/request-worker-2")
    public String requestWorker2() {
        String result = dependencyService.requestWorker2();
        printService.printResponse(Thread.currentThread().getId());
        return result;
    }

    @GetMapping("/request-worker-3")
    public String requestWorker3() {
        String result = dependencyService.requestWorker3();
        printService.printResponse(Thread.currentThread().getId());
        return result;
    }
}
