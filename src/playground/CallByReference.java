package playground;

public class CallByReference {

    static class MyInteger {

        int val;
    }

    private void swap(MyInteger a, MyInteger b) {
        int temp = a.val;
        a.val = b.val;
        b.val = temp;
    }

    private void swap(Integer a, Integer b) {
        // Integer is immutable so can't change its value
        Integer temp = a;
        a = b;
        b = temp;
    }

    public static void main(String[] args) {
        CallByReference callByReference = new CallByReference();
        Integer a = 1;
        Integer b = 9;
        callByReference.swap(a, b);

        MyInteger ma = new MyInteger();
        ma.val = 1;
        MyInteger mb = new MyInteger();
        mb.val = 9;
        callByReference.swap(ma, mb);

        return;
    }
}
