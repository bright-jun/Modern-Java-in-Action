package exercise.chapter08;

import java.util.Arrays;
import java.util.List;

public class Quiz8_1 {

    public static void main(String[] args) {
        List<String> actors1 = List.of("Keanu", "Jessica");
        // UnsupportedOperationException
        // List.of로 만든 컬렉션은 바꿀 수 없기 때문이다
//        actors1.set(0, "Brad");
        System.out.println(actors1);

        // [JAVA] - Arrays.asList()와 List.of()의 차이
        // https://kim-jong-hyun.tistory.com/31
        List<String> actors2 = Arrays.asList("Keanu", "Jessica");
        actors2.set(0, "Brad");
        System.out.println(actors2);
    }
}
