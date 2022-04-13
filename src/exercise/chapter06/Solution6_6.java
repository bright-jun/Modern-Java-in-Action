package exercise.chapter06;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.partitioningBy;

import exercise.chapter04.Dish;
import exercise.chapter04.Dish.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution6_6 {

    public boolean isPrime(int candidate) {
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

    public Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed()
            .collect(partitioningBy(candidate -> isPrime(candidate)));
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
        boolean solution2 = solution6_6.isPrimeOpt(IntStream.rangeClosed(2, 100).boxed().collect(Collectors.toList()), 100);

        return;
    }
}
