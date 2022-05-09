package exercise.chapter10;

public class TradeBuilder4 {
    public Trade trade4 = new Trade();

    public TradeBuilder4 quantity4(int quantity) {
        trade4.setQuantity(quantity);
        return this;
    }

    public TradeBuilder4 at4(double price) {
        trade4.setPrice(price);
        return this;
    }

    public StockBuilder4 stock4(String symbol) {
        return new StockBuilder4(this, trade4, symbol);
    }
}
