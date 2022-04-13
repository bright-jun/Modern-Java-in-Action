package exercise.chapter06;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

import exercise.chapter04.Dish;
import exercise.chapter04.Dish.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public class Solution6_4 {

    public boolean isPrime(int candidate) {
        return IntStream.range(2, candidate)
            .noneMatch(i -> candidate % i == 0);
    }

    public boolean isPrimeOpt(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot)
            .noneMatch(i -> candidate % i == 0);
    }

    public Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed()
            .collect(
                partitioningBy(candidate -> isPrime(candidate)));
    }

    public static void main(String[] args) {
        Solution6_4 solution6_4 = new Solution6_4();

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

        Map<Boolean, List<Dish>> partitionedMenu =
            menu.stream()
                .collect(partitioningBy(Dish::isVegetarian));

        List<Dish> vegetarianDishes1 = partitionedMenu.get(true);

        List<Dish> vegetarianDishes2 =
            menu.stream()
                .filter(Dish::isVegetarian)
                .collect(toList());

        Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType =
            menu.stream().collect(
                partitioningBy(Dish::isVegetarian,
                    groupingBy(Dish::getType)));

        Map<Boolean, Dish> mostCaloricPartitionedByVegetarian =
            menu.stream().collect(
                partitioningBy(Dish::isVegetarian,
                    collectingAndThen(maxBy(comparingInt(Dish::getCalories)),
                        Optional::get)));

        // 정수 n을 인수로 받까서 2에서 n까지의 자연수를 소수(prime)와 비소수(none prime)로 나누는 프로그램
        Map<Boolean, List<Integer>> solution6_4_2 = solution6_4.partitionPrimes(100);

        return;
    }
}
