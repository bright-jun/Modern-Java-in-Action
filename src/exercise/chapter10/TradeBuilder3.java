package exercise.chapter10;

import java.util.function.Consumer;

public class TradeBuilder3 {
    public Trade trade3 = new Trade();

    public void quantity3(int quantity) {
        trade3.setQuantity( quantity );
    }

    public void price3(double price) {
        trade3.setPrice( price );
    }

    public void stock3(Consumer<StockBuilder3> consumer) {
        StockBuilder3 builder = new StockBuilder3();
        consumer.accept(builder);
        trade3.setStock(builder.stock3);
    }
}