package exercise.chapter06;

import static java.util.stream.Collectors.*;

import exercise.chapter04.Dish;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Quiz6_1 {

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

        String shortMenu =
            menu.stream()
                .map(Dish::getName)
                .collect(joining());
        String shortMenu1 =
            menu.stream()
                .map(Dish::getName)
                .collect(reducing((s1, s2) -> s1 + s2))
                .get();
        // reducing 은 두 인수를 받아 같은 형식을 반환하는 함수를 인수로 받는다.
        // 하지만 2번 람다 표현식은 두 개의 요리를 인수로 받아 문자열을 반환한다.
//        String shortMenu2 = menu.stream()
//            .collect(reducing((d1, d2) -> d1.getName() + d2.getName()))
//            .get();

        String shortMenu3 = menu.stream()
            .collect(reducing("", Dish::getName, (s1, s2) -> s1 + s2));
    }
}
