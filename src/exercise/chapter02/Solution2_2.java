package exercise.chapter02;

import static exercise.chapter02.Color.*;

import java.util.ArrayList;
import java.util.List;

public class Solution2_2 {
	// 전략 디자인 패턴
	// 사과 선택 전략을 캡슐화 하였음
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

	public static class AppleRedAndHeavyPredicate implements ApplePredicate {
		public boolean test(Apple apple) {
			return RED.equals(apple.getColor()) && apple.getWeight() > 150;
		}
	}
	
	// 동작 파라미터화
	// 메서드가 다양한 동작(또는 전략)을 받아서 내부적으로 다양한 동작을 수행할 수 있다.
	// 동작(또는 전략) = Predicate 객체
	// filter 동작을 파라미터화 하였음
	public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (p.test(apple)) {
				result.add(apple);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		List<Apple> inventory = new ArrayList<Apple>();
		inventory.add(new Apple(20, GREEN));
		inventory.add(new Apple(10, RED));
		inventory.add(new Apple(200, RED));
		
		List<Apple> redAndHeavyApples = filterApples(inventory, new AppleRedAndHeavyPredicate());
		
		return;
	}
}
