package izi.blog.dependencyservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class DataController1 {
    private static int attempt = 0;

    @GetMapping("/data")
    public String getData() {
        attempt++;
        if (!isPrime(attempt)) {
            throw new RuntimeException("Service is temporarily unavailable");
        }

        return "Success from Service B! Count: " + attempt;
    }

    private boolean isPrime(int n)
    {
        if (n <= 1)
            return false;

        if (n == 2 || n == 3)
            return true;

        if (n % 2 == 0 || n % 3 == 0)
            return false;

        for (int i = 5; i <= Math.sqrt(n); i = i + 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;

        return true;
    }
}