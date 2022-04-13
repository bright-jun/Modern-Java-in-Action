package exercise.chapter06;

import static java.util.stream.Collectors.partitioningBy;

import exercise.chapter02.Predicate;
import exercise.chapter04.Dish;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Quiz6_3 {

    public static <A> List<A> myTakeWhile(List<A> list, Predicate<A> p) {
        int i = 0;
        for (A item : list) {
            if (!p.test(item)) {
                return list.subList(0, i);
            }
            i++;
        }
        return list;
    }

    public static boolean isPrime(List<Integer> primes, int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return myTakeWhile(primes, i -> i <= candidateRoot)
            .stream()
            .noneMatch(p -> candidate % p == 0);
    }

    public static void main(String[] args) {
        Quiz6_3 quiz6_3 = new Quiz6_3();

        List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH));

        boolean quiz1 = isPrime(
            IntStream.rangeClosed(2, 10).boxed().collect(Collectors.toList()), 210);
        boolean quiz2 = isPrime(
            Arrays.asList(2,3,5,7).stream().collect(Collectors.toList()), 210);

        return;
    }
}
