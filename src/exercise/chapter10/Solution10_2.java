package exercise.chapter10;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solution10_2 {

    public static void main(String[] args) throws IOException {
        List<Person> persons = Arrays.asList(new Person(1, "A"), new Person(2, "B"),
            new Person(3, "C"));
        Collections.sort(persons, new Comparator<Person>() {
            public int compare(Person p1, Person p2) {
                return p1.getAge() - p2.getAge();
            }
        });

        // 람다 표현식 - 신호 대비 잡음 비율을 줄임
        Collections.sort(persons, (p1, p2) -> p1.getAge() - p2.getAge());
        // 정적 유틸리티 메서드 집합
        Collections.sort(persons, comparing(p -> p.getAge()));
        // 메서드 참조
        Collections.sort(persons, comparing(Person::getAge));
        // 역순
        Collections.sort(persons, comparing(Person::getAge).reversed());
        // 나이순 + 이름순
        Collections.sort(persons, comparing(Person::getAge)
            .thenComparing(Person::getName));
        // List 인터페이스에 추가된 새 sort 메서드
        persons.sort(comparing(Person::getAge)
            .thenComparing(Person::getName));


        List<String> errors1 = new ArrayList<>();
        int errorCount = 0;
        File fileName = new File("src/exercise/chapter10/sample.txt");
        BufferedReader bufferedReader
            = new BufferedReader(new FileReader(fileName));
        String line = bufferedReader.readLine();
        while (errorCount < 40 && line != null) {
            if (line.startsWith("ERROR")) {
                errors1.add(line);
                errorCount++;
            }
            line = bufferedReader.readLine();
        }

        // Stream 인터페이스 -> 함수형 코드 구현
        List<String> errors2 = Files.lines(Paths.get("src/exercise/chapter10/sample.txt"))
            .filter(l -> l.startsWith("ERROR"))
            .limit(40)
            .collect(toList());

        // 자동차를 브랜드별 -> 색상별로 그룹화
        /* TODO Implement
        Map<String, Map<Color, List<Car>>> carsByBrandAndColor =
            cars.stream().collect(groupingBy(Car::getBrand,
                groupingBy(Car::getColor)));
        */
        // 두 Comparator를 플루언트 방식으로 연결해서 다중 필드 Comparator를 정의
        Comparator<Person> comparator =
            comparing(Person::getAge).thenComparing(Person::getName);

        // 반면 Collectors API를 이용해 Collectors를 중첩함으로 다중 수준 Collector 생성
        /* TODO Implement
        Collector<? super Car, ?, Map<Brand, Map<Color, List<Car>>>>
            carGroupingCollector =
            groupingBy(Car::getBrand, groupingBy(Car::getColor));
        */

        /* TODO Implement
        Collector<? super Car, ?, Map<Brand, Map<Color, List<Car>>>>
            carGroupingCollector =
            groupOn(Car::getColor).after(Car::getBrand).get()
        */

        return;
    }
}
