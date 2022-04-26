package exercise.chapter08;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Solution8_4 {

    public static void main(String[] args) {
        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
        long parallelismThreshold = 1;
        Optional<Long> maxValue =
            Optional.ofNullable(map.reduceValues(parallelismThreshold, Long::max));
        // int, long, double 등의 기본값에는 전용 each reduce 연산이 제공되므로 reduceValuesToInt,
        // reduceKeysToLong 등을 이용하면 박싱 작업을 할 필요가 없고 효율적으로 작업을 처리할 수
        // 있다.

    }
}
