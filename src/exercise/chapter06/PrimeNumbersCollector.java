package exercise.chapter06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 누적자와 최종 결과의 형식이 Map<Boolean, List<Integer>> 인 컬렉터를 구현
 */
public class PrimeNumbersCollector implements
    Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

    /**
     * <PRE>
     * 누적자를 만드는 함수를 반환 누적자로 사용할 맵을 만들면서 true, false 키와 빈 리스트로 초기화
     * </PRE>
     *
     * @return the supplier
     */
    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<>() {{
            put(true, new ArrayList<Integer>());
            put(false, new ArrayList<Integer>());
        }};
    }

    /**
     * <PRE>
     * 수집 과정에서 빈 리스트에 각각 소수와 비소수를 추가 지금까지 발견한 소수를 포함하는 누적자에 접근할 수 있다 isPrime의 호출 결과로 소수 리스트 또는 비소수
     * 리스트 중 알맞은 리스트로 candidate를 추가한다.
     * </PRE>
     *
     * @return the bi consumer
     */
    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {
            acc.get(Solution6_6.isPrimeOpt(acc.get(true), candidate))
                .add(candidate);
        };
    }

    /**
     * <PRE>
     * 병렬 실행할 수 있는 컬렉터 두 번째 맵의 소수 리스트와 비소수 리스트의 모든 수를 첫 번째 맵에 추가하는 연산
     * 알고리즘 자체가 순차적이어서 실제로는 적용안되지만(characteristics에 CONCURRENT 아님)
     * 학습용 임의구현
     * </PRE>
     *
     * @return the binary operator
     */
    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (Map<Boolean, List<Integer>> map1,
            Map<Boolean, List<Integer>> map2) -> {
            map1.get(true).addAll(map2.get(true));
            map1.get(false).addAll(map2.get(false));
            return map1;
        };
    }

    /**
     * 컬렉터 결과 형식과 같으므로 변환 과정이 필요 없다
     *
     * @return the function
     */
    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    /**
     * IDENTITY_FINISH
     *
     * @return the set
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
    }
}
