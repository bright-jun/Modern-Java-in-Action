package exercise.chapter05;

import exercise.chapter04.Dish;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Solution5_5 {

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

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        // sum 변수의 초깃값 0
        // 리스트의 모든 요소를 조합하는 연산(+)
        int sum1 = 0;
        for (int x : numbers) {
            sum1 += x;
        }

        // reduce를 이용하면 애플리케이션의 반복된 패턴을 추상화
        // 초깃값
        // 두 요소를 조합해서 새로운 값을 만드는 BinaryOperator<T>. 예제에서는 람다 표현식 (a, b) -> a + b 를 사용했다
        int sum2 = numbers.stream().reduce(0, (a, b) -> a + b);
        int sum3 = numbers.stream().reduce(0, Integer::sum);

        // 초깃값 없음
        // 초깃값을 받지 않도록 오버로드된 reduce도 있다. 그러나 이 reduce는 Optional 객체를 반환 한다.
        Optional<Integer> sum4 = numbers.stream().reduce((a, b) -> (a + b));

        Optional<Integer> max = numbers.stream().reduce(Integer::max);
        Optional<Integer> min = numbers.stream().reduce(Integer::min);

        return;
    }
}
