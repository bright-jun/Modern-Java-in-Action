package exercise.chapter05;

import static java.util.stream.Collectors.toList;

import exercise.chapter04.Dish;
import exercise.chapter04.Dish.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Quiz5_2 {

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

        // 1. 숫자 리스트가 주어졌을 때 각 숫자의 제곱근으로 이루어진 리스트를 반환하시오. 예를 들어 [1, 2, 3, 4, 5］가 주어지면 ［1, 4, 9, 16, 25］를 반환해야 한다.
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squares =
            numbers.stream()
                .map(n -> n * n)
                .collect(toList());

        // 2. 두 개의 숫자 리스트가 있을 때 모든 숫자 쌍의 리스트를 반환하시오. 예를 들어 두 개의 리스트 [1, 2, 3]과 [3, 4]가 주어지면 [(1, 3),(1, 4),(2, 3),(2, 4),(3, 3)，(3, 4)]를 반환해야 한다
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int[]> pairs1 =
            numbers1.stream()
                // int[] 원소자체를 Mapping 함
                .flatMap(i -> numbers2.stream()
                    .map(j -> new int[]{i, j})
                )
                .collect(toList());
        List<Stream<int[]>> pairs2 =
            numbers1.stream()
                // int[] 원소 스트림을 Mapping 함
                .map(i -> numbers2.stream()
                    .map(j -> new int[]{i, j})
                )
                .collect(toList());

        // 3. 이전 예제에서 합이 3으로 나누어떨어지는 쌍만 반환하려면 어떻게 해야 할까? 예를 들어(2, 4), (3, 3)을 반환해야한다.
        // TODO pairs 3,4,5 차이?
        List<int[]> pairs3 =
            numbers1.stream()
                // int[] 원소자체를 Mapping 함
                .flatMap(i -> numbers2.stream()
                    .map(j -> new int[]{i, j})
                )
                .filter(i -> (i[0] + i[1]) % 3 == 0)
                .collect(toList());

        List<int[]> pairs4 =
            numbers1.stream()
                // int[] 원소자체를 Mapping 함
                .flatMap(i -> numbers2.stream()
                    .map(j -> new int[]{i, j})
                    .filter(j -> (j[0] + j[1]) % 3 == 0)
                )
                .collect(toList());

        List<int[]> pairs5 =
            numbers1.stream()
                // int[] 원소자체를 Mapping 함
                .flatMap(i -> numbers2.stream()
                    .filter(j -> (i + j) % 3 == 0)
                    .map(j -> new int[]{i, j})
                )
                .collect(toList());

        return;
    }
}
