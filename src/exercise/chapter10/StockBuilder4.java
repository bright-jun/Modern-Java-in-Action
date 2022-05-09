package exercise.chapter10;

public class StockBuilder4 {
    private final TradeBuilder4 builder4;
    private final Trade trade4;
    private final Stock stock4 = new Stock();

    public StockBuilder4(TradeBuilder4 builder, Trade trade, String symbol){
        this.builder4 = builder;
        this.trade4 = trade;
        stock4.setSymbol(symbol);
    }

    public TradeBuilder4 on4(String market) {
        stock4.setMarket(market);
        trade4.setStock(stock4);
        return builder4;
    }
}
