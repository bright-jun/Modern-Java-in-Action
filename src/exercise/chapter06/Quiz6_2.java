package exercise.chapter06;

import static java.util.stream.Collectors.*;

import exercise.chapter04.Dish;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Quiz6_2 {

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

        Map<Boolean, Map<Boolean, List<Dish>>> quiz1 =
            menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                    partitioningBy(d -> d.getCalories() > 500)));

        // partitioningBy는 불리언,  Dish::getType은 프레디케이트로 사용할 수 없는 메서드 참조
//        Map<Boolean, Map<Boolean, List<Dish>>> quiz2 = menu.stream().collect(partitioningBy(Dish::isVegetarian,
//            partitioningBy(Dish::getType)));

        Map<Boolean, Long> quiz3 =
            menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                    counting()));

        return;
    }
}
