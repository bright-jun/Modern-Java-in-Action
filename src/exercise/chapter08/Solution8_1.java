package exercise.chapter08;

import static java.util.stream.Collectors.*;
import static java.util.Map.entry;

import java.util.*;
import java.util.stream.Stream;

public class Solution8_1 {

    public static void main(String[] args) {
        List<String> friends1 = new ArrayList<>();
        friends1.add("Raphael");
        friends1.add("Olivia");
        friends1.add("Thibaut");

        // 팩토리 메서드
        List<String> friends2
            = Arrays.asList("Raphael", "Olivia", "Thibaut");

        List<String> friends3 = Arrays.asList("Raphael", "Olivia");
        friends3.set(0, "Richard");
        // 고정 크기의 리스트를 만들었으므로 요소를 갱신할 순 있지만 새 요소를 추가하거나 요소를 삭제할 순 없다.
        // UnsupportedOperationException
        // friends3.add("Thibaut");

        // 리스트를 인수로 받는 HashSet 생성자
        Set<String> friends4
            = new HashSet<>(Arrays.asList("Raphael", "Olivia", "Thibaut"));

        // 스트림 API
        Set<String> friends5
            = Stream.of("Raphael", "Olivia", "Thibaut")
            .collect(toSet());

        // 8.1.1 리스트 팩토리
        List<String> friends6 = List.of("Raphael", "Olivia", "Thibaut");
        // UnsupportedOperationException
        // friends6.add("Chih-Chun");

        // 8.1.2 집합 팩토리
        Set<String> friends7 = Set.of("Raphael", "Olivia", "Thibaut");
        System.out.println(friends7);

        // IllegalArgumentException
        // 중복된 요소
        // Set<String> friends8 = Set.of("Raphael", "Olivia", "Olivia");

        // 8.1.3 맵 팩토리
        // 열 개 이하의 키와 값 쌍을 가진 작은 맵에는 유용
        Map<String, Integer> ageOfFriends1
            = Map.of("Raphael", 30, "Olivia", 25, "Thibaut", 26);
        System.out.println(ageOfFriends1);

        // 가변 인수로 구현
        Map<String, Integer> ageOfFriends2
            = Map.ofEntries(entry("Raphael", 30),
            entry("Olivia", 25),
            entry("Thibaut", 26));
        System.out.println(ageOfFriends2);
    }
}
