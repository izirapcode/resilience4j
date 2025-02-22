package izi.blog.ratelimiter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Counter {
    private static int counter = 0;

    public static void doSomething() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("[" + timestamp + "] Doing something " + counter++);
    }

    public static void main(String[] args) {
        doSomething();
    }
}
