package exercise.chapter08;

import static java.util.Map.entry;
import static java.util.stream.Collectors.toSet;

import exercise.chapter05.Trader;
import exercise.chapter05.Transaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Solution8_2 {

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

        // #1.
        for (Transaction transaction : transactions) {
            if (transaction.getYear() > 2010) {
                // TODO ConcurrentModificationException이 발생하지 않고, UnsupportedOperationException이 발생함
//                transactions.remove(transaction);
            }
        }

        // #2. #1 과 동일
        // 두 개의 개별 객체가 컬렉션을 관리한다는 사실을 주목하자.
        // Iterator 객체，next(), hasNext()를 이용해 소스를 질의한다.
        // Collection 객체 자체, remove()를 호출해 요소를 삭제한다
        // 결과적으로 반복자의 상태는 컬렉션의 상태와 서로 동기화되지 않는다.
//        for (Iterator<Transaction> iterator = transactions.iterator(); iterator.hasNext(); ) {
//            Transaction transaction = iterator.next();
//            if (transaction.getYear() > 2010) {
//                transactions.remove(transaction);
//            }
//        }

        // #3.
        // Iterator 객체를 명시적으로 사용하고 그 객체의 remove() 메서드를 호출함으로 이 문제를 해결할 수 있다.
        for (Iterator<Transaction> iterator = transactions.iterator(); iterator.hasNext(); ) {
            Transaction transaction = iterator.next();
            if (transaction.getYear() > 2010) {
                // TODO UnsupportedOperationException이 발생함
//                iterator.remove();
            }
        }

        // #4.
        // TODO UnsupportedOperationException이 발생함
//        transactions.removeIf(transaction -> transaction.getYear() > 2010);
    }
}
