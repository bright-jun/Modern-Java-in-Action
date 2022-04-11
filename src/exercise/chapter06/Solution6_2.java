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

public class Solution6_2 {

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

        long howManyDishes1 = menu.stream().collect(counting());
        long howManyDishes2 = menu.stream().count();

        Comparator<Dish> dishCaloriesComparator =
            Comparator.comparingInt(Dish::getCalories);
        /*
        public static <T> Comparator<T> comparingInt(ToIntFunction<? super T> keyExtractor) {
            Objects.requireNonNull(keyExtractor);
            return (Comparator<T> & Serializable)
                (c1, c2) -> Integer.compare(keyExtractor.applyAsInt(c1), keyExtractor.applyAsInt(c2));
        }
         */
        Optional<Dish> mostCalorieDish1 =
            menu.stream()
                .collect(maxBy(dishCaloriesComparator));

        int totalCalories1 =
            menu.stream()
                .collect(summingInt(Dish::getCalories));

        double avgCalories =
            menu.stream()
                .collect(averagingInt(Dish::getCalories));

        IntSummaryStatistics menuStatistics =
            menu.stream()
                .collect(summarizingInt(Dish::getCalories));

        String shortMenu1 =
            menu.stream()
                .map(Dish::getName)
                .collect(joining());
        // `Dish` 클래스가 요리명을 반환하는 `toString` 메서드를 포함하고 있다면 다음 코드에서 보여주는 것처럼 `map`으로 각 요리의 이름을 추출하는 과정을 생략할 수 있다.
//        String shortMenu2 =
//            menu.stream()
//                .collect(joining());
        String shortMenu2 =
            menu.stream()
                .map(Dish::toString)
                .collect(joining());

        String shortMenu3 =
            menu.stream()
                .map(Dish::getName)
                .collect(joining(", "));

        // 메뉴의 모든 칼로리 합계를 계산
        int totalCalories2 =
            menu.stream()
                .collect(reducing(0, Dish::getCalories, (i, j) -> i + j));

        // 가장 칼로리가 높은 요리를 찾는 방 법
        Optional<Dish> mostCalorieDish2 =
            menu.stream()
                .collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));

        Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5, 6).stream();
        List<Integer> numbers = stream.reduce(
            new ArrayList<>(),
            (List<Integer> l, Integer e) -> {
                l.add(e);
                return l;
            },
            (List<Integer> l1, List<Integer> l2) -> {
                l1.addAll(l2);
                return l1;
            });

        int totalCalories3 =
            menu.stream()
                .collect(reducing(
                    // 누적자
                    0,
                    // 변환 함수
                    Dish::getCalories,
                    // 합계 함수
                    Integer::sum));

        int totalCalories4 =
            menu.stream()
                .map(Dish::getCalories)
                .reduce(Integer::sum)
                .get(); // orElse, orElseGet 이용

        // 가독성이 가장 좋고 간결하다. IntStream 덕분에 자동 언박싱 autounboxing 연산을 수행하거나 integer를 int로 변환하는 과정을 피할 수 있으므로 성능까지 좋다
        int totalCalories5 =
            menu.stream()
                // IntStream으로 매핑
                .mapToInt(Dish::getCalories)
                .sum();

        return;
    }
}
