package playground;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class VarargsExample {

    public static <T> boolean contains(T target, T... a) {
        if (a == null) {
            if(target == null){
                return true;
            }
            return false;
        }

        return new HashSet<T>(Arrays.asList(a)).contains(target);
    }

    public static void main(String[] args) {
//        System.out.println(contains(null, )); // error
        System.out.println(contains("A", "A"));
        System.out.println(contains("C", "A", "B"));
        System.out.println(contains("C", null));
        System.out.println(contains("C", null, null));
        System.out.println(contains("C", null, null, "A"));

        System.out.println(contains(null, "A"));
        System.out.println(contains(null, "A", "B"));
        System.out.println(contains(null, null));
        System.out.println(contains(null, null, null));
        System.out.println(contains(null, null, null, "A"));

//        List<String> a = Arrays.asList(null); // NPE

        return;
    }
}
