package exercise.chapter13;

public class Quiz13_3 {

    interface A{
        default Number getNumber(){
            return 10;
        }
    }
    interface B{
        default Integer getNumber(){
            return 42;
        }
    }

    public class C implements B, A {

        @Override
        public Integer getNumber() {
            // 구분 안해주면 컴파일 에러 발생
            return B.super.getNumber();
        }
    }

    public static void main(String[] args) {
        Quiz13_3 q = new Quiz13_3();
        System.out.println(q.new C().getNumber());
    }
}
