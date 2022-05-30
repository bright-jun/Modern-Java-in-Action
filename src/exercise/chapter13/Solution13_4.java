package exercise.chapter13;

public class Solution13_4 {

    interface A {
        default void hello() {
            System.out.println("Hello from A");
        }
    }
    interface B {
        default void hello() {
            System.out.println("Hello from B");
        }
    }
    class C implements B, A {

        @Override
        public void hello() {
            B.super.hello();
        }
    }

    public static void main(String[] args) {
        Solution13_4 sol = new Solution13_4();
        C c = sol.new C();
        c.hello(); // Hello from B
    }
}
