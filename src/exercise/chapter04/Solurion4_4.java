package exercise.chapter04;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Solurion4_4 {

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

        List<String> names1 =
            // 요리 리스트에서 스트림 얻기
            menu.stream()
                // 중간연산
                .filter(dish -> dish.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                // 스트림을 리스트로 변환
                .collect(toList());

        // 중간 연산
        // filter, distinct, limit는 스트림을 반환하며 서로 연결할수 있다
        // 최종 연산
        // count. 스트림 파이프라인의 마지막 연산 count는 스트림이 아닌 long을 반환한다.
        List<String> names2 =
            menu.stream()
                .filter(dish -> {
                    // 필터링한 요리명을 출력한다
                    System.out.println("filtering:" + dish.getName());
                    return dish.getCalories() > 300;
                })
                .map(dish -> {
                    // 추출한 요리명을 출력한다
                    System.out.println("mapping:" + dish.getName());
                    return dish.getName();
                })
                .limit(3)
                .collect(toList());
        System.out.println(names2);

        // 최종 연산
        menu.stream().forEach(System.out::println);

        return;
    }
}
