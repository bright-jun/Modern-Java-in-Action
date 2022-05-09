package exercise.chapter10;

import static exercise.chapter10.MethodChainingOrderBuilder.*;
import static exercise.chapter10.NestedFunctionOrderBuilder.*;
import static exercise.chapter10.LambdaOrderBuilder.*;
import static exercise.chapter10.MixedBuilder.*;
import static exercise.chapter10.Tax.*;

import exercise.chapter10.Trade.Type;

public class Solution10_3 {

    public static void main(String[] args) {
        Order order = new Order();
        order.setCustomer("BigBank");

        Trade trade1 = new Trade();
        trade1.setType(Type.BUY);

        Stock stock1 = new Stock();
        stock1.setSymbol("IBM");
        stock1.setMarket("NYSE");

        trade1.setStock(stock1);
        trade1.setPrice(125.00);
        trade1.setQuantity(80);
        order.addTrade(trade1);

        Trade trade2 = new Trade();
        trade2.setType(Type.BUY);

        Stock stock2 = new Stock();
        stock2.setSymbol("GOOGLE");
        stock2.setMarket("NASDAQ");

        trade2.setStock(stock2);
        trade2.setPrice(375.00);
        trade2.setQuantity(50);
        order.addTrade(trade2);

        // Method chaining
        Order order1 = forCustomer("BigBank")
            .buy(80)
            .stock("IBM")
            .on("NYSE")
            .at(125.00)
            .sell(50)
            .stock("GOOGLE")
            .on("NASDAQ")
            .at(375.00)
            .end();

        // Using nested functions
        Order order2 = order("BigBank",
            buy(80,
                stock("IBM", on("NYSE")),
                at(125.00)),
            sell(50,
                stock("GOOGLE", on("NASDAQ")),
                at(375.00))
        );

        // Function sequencing with lambda expressions
        Order order3 = order3(o -> {
            o.forCustomer3("BigBank");
            o.buy3(t -> {
                t.quantity3(80);
                t.price3(125.00);
                t.stock3(s -> {
                    s.symbol3("IBM");
                    s.market3("NYSE");
                });
            });
            o.sell3(t -> {
                t.quantity3(50);
                t.price3(375.00);
                t.stock3(s -> {
                    s.symbol3("GOOGLE");
                    s.market3("NASDAQ");
                });
            });
        });

        // Putting it all together
        Order order4 =
            forCustomer4("BigBank",
                buy4(t -> t.quantity4(80)
                    .stock4("IBM")
                    .on4("NYSE")
                    .at4(125.00)),
                sell4(t -> t.quantity4(50)
                    .stock4("GOOGLE")
                    .on4("NASDAQ")
                    .at4(125.00)));

        // Using method references in a DSL
        // 불리언 변수의 올바른 순서를 기억하기도 어려우며 어떤 세금이 적용되었는지도 파악하기 어렵다
        double value1 = calculate1(order, true, false, true);
        // 유창한 코딩
        double value2 = new TaxCalculator().withTaxRegional()
            .withTaxSurcharge()
            .calculate(order);
        // 코드가 장황, 확장성 추가
        double value3 = new TaxCalculator().with(Tax::regional)
            .with(Tax::surcharge)
            .calculate(order);
    }
}
