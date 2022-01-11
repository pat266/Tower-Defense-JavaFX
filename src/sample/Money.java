package sample;

public class Money {
    private double balance;

    public double getBalance() {
        return balance;
    }
    public void addBalance(double balance) {
        // if the balance goes negative
        if (this.balance + balance < 0) {
            throw new IllegalArgumentException("Balance cannot go negative");
        }
        this.balance += balance;
    }

    public void subtractBalance(double balance) {
        if (!isValidTransaction(balance)) {
            throw new IllegalArgumentException("Passed in balance cannot be negative");
        }
        this.balance -= balance;
    }
    public boolean isValidTransaction(double balance) {
        return this.balance - balance >= 0;
    }
    public void set(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Passed in balance cannot be negative");
        }
        this.balance = balance;
    }

    public void setBalanceFromDifficulty(Difficulty difficulty) {
        if (difficulty == null) {
            throw new IllegalArgumentException("Difficulty cannot be null.");
        }
        switch (difficulty) {
        case EASY:
            this.balance = 500;
            break;
        case MEDIUM:
            this.balance = 400;
            break;
        default:
            // case HARD
            this.balance = 300;
            break;
        }
    }
}
