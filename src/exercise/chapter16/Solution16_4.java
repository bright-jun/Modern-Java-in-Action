package exercise.chapter16;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Solution16_4 {

    public static Random random = new Random();

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    List<Shop> shops = List.of(new Shop("BestPrice"), new Shop("LetsSaveBig"),
        new Shop("MyFavoriteShop"), new Shop("BuyItAll"));

    public List<String> findPrices1(String product) {
        return shops.stream()
            // 각 상점에서 할인 전 가격 얻기. 5s
            .map(shop -> shop.getPrice(product))
            // 상점에서 반환한 문자열을 Quote 객체로 변환한다
            .map(Quote::parse)
            // Discount 서비스를 이용해서 각 Quote에 할인을 적용한다 5s
            .map(Discount::applyDiscount).collect(toList());
    }

    public List<String> findPrices2(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                // customExcutor 정의해서 수행 태스크 설정 -> CPU 사용 극대화
            .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                // thenApply
            .map(future -> future.thenApply(Quote::parse))
                // thenCompose
            .map(future -> future.thenCompose(
                quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote),
                    executor)))
            .collect(toList());
        return priceFutures.stream().map(CompletableFuture::join).collect(toList());
    }

    public class Shop {

        private String name;

        public Shop(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

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

    private final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
        (Runnable r) -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });

    public static void main(String[] args) {
        Solution16_4 sol = new Solution16_4();
        long start = System.nanoTime();
        System.out.println(sol.findPrices1("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
        // findPrice
        //
    }
}
