package exercise.chapter06;


public class Transaction {

    private final Currency currency;
    private final Trader trader;
    private final int year;
    private final int value;

    public Transaction(Currency currency, Trader trader, int year, int value) {
        this.currency = currency;
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Trader getTrader() {
        return this.trader;
    }

    public int getYear() {
        return this.year;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("currency=").append(currency);
        sb.append(", trader=").append(trader);
        sb.append(", year=").append(year);
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}