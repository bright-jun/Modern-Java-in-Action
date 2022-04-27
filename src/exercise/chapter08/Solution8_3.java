package exercise.chapter08;

import exercise.chapter05.Trader;
import exercise.chapter05.Transaction;

import java.util.*;

public class Solution8_3 {

    public static void main(String[] args) {
        Map<Integer, Integer> m = new HashMap<>();

        m.put(1, 1);
        m.put(2, 2);
        m.put(3, 3);

        m.replace(1, 3);
        m.replace(1, 2, 2);
        m.replaceAll((key, value) -> value + 1);

    }
}
