package exercise.chapter05;

import static java.util.stream.Collectors.toList;

import exercise.chapter04.Dish;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Solution5_3 {

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

        List<String> dishNames = menu.stream()
            .map(Dish::getName)
            .collect(toList());

        List<String> words = Arrays.asList("Modern", "Java", "In", "Action");
        List<Integer> wordLengths = words.stream()
            .map(String::length)
            .collect(toList());

        List<Integer> dishNameLengths = menu.stream()
            // String dishName
            .map(Dish::getName)
            // Integer dishName.length
            .map(String::length)
            .collect(toList());

        List<String[]> chars1 = words.stream()
            // 위 코드에서 map으로 전달한 람다는 각 단어의 String!] (문자열 배열)을 반환한다는 점이 문제다
            .map(word -> word.split(""))
            .distinct()
            .collect(toList());

        // map과 Arrays.stream 활용
        String[] arrayOfWords = {"Goodbye", "World"};
        Stream<String> streamOfwords = Arrays.stream(arrayOfWords);
        streamOfwords.forEach(System.out::println);

        List<Stream<String>> chars2 = words.stream()
            // List<Stream<String>> 반환
            .map(word -> word.split(""))
            .map(Arrays::stream)
            .distinct()
            .collect(toList());

        List<String> uniqueCharacters =
            words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(toList());

        return;
    }
}
