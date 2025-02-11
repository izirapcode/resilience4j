package izi.blog.ratelimiter;

public class Counter {
    private static int counter = 0;

    public static void doSomething() {
        System.out.println("Doing something " + counter++);
    }
}
