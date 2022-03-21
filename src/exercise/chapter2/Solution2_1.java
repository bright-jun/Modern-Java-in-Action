package exercise.chapter2;

import static exercise.chapter2.Color.*;

import java.util.ArrayList;
import java.util.List;

public class Solution2_1 {
	// 녹색 사과 필터링
	public static List<Apple> filterGreenApples(List<Apple> inventory) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (GREEN.equals(apple.getColor())) {
				result.add(apple);
			}
		}
		return result;
	}

	// 색을 파라미터화
	public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (apple.getColor().equals(color)) {
				result.add(apple);
			}
		}
		return result;
	}

	// 무게 파라미터화
	public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (apple.getWeight() > weight) {
				result.add(apple);
			}
		}
		return result;
	}

	// 가능한 모든 속성으로 필터링
	// flag 기준으로 필터링 조건을 선택한다
	// 동적필터링이 안됨
	// 마치 JPA QueryMethod 같은 느낌
	public static List<Apple> filterApples(List<Apple> inventory, Color color, int weight, boolean flag) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if ((flag && apple.getColor().equals(color)) || (!flag && apple.getWeight() > weight)) {
				result.add(apple);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		List<Apple> inventory = new ArrayList<Apple>();
		inventory.add(new Apple(20, GREEN));
		inventory.add(new Apple(10, RED));

		List<Apple> greenApples = filterApplesByColor(inventory, GREEN);
		List<Apple> redApples = filterApplesByColor(inventory, RED);
		List<Apple> weightMoreThan10Apples = filterApplesByWeight(inventory, 10);
		List<Apple> greenApples1 = filterApples(inventory, GREEN, 0, true);
		List<Apple> heavyApples2 = filterApples(inventory, null, 150, false);

		return;
	}
}
