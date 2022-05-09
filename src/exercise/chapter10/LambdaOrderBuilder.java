package exercise.chapter10;

import java.util.function.Consumer;

public class LambdaOrderBuilder {

    private Order order3 = new Order();

    public static Order order3(Consumer<LambdaOrderBuilder> consumer) {
        LambdaOrderBuilder builder = new LambdaOrderBuilder();
        consumer.accept(builder);
        return builder.order3;
    }

    public void forCustomer3(String customer) {
        order3.setCustomer(customer);
    }

    public void buy3(Consumer<TradeBuilder3> consumer) {
        trade3(consumer, Trade.Type.BUY);
    }

    public void sell3(Consumer<TradeBuilder3> consumer) {
        trade3(consumer, Trade.Type.SELL);
    }

    private void trade3(Consumer<TradeBuilder3> consumer, Trade.Type type) {
        TradeBuilder3 builder = new TradeBuilder3();
        builder.trade3.setType(type);
        consumer.accept(builder);
        order3.addTrade(builder.trade3);
    }
}


