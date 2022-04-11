package exercise.chapter06;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution6_1 {

    public static void main(String[] args) {
        Currency won = new Currency("Won");
        Currency dollar = new Currency("Dollar");
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        List<Transaction> transactions = Arrays.asList(
            new Transaction(won, brian, 2011, 300),
            new Transaction(won, raoul, 2012, 1000),
            new Transaction(won, raoul, 2011, 400),
            new Transaction(won, mario, 2012, 710),
            new Transaction(dollar, mario, 2012, 700),
            new Transaction(dollar, alan, 2012, 950)
        );

        Map<Currency, List<Transaction>> transactionsByCurrencies1 = new HashMap<>();
        for (Transaction transaction : transactions) {
            Currency currency = transaction.getCurrency();
            List<Transaction> transactionsForCurrency = transactionsByCurrencies1.get(currency);
            if (transactionsForCurrency == null) {
                transactionsForCurrency = new ArrayList<>();
                transactionsByCurrencies1.put(currency, transactionsForCurrency);
            }
            transactionsForCurrency.add(transaction);
        }

        Map<Currency, List<Transaction>> transactionsByCurrencies2 =
            transactions.stream()
                .collect(groupingBy(Transaction::getCurrency));

        return;
    }
}
