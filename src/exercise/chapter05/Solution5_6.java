package exercise.chapter05;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Solution5_6 {

    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
        );

        // TODO
//        1. 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.
        List<Transaction> tr2011 =
            transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(toList());

//        2. 거래자가 근무하는 모든 도시를 중복 없이 나열하시오.
        List<String> cities1 =
            transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(toList());

        Set<String> cities2 =
            transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .collect(toSet());

//        3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.
        List<Trader> traders =
            transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(toList());

//        4. 모든 거래자의 이름을 알파벳순으로 정 렬해서 반환하시오.
        String traderStr1 =
            transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2);

        String traderStr2 =
            transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .collect(joining());

//        5. 밀라노에 거래자가 있는가?
        boolean milanBased =
            transactions.stream()
                .anyMatch(transaction -> transaction.getTrader()
                    .getCity()
                    .equals("Milan"));

//        6. 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력하시오.
        transactions.stream()
            .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
            .map(Transaction::getValue)
            .forEach(System.out::println);

//        7. 전체 트랜잭션 중 최댓값은 얼마인가?
        Optional<Integer> highestValue =
            transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);

//        8. 전체 트랜잭션 중 최솟값은 얼마인가?
        Optional<Transaction> smallestTransaction1 =
            transactions.stream()
                .reduce((t1, t2) ->
                    t1.getValue() < t2.getValue() ? t1 : t2);

        Optional<Transaction> smallestTransaction2 =
            transactions.stream()
                .min(comparing(Transaction::getValue));

        return;
    }
}
