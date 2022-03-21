package exercise.chapter2;

public class Quiz2_3 {
	public final int value = 4;

	public void doIt() {
		int value = 6;
		Runnable r = new Runnable() {
			public final int value = 5;

			public void run() {
				int value = 10;
				// this = Runnable 인스턴스
				System.out.println(this.value);
			}
		};
		r.run();
	}

	public static void main(String... args) {
		Quiz2_3 quiz2_3 = new Quiz2_3();
		quiz2_3.doIt();
	}
}
