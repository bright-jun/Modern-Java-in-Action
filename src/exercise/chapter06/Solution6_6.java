package exercise.chapter06;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.partitioningBy;

import exercise.chapter04.Dish;
import exercise.chapter04.Dish.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution6_6 {

    public static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot)
            .noneMatch(i -> candidate % i == 0);
    }

    public static boolean isPrime(List<Integer> primes, int candidate) {
        return primes.stream().noneMatch(i -> candidate % i == 0);
    }

    public static boolean isPrimeOpt(List<Integer> primes, int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return primes.stream()
            .takeWhile(i -> i <= candidateRoot)
            .noneMatch(i -> candidate % i == 0);
    }

    public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed()
            .collect(partitioningBy(candidate -> isPrime(candidate)));
    }

    public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector1(int n) {
        return IntStream.rangeClosed(2, n).boxed()
            .collect(new PrimeNumbersCollector());
    }

    public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector2(int n) {
        return IntStream.rangeClosed(2, n).boxed()
            .collect(
                () -> new HashMap<Boolean, List<Integer>>() {{
                    put(true, new ArrayList<Integer>());
                    put(false, new ArrayList<Integer>());
                }},
                (acc, candidate) -> {
                    acc.get(isPrime(acc.get(true), candidate))
                        .add(candidate);
                },
                (map1, map2) -> {
                    map1.get(true).addAll(map2.get(true));
                    map1.get(false).addAll(map2.get(false));
                });
    }

    public static void main(String[] args) {
        Solution6_6 solution6_6 = new Solution6_6();

        List<Dish> menu = asList(
            new Dish("pork", false, 800, Type.MEAT),
            new Dish("beef", false, 700, Type.MEAT),
            new Dish("chicken", false, 400, Type.MEAT),
            new Dish("french fries", true, 530, Type.OTHER),
            new Dish("rice", true, 350, Type.OTHER),
            new Dish("season fruit", true, 120, Type.OTHER),
            new Dish("pizza", true, 550, Type.OTHER),
            new Dish("prawns", false, 300, Type.FISH),
            new Dish("salmon", false, 450, Type.FISH));

        Map<Boolean, List<Integer>> solution1 = solution6_6.partitionPrimes(100);
        // TODO 중간 결과 리스트를 어떻게 전달한다는 것? takeWhile 결과가 중간결과 리스트?
        boolean solution2 = solution6_6.isPrimeOpt(
            IntStream.rangeClosed(2, 100).boxed().collect(Collectors.toList()), 100);

        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            partitionPrimes(1000000);
            long duration = (System.nanoTime() - start) / 1_000_000;
            if (duration < fastest) {
                fastest = duration;
            }
        }
        System.out.println(
            "Fastest execution done in " + fastest + " msecs");

        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            partitionPrimesWithCustomCollector1(1000000);
            long duration = (System.nanoTime() - start) / 1_000_000;
            if (duration < fastest) {
                fastest = duration;
            }
        }
        System.out.println(
            "Fastest execution done in " + fastest + " msecs");

        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            // TODO 이경우는 잘 동작을 안하는 것 같다...?
            partitionPrimesWithCustomCollector2(10000);
            long duration = (System.nanoTime() - start) / 1_000_000;
            if (duration < fastest) {
                fastest = duration;
            }
        }
        System.out.println(
            "Fastest execution done in " + fastest + " msecs");

        return;
    }
}
