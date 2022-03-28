package exercise.chapter03;

public class Solution3_2 {
	Runnable r1 = () -> System.out.println("Hello World 1");
	Runnable r2 = new Runnable() {
		public void run() {
			System.out.println("Hello World 2");
		}
	};
	public static void process(Runnable r) {
		r.run();
	}
	
	public static void main(String[] args) {
		Solution3_2 solution3_2 = new Solution3_2();
		process(solution3_2.r1);
		process(solution3_2.r2);
		process(() -> System.out.println("Hello World 3"));
		process(() -> {});
		process(() -> System.out.println("This is awesome"));
		// 한 개의 void 함수는 중괄호가 필요 없다. 
		process(() -> { System.out.println("This is awesome"); });
	}
}
