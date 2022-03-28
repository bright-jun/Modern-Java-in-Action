package exercise.chapter03;

import java.util.concurrent.Callable;
import java.util.function.Predicate;

import exercise.chapter02.Apple;

public class Quiz3_3 {
	public static void execute(Runnable r) {
		r.run();
	}

	public Callable<String> fetch() {
		return () -> "Tricky example ;-)";
	}

	public static void main(String[] args) {
		execute(() -> {});
		
//		Predicate<Apple> p = (Apple a) -> a.getWeight(); // type mismatch
	}
}
