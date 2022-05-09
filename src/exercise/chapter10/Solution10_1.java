package exercise.chapter10;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Solution10_1 {

    public static void main(String[] args) {
        List<String> numbers = Arrays.asList("one", "two", "three");
        numbers.forEach(new Consumer<String>() { // 코드의 잡음
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });

        // 익명 내부클래스 -> 람다 표현식으로 변환
        numbers.forEach(s -> System.out.println(s));
        numbers.forEach(System.out::println);
    }
}
