package exercise.chapter05;

import static java.util.stream.Collectors.toList;

import exercise.chapter04.Dish;
import exercise.chapter04.Dish.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Quiz5_3 {

    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Type.MEAT),
            new Dish("beef", false, 700, Type.MEAT),
            new Dish("chicken", false, 400, Type.MEAT),
            new Dish("french fries", true, 530, Type.OTHER),
            new Dish("rice", true, 350, Type.OTHER),
            new Dish("season fruit", true, 120, Type.OTHER),
            new Dish("pizza", true, 550, Type.OTHER),
            new Dish("prawns", false, 300, Type.FISH),
            new Dish("salmon", false, 450, Type.FISH));

        // map과 reduce 메서드를 이용해서 스트림의 요리 개수를 계산하시오.
        // map과 reduce를 연결하는 기법을 맵 리듀스(map-reduce)패턴이라 하며, 쉽게 병렬화하는 특징 덕분 에 구글이 웹 검색에 적용하면서 유명해졌다.
        int count1 = menu.stream()
            .map(d -> 1)
            .reduce(0, (a, b) -> a + b);

        long count2 = menu.stream().count();

        return;
    }
}
