package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * The Transaction class represents a financial transaction, which is identified by a unique identifier,
 * has a transaction date, description, and amount.
 *
 * @author Alessandro Catenacci
 */

public class Transaction implements Serializable {
    private final int id;
    private LocalDateTime transactionDate;
    private String description;
    private float amount;
    private static Random random = new Random();

    /**
     * Creates a new transaction with the given amount, date and time, and description.
     *
     * @param amount          The amount of this transaction.
     * @param transactionDate The date and time of this transaction.
     * @param description     The description of this transaction.
     */
    public Transaction(float amount, LocalDateTime transactionDate, String description) {
        this.id = random.nextInt(999) + 1;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public float getAmount() {
        return amount;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format("Transaction ID: T%03d, Date: %s, Amount: %s, Description: %s",
                id, transactionDate, amount, description);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o The reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id;
    }
}