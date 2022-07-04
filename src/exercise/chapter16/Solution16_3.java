package exercise.chapter16;

import static java.util.stream.Collectors.toList;

import exercise.chapter16.Solution16_2.Shop;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class Solution16_3 {

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

    public List<String> findPrices1(String product) {
        return shops.stream()
            .map(shop -> String.format("%s price is %.2f",
                shop.getName(), shop.getPrice(product)))
            .collect(toList());
    }

    public List<String> findPrices2(String product) {
        return shops.parallelStream()
            .map(shop -> String.format("%s price is %.2f",
                shop.getName(), shop.getPrice(product)))
            .collect(toList());
    }

    public List<String> findPrices3(String product) {
        List<CompletableFuture<String>> priceFutures =
            shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                    () -> shop.getName() + " price is " +
                        shop.getPrice(product)))
                .collect(Collectors.toList());
        return priceFutures.stream()
            .map(CompletableFuture::join)
            .collect(toList());
    }

    List<Shop> shops = List.of(new Shop("BestPrice"),
        new Shop("LetsSaveBig"),
        new Shop("MyFavoriteShop"),
        new Shop("BuyItAll"));

    public static void main(String[] args) {
        Solution16_3 sol = new Solution16_3();
        long start = System.nanoTime();
        System.out.println(sol.findPrices1("myPhone27S"));
//        System.out.println(sol.findPrices2("myPhone27S"));
//        System.out.println(sol.findPrices3("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
        // findPrice1
        // [BestPrice price is 208.41, LetsSaveBig price is 191.42, MyFavoriteShop price is 229.01, BuyItAll price is 206.64]
        // Done in 4053 msecs
        // 네 개의 상점에서 가격을 검색하는 동안 각각 1초의 대기시간이 있으므로 전체 가격 검색 결과는 4초보다 조금 더 걸린다
        // findPrice2
        // [BestPrice price is 189.94, LetsSaveBig price is 126.77, MyFavoriteShop price is 151.80, BuyItAll price is 204.15]
        // Done in 1056 msecs
        // 네 개의 상점에서 병렬로 검색이 진행되므로 1초 남짓의 시간에 검색이 완료
        // findPrice3
        // [BestPrice price is 163.70958051465024, LetsSaveBig price is 214.8930466182025, MyFavoriteShop price is 135.99150219166995, BuyItAll price is 179.77310211844315]
        // Done in 1093 msecs
        // TODO 책 결과 병렬 스트림을 사용한 구현보다는 두 배나 느리다 ?
        // [BestPrice price is 123.26, LetsSaveBig price is 169.47, MyFavoriteShop price is 214.13, BuyltAll price is 184.74］
        // Done in 2005 msecs
    }

    private final Executor executor =
        Executors.newFixedThreadPool(Math.min(shops.size(), 100),
            (Runnable r) -> {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        );
}
