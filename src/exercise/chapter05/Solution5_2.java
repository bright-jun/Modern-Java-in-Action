package exercise.chapter05;

import static java.util.stream.Collectors.toList;

import exercise.chapter04.Dish;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution5_2 {

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

        List<Dish> specialMenu = Arrays.asList(
            new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER));
        List<Dish> filteredMenu =
            specialMenu.stream()
                .filter(dish -> dish.getCalories() < 320)
                .collect(toList());

        // using TAKEWHILE
        List<Dish> slicedMenu1 =
            specialMenu.stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .collect(toList());

        // using DROPWHILE
        List<Dish> slicedMenu2 =
            specialMenu.stream()
                .dropWhile(dish -> dish.getCalories() < 320)
                .collect(toList());

        List<Dish> dishes1 =
            specialMenu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .limit(3)
                .collect(toList());

        List<Dish> dishes2 = menu.stream()
            .filter(d -> d.getCalories() > 300)
            .skip(2)
            .collect(toList());

        List<Dish> dishes3 = menu.stream()
            .skip(2)
            .limit(3)
            .collect(toList());

        List<Dish> dishes4 = menu.stream()
            .limit(3)
            .skip(2)
            .collect(toList());
        
        // 중간연산 순서에 따라 결과가 달라짐
        // 중간연산은 받은 stream을 기준으로 연산을 하기 때문
        return;
    }
}
