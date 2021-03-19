package raffle;

public class Currency {
    private int amount;

    public Currency(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void addToAmount(int currencyValue) {
        this.amount += currencyValue;
    }
}
