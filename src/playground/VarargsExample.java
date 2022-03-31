package playground;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class VarargsExample {

    public static <T> boolean contains(T target, T... a) {
        HashSet<T> hs;
        if (a == null) {
            hs = new HashSet<T>();
            hs.add(null);
        } else {
            hs = new HashSet<T>(Arrays.asList(a));
        }
        return hs.contains(target);
    }

    public static void main(String[] args) {
//        System.out.println(contains(null, )); // error
        System.out.println(contains("A", "A"));
        System.out.println(contains("C", "A", "B"));
        System.out.println(contains("C", null));
        System.out.println(contains("C", null, null));
        System.out.println(contains("C", null, null, "A"));
        System.out.println("====================");
        System.out.println(contains("A", Arrays.asList("A").toArray(new String[0])));
        System.out.println(contains("A", Arrays.asList("A","B").toArray(new String[0])));
        System.out.println(contains("A", Arrays.asList().toArray(new String[0])));
//        System.out.println(contains("A", Arrays.asList(null).toArray(new String[0]))); // NPE
        System.out.println(contains("A", Arrays.asList(null,"A").toArray(new String[0])));
        System.out.println("====================");
        System.out.println(contains(null, "A"));
        System.out.println(contains(null, "A", "B"));
        System.out.println(contains(null, null));
        System.out.println(contains(null, null, null));
        System.out.println(contains(null, null, null, "A"));
        System.out.println("====================");
        System.out.println(contains(null, Arrays.asList("A").toArray(new String[0])));
        System.out.println(contains(null, Arrays.asList("A","B").toArray(new String[0])));
        System.out.println(contains(null, Arrays.asList().toArray(new String[0])));
//        System.out.println(contains(null, Arrays.asList(null).toArray(new String[0]))); // NPE
        System.out.println(contains(null, Arrays.asList(null,"A").toArray(new String[0])));

//        List<String> a = Arrays.asList(null); // NPE
        String nullStr = null;
        return;
    }
}
