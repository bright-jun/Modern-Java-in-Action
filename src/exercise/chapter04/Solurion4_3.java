package exercise.chapter04;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Solurion4_3 {

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

        // 4.3.1
        List<String> title = Arrays.asList("Modern", "Java", "In", "Action");
        Stream<String> s = title.stream();
        s.forEach(System.out::println);
//        s.forEach(System.out::println); // 스트림이 이미 소비되었거나 닫힘

        // 4.3.2
        // Listing 4.1. Collections: external iteration with a for-each loop
        List<String> names1 = new ArrayList<>();
        for(Dish dish: menu) {
            names1.add(dish.getName());
        }

        // Listing 4.2. Collections: external iteration using an iterator behind the scenes
        List<String> names2 = new ArrayList<>();
        Iterator<Dish> iterator = menu.iterator();
        while(iterator.hasNext()) {
            Dish dish = iterator.next();
            names2.add(dish.getName());
        }

        // Listing 4.3. Streams: internal iteration
        List<String> names3 = menu.stream()
            .map(Dish::getName)
            .collect(toList());

        return;
    }
}
