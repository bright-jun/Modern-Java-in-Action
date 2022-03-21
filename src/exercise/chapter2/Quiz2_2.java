package exercise.chapter2;

import static exercise.chapter2.Color.GREEN;
import static exercise.chapter2.Color.RED;

import java.util.ArrayList;
import java.util.List;

public class Quiz2_2 {
	public static void prettyPrintApple(List<Apple> inventory, AppleFormatter f) {
		for (Apple apple : inventory) {
			String output = f.accept(apple);
			System.out.println(output);
		}
	}

	public static class AppleFancyFormatter implements AppleFormatter {
		public String accept(Apple apple) {
			String characteristic = apple.getWeight() > 150 ? "heavy" : "light";
			return "A " + characteristic + " " + apple.getColor() + " apple";
		}
	}

	public static class AppleSimpleFormatter implements AppleFormatter {
		public String accept(Apple apple) {
			return "An apple of " + apple.getWeight() + "g";
		}
	}
	
	public static void main(String[] args) {
		List<Apple> inventory = new ArrayList<Apple>();
		inventory.add(new Apple(20, GREEN));
		inventory.add(new Apple(10, RED));
		inventory.add(new Apple(200, RED));
		
		prettyPrintApple(inventory, new AppleFancyFormatter());
		prettyPrintApple(inventory, new AppleSimpleFormatter());
	}
}
