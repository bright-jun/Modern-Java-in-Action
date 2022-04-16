package playground;

import java.util.ArrayList;
import java.util.List;

public class Overflow {

    public static int depth = 0;

    public static void createStackOverflow() {
        if(depth == 100000000) {
            return;
        }
        try {
            depth++;
            createStackOverflow();
        } catch (Throwable e) {
            System.out.println(depth);
            e.printStackTrace();
        }
    }

    public static void createHeapOverflow() {
        List<Object> listObj = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Byte[] bytes = new Byte[1 * 1024 * 1024];
            listObj.add(bytes);
        }
    }

    public static void main(String[] args) {
        createStackOverflow();
        createHeapOverflow();
    }
}
