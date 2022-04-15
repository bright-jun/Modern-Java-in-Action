package exercise.chapter06;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

import exercise.chapter04.Dish;
import exercise.chapter04.Dish.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class Solution6_3 {

    public static void main(String[] args) {
        List<Dish> menu = asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH));

        Map<Type, List<Dish>> dishesByType =
            menu.stream().collect(groupingBy(Dish::getType));

        // 메서드 참조 대신 람다 표현식으로 필요한 로직을 구현할 수 있다
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream().collect(
            groupingBy(dish -> {
                if (dish.getCalories() <= 400) {
                    return CaloricLevel.DIET;
                } else if (dish.getCalories() <= 700) {
                    return CaloricLevel.NORMAL;
                } else {
                    return CaloricLevel.FAT;
                }
            }));

        // 우리의 필터 프레디케이트를 만족하는 FISH 종류 요리는 없으므로 결과 맵에서 해당 키 자체가 사라진다.
        Map<Dish.Type, List<Dish>> caloricDishesByType1 =
            menu.stream()
                .filter(dish -> dish.getCalories() > 500)
                .collect(groupingBy(Dish::getType));

        // Collector 안으로 필터 프레디케이트를 이동함으로 이 문제를 해결할 수 있다.
        Map<Dish.Type, List<Dish>> caloricDishesByType2 =
            menu.stream()
                .collect(groupingBy(Dish::getType,
                    // 프레디케이트를 인수로 받는다
                    // 이 프레디케이트로 각 그룹의 요소와 필터링 된 요소를 재그룹화 한다
                    // 이렇게 해서 아래 결과 맵에서 볼 수 있는 것처럼 목록이 비어있는 FISH도 항목으로 추가된다.
                    filtering(dish -> dish.getCalories() > 500, toList())));

        // 그룹의 각 요리를 관련 이름 목록으로 변환할 수 있다.
        Map<Dish.Type, List<String>> dishNamesByType1 =
            menu.stream()
                .collect(groupingBy(Dish::getType,
                    mapping(Dish::getName, toList())));

        // 세 번째 컬렉터를 사용해서 일반 맵이 아닌 flatMap 변환을 수행할 수 있다
        Map<String, List<String>> dishTags = new HashMap<>();
        dishTags.put("pork", asList("greasy", "salty"));
        dishTags.put("beef", asList("salty", "roasted"));
        dishTags.put("chicken", asList("fried", "crisp"));
        dishTags.put("french fries", asList("greasy", "fried"));
        dishTags.put("rice", asList("light", "natural"));
        dishTags.put("season fruit", asList("fresh", "natural"));
        dishTags.put("pizza", asList("tasty", "salty"));
        dishTags.put("prawns", asList("tasty", "roasted"));
        dishTags.put("salmon", asList("delicious", "fresh"));

        // 각 형식의 요리의 태그를 간편하게 추출할 수 있다.
        Map<Dish.Type, Set<String>> dishNamesByType2 =
            menu.stream()
                .collect(groupingBy(Dish::getType,
                    flatMapping(dish -> dishTags.get(dish.getName()).stream(),
                        toSet())));

        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel =
            menu.stream().collect(
                groupingBy(Dish::getType,
                    groupingBy(dish -> {
                        if (dish.getCalories() <= 400) {
                            return CaloricLevel.DIET;
                        } else if (dish.getCalories() <= 700) {
                            return CaloricLevel.NORMAL;
                        } else {
                            return CaloricLevel.FAT;
                        }
                    })
                )
            );

        Map<Dish.Type, Long> typesCount = menu.stream().collect(
            groupingBy(Dish::getType, counting()));

        // 그룹화의 결과로 요리의 종류를 키(key)로，Optional<Dish>를 값(value)으로 갖는 맵이 반환된다.
        Map<Dish.Type, Optional<Dish>> mostCaloricByType1 =
            menu.stream()
                .collect(groupingBy(Dish::getType,
                    maxBy(comparingInt(Dish::getCalories))));
        /*
        NOTE_ 팩토리 메서드 maxBy가 생성하는 컬렉터의 결과 형식에 따라 맵의 값이 Optional 형식이 되었다.
        실제로 메뉴의 요리 중 Optional.empty()를 값으로 갖는 요리는 존재하지 않는다
        처음부터 존재하지 않는 요리의 키는 맵에 추가되지 않기 때문이다.
        groupingBy 컬렉터는 스트림의 첫 번째 요소를 찾은 이후에야 그룹화 맵에 새로운 카를 (게으르게) 추가한다
        리듀싱 컬렉터가 반환하는 형식을 사용하는 상황이므로 굳이 Optional 래퍼를 사용할 필요가 없다

        mostCaloricByType2처럼 굳이 Optional<Dish>를 반환하도록 안해도 된다.
         */

        Map<Dish.Type, Dish> mostCaloricByType2 =
            menu.stream()
                .collect(groupingBy(Dish::getType,
                    collectingAndThen(
                        maxBy(comparingInt(Dish::getCalories)),
                        Optional::get)));

        Map<Dish.Type, Integer> totalCaloriesByType =
            menu.stream().collect(groupingBy(Dish::getType,
                summingInt(Dish::getCalories)));

        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType1 =
            menu.stream().collect(
                // mapping 메서드에 전달한 변환함수는 Dish를 CaloricLevel로 매핑한다.
                groupingBy(Dish::getType, mapping(dish -> {
                        if (dish.getCalories() <= 400) {
                            return CaloricLevel.DIET;
                        } else if (dish.getCalories() <= 700) {
                            return CaloricLevel.NORMAL;
                        } else {
                            return CaloricLevel.FAT;
                        }
                    },
                    // Caloric Level 결과 스트림은 (toList 와 비슷한) toSet 컬렉터로 전달되면서 리스트가 아닌 집합으로 스트림의 요소가 누적된다(따라서 중복된 값은 저장되지 않는다)
                    toSet())));

        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType2 =
            menu.stream().collect(
                groupingBy(Dish::getType, mapping(dish -> {
                        if (dish.getCalories() <= 400) {
                            return CaloricLevel.DIET;
                        } else if (dish.getCalories() <= 700) {
                            return CaloricLevel.NORMAL;
                        } else {
                            return CaloricLevel.FAT;
                        }
                    },
                    // set 형식 제어 가능
                    toCollection(HashSet::new))));

        return;
    }
}
