package exercise.chapter06;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

import exercise.chapter04.Dish;
import exercise.chapter04.Dish.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.IntStream;

public class Solution6_5 {

    public static void main(String[] args) {
        Solution6_5 solution6_5 = new Solution6_5();

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

        List<Dish> dishes1 =
            menu.stream()
                .collect(new ToListCollector<Dish>());

        List<Dish> dishes2 =
            menu.stream()
                .collect(toList());

        // 컬렉터 구현을 만들지 않고도 커스팀 수집 수행하기
        List<Dish> dishes = menu.stream()
            .collect(ArrayList::new,
                List::add,
                List::addAll);
        // TODO characteristics 는 디폴트 값이 있는건지? 없으면 아래조건밖에 못한다는 건지?
        // Characteristics를 전달할 수 없다. 즉，두 번째 collect 메서드는
        // IDENTITY_FINISH : 같은 형식반환이고
        // CONCURRENT : 요소의 순서가 무의미하고
        // UNORDERED : 리듀싱 결과는 스트림 요소의 방문 순서나 누적 순서에 영향을 받지 않는데, 그러면 안되는 컬렉터로만 동작한다.

        return;
    }
}
