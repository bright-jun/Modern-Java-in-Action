package exercise.chapter16;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Solution16_2 {

    public void doSomethingElse() {
        System.out.println("doSomethingElse");
    }

    public static Random random = new Random();

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public class Shop {

        public double getPrice(String product) {
            return calculatePrice(product);
        }

        private double calculatePrice(String product) {
            delay();
            return random.nextDouble() * product.charAt(0) + product.charAt(1);
        }

        public Future<Double> getPriceAsync(String product) {
            // 계산 결과를 포함할 CompletableFuture 를 생성한다.
            CompletableFuture<Double> futurePrice = new CompletableFuture<>();
            new Thread(() -> {
                // 다른 스레드에서 비동기적으로 계산을 수행한다
                double price = calculatePrice(product);
                // 오랜 시간이 걸리는 계산이 완료되면 Future 에 값을 설정한다
                futurePrice.complete(price);
            }).start();
            // 계산 결과가 완료되길 기다리지않고 Future 를 반환한다
            return futurePrice;
        }
    }

    public static void main(String[] args) {
        Solution16_2 sol = new Solution16_2();
        Shop shop = sol.new Shop();
        long start = System.nanoTime();
        // 상점에 제품가격 정보요청
        // 가격 계산이 끝나기 전에 getPriceAsync가 반환
        Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime + " msecs");
        // Do some more tasks, like querying other shops
        sol.doSomethingElse();
        // while the price of the product is being calculated
        try {
            // 가격 정보가 있으면 Future에서 가격 정보를 읽고, 가격 정보가 없으면 가격 정보를 받을 때까지 블록한다.
            double price = futurePrice.get();
            System.out.printf("Price is %.2f%n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");
    }
}
