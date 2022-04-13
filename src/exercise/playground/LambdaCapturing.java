package exercise.playground;

public class LambdaCapturing {
    private int a = 111; // Heap
    private Integer integerA = 111; // TODO Heap? Stack?
    private String strA = "111"; // TODO Heap? Stack?
    private String stringA = new String("111"); // TODO Heap? Stack?
    private Dish dishA = new Dish("dishA", false, 100, Dish.Type.FISH);

    public void test() {
        final int b = 222; // stack
        int c = 333; // stack
        int d = 444; // stack

        final String strB = "222"; // TODO Heap? Stack?
        String strC = "333"; // TODO Heap? Stack?
        String strD = "444"; // TODO Heap? Stack?

        final String stringB = new String("222"); // TODO Heap? Stack?
        String stringC = new String("333"); // TODO Heap? Stack?
        String stringD = new String("444"); // TODO Heap? Stack?

        final Integer integerB = 222; // TODO Heap? Stack?
        Integer integerC = 333; // TODO Heap? Stack?
        Integer integerD = 444; // TODO Heap? Stack?

        final Dish dishB = new Dish("dishB", false, 100, Dish.Type.FISH); // Heap
        Dish dishC = new Dish("dishC", false, 100, Dish.Type.FISH); // Heap
        Dish dishD = new Dish("dishD", false, 100, Dish.Type.FISH); // Heap

        // 인스턴스 변수 a는 final로 선언돼있을 필요도, final처럼 재할당하면 안된다는 제약조건도 적용되지 않는다.
        final Runnable r1 = () -> {
            a = 123;
            System.out.println(a);
        };
        final Runnable rStrA = () -> {
            strA = "123";
            System.out.println(strA);
        };
        final Runnable rStringA = () -> {
            stringA = new String("123");
            System.out.println(strA);
        };
        final Runnable ria = () -> {
            integerA = 123;
            System.out.println(integerA);
        };
        final Runnable ra = () -> {
            dishA.setCalories(123);
            System.out.println(dishA);
        };

        // 지역변수 b는 final로 선언돼있기 때문에 OK
        final Runnable r2 = () -> System.out.println(b);
        final Runnable rStrB = () -> System.out.println(strB);
        final Runnable rStringB = () -> System.out.println(stringB);
        final Runnable rib = () -> System.out.println(integerB);
        final Runnable rb = () -> System.out.println(dishB);

        // 지역변수 c는 final로 선언돼있지 않지만 final을 선언한 것과 같이 변수에 값을 재할당하지 않았으므로 OK
        final Runnable r3 = () -> System.out.println(c);
        final Runnable rStrC = () -> System.out.println(stringC);
        final Runnable rStringC = () -> System.out.println(stringC);
        final Runnable ric = () -> System.out.println(integerC);
        final Runnable rc = () -> System.out.println(dishC);

        // 지역변수 d는 final로 선언돼있지도 않고, 값의 재할당이 일어났으므로 final처럼 동작하지 않기 때문에 X
//        d = 123;
        final Runnable r4 = () -> System.out.println(d);
//        strD = "123"; // TODO Heap? Stack?
        final Runnable rStrD = () -> System.out.println(strD);
//        stringD = new String("123"); // TODO Heap? Stack?
        final Runnable rStringD = () -> System.out.println(stringD);
//        integerD = 123; // TODO Heap? Stack?
        final Runnable rid = () -> System.out.println(integerD);
        dishD.setCalories(123);
        final Runnable rd = () -> System.out.println(dishD);

        Thread t1 = new Thread(r1);
        t1.start();
        Thread ta = new Thread(ra);
        ta.start();
        Thread t2 = new Thread(r2);
        t2.start();
        Thread tb = new Thread(rb);
        tb.start();
        Thread t3 = new Thread(r3);
        t3.start();
        Thread tc = new Thread(rc);
        tc.start();
        Thread t4 = new Thread(r4);
        t4.start();
        Thread td = new Thread(rd);
        td.start();

    }

    public static void main(String[] args) {
        LambdaCapturing lambdaCapturing = new LambdaCapturing();
        lambdaCapturing.test();
    }
}
