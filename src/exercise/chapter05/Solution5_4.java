package exercise.chapter05;

import static java.util.stream.Collectors.toList;

import exercise.chapter04.Dish;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Solution5_4 {

    public static void main(String[] args) {
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

        // 스트림 파이프라인은 내부적으로 단일 과정으로 실행할 수 있도록 최적화된다. 즉，쇼트서킷을 이용해서 결과를 찾는 즉시 실행을 종료한다.
        Optional<Dish> dish =
            menu.stream()
                .filter(Dish::isVegetarian)
                .findAny();

        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> firstSquareDivisibleByThree =
            someNumbers.stream()
                .map(n -> n * n)
                .filter(n -> n % 3 == 0)
                .findFirst(); // 9

        return;
    }
}
