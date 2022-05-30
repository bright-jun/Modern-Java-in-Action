package exercise.chapter13;

public class Quiz13_2 {

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

    class D implements A {

        @Override
        public void hello() {
            System.out.println("Hello from D");
        }
    }

    class C extends D implements B, A {
    }

    public static void main(String[] args) {
        Quiz13_2 q = new Quiz13_2();
        q.new C().hello(); // Hello from D
    }
}
