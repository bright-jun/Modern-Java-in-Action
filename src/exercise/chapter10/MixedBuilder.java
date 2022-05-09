package exercise.chapter10;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class MixedBuilder {

    public static Order forCustomer4(String customer,
        TradeBuilder4... builders) {
        Order order4 = new Order();
        order4.setCustomer(customer);
        Stream.of(builders).forEach(b -> order4.addTrade(b.trade4));
        return order4;
    }

    public static TradeBuilder4 buy4(Consumer<TradeBuilder4> consumer) {
        return buildTrade4(consumer, Trade.Type.BUY);
    }

    public static TradeBuilder4 sell4(Consumer<TradeBuilder4> consumer) {
        return buildTrade4(consumer, Trade.Type.SELL);
    }

    private static TradeBuilder4 buildTrade4(Consumer<TradeBuilder4> consumer,
        Trade.Type buy) {
        TradeBuilder4 builder = new TradeBuilder4();
        builder.trade4.setType(buy);
        consumer.accept(builder);
        return builder;
    }
}
