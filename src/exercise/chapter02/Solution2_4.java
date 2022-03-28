package exercise.chapter02;

import static exercise.chapter02.Color.GREEN;
import static exercise.chapter02.Color.RED;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Solution2_4 {
	/*
	 * / java.util.Comparator
	 * public interface Comparator<T> {
	 *     int compare(T o1, T o2);
	 * }
	 */
	
	/*
	 * / java.lang.Runnable
	 * public interface Runnable {
	 *     void run();
	 * }
	 */
	
	/*
	 * / java.util.concurrent.Callable
	 * public interface Callable<V> {
	 *     V call();
	 * }
	 */
	
	// 실전
	public static void main(String[] args) {
		// 정렬하기
		List<Apple> inventory = Arrays.asList(new Apple(80, GREEN), new Apple(155, GREEN), new Apple(120, RED));
		inventory.sort(new Comparator<Apple>() {
			public int compare(Apple a1, Apple a2) {
				// int에는 compareTo 함수가없음, Integer객체에는 있음
				return a1.getWeight().compareTo(a2.getWeight());
			}
		});
		// 람다 표현식
		inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));

		// 스레드
		Thread t = new Thread(new Runnable() {
			public void run() {
				System.out.println("Hello world");
			}
		});
		// 람다 표현식
		t = new Thread(() -> System.out.println("Hello world"));
		
		// Future 이벤트 처리하기
		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<String> threadName = executorService.submit(new Callable<String>() {
		    @Override
		    public String call() throws Exception {
		        return Thread.currentThread().getName();
		    }
		});
		// 람다 표현식
		threadName = executorService.submit(() -> Thread.currentThread().getName());
		
//		Button button = new Button("Send");
//		button.setOnAction(new EventHandler<ActionEvent>() {
//		    public void handle(ActionEvent event) {
//		        label.setText("Sent!!");
//		    }
//		});
//		
//		button.setOnAction((ActionEvent event) -> label.setText("Sent!!"));
		
		return;
	}
}
