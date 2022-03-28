package exercise.chapter02;

import static exercise.chapter02.Color.GREEN;
import static exercise.chapter02.Color.RED;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution2_3 {
	// 익명 클래스
	// 지역 클래스(local class)(블록 내부에 선언된 클래스)와 비슷한 개념
	// 이름이 없는 클래스
	// 클래스 선언과 인스턴스화를 동시에 할 수 있다.
	// 즉석에서 필요한 구현을 만들어서 사용할 수 있다.

	// 익명 클래스 사용
	public static class AppleHeavyWeightPredicate implements ApplePredicate {
		public boolean test(Apple apple) {
			return apple.getWeight() > 150;
		}
	}

	public static class AppleGreenColorPredicate implements ApplePredicate {
		public boolean test(Apple apple) {
			return GREEN.equals(apple.getColor());
		}
	}

	public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (p.test(apple)) {
				result.add(apple);
			}
		}
		return result;
	}

	public static <T> List<T> filter(List<T> list, Predicate<T> p) {
		List<T> result = new ArrayList<>();
		for (T e : list) {
			if (p.test(e)) {
				result.add(e);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		List<Apple> inventory = Arrays.asList(new Apple(80, GREEN), new Apple(155, GREEN), new Apple(120, RED));
		List<Apple> heavyApples = filterApples(inventory, new AppleHeavyWeightPredicate());
		List<Apple> greenApples = filterApples(inventory, new AppleGreenColorPredicate());

		// 익명 Predicate객체를 만듦으로써 filterApples메서드의 동작을 직접 파라미터화 했음
		List<Apple> redApples = filterApples(inventory, new ApplePredicate() {
			public boolean test(Apple apple) {
				return RED.equals(apple.getColor());
			}
		});

		// 람다 표현식 사용으로 훨씬 깔끔한 코드 구현
		List<Apple> result = filterApples(inventory, (Apple apple) -> RED.equals(apple.getColor()));

		// 제네릭을 사용하여 인풋형식을 추상화
		List<Apple> redApples2 = filter(inventory, (Apple apple) -> RED.equals(apple.getColor()));
		
		List<Integer> numbers = IntStream.range(1, 11).collect(ArrayList::new, List::add, List::addAll);
		List<Integer> evenNumbers = filter(numbers, (Integer i) -> i % 2 == 0);
		
		return;
	}
}
