package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A class that represents a budget and all its transactions.
 * The transactions are stored in an ArrayList.
 * A thread-safe hash map is used to keep track of the transaction indices.
 *
 * @author Alessandro Catenacci
 */
public class Budget implements Serializable {
    private final List<Transaction> transactions;
    private final Map<Transaction, Integer> indices;

    /**
     * Creates an empty Budget.
     */
    public Budget() {
        transactions = new ArrayList<>();
        indices = Collections.synchronizedMap(new HashMap<>());
    }

    /**
     * Gets all the transactions in this budget.
     *
     * @return A list of all transactions in this budget.
     */
    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    /**
     * Sets the transactions in this budget.
     *
     * @param transactions The transactions to set.
     */
    public void setTransactions(Collection<Transaction> transactions) {
        this.transactions.clear();
        this.transactions.addAll(transactions);
        indices.clear();
        for (int i = 0; i < this.transactions.size(); i++) {
            indices.put(this.transactions.get(i), i);
        }
    }

    /**
     * Adds a transaction to this budget.
     *
     * @param transaction The transaction to add.
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        indices.put(transaction, transactions.size() - 1);
    }

    /**
     * Deletes a transaction from this budget.
     *
     * @param transaction The transaction to delete.
     * @throws IllegalArgumentException If the transaction is not found.
     */
    public void deleteTransaction(Transaction transaction) {
        final int index = indices.remove(transaction);
        if (index == -1) {
            throw new IllegalArgumentException("Transaction not found");
        }
        transactions.remove(index);
        indices.entrySet().removeIf(e -> e.getValue() > index);
        for (int i = index; i < transactions.size(); i++) {
            indices.put(transactions.get(i), i);
        }
    }

    /**
     * Modifies a transaction in this budget.
     *
     * @param transaction    The transaction to modify.
     * @param newAmount      The new amount for the transaction.
     * @param newDate        The new date for the transaction.
     * @param newDescription The new description for the transaction.
     * @throws IllegalArgumentException If the transaction is not found.
     */
    public void modifyTransaction(
            Transaction transaction,
            float newAmount,
            LocalDateTime newDate,
            String newDescription) {
        final Integer index = indices.get(transaction);
        if (index == null) {
            throw new IllegalArgumentException("Transaction not found");
        }
        transaction.setAmount(newAmount);
        transaction.setTransactionDate(newDate);
        transaction.setDescription(newDescription);
    }

    /**
     * Searches for a transaction in the Budget object.
     *
     * @param transaction the transaction to search for
     * @return an Optional containing the found transaction, or empty if not found
     */
    public Optional<Transaction> searchTransaction(Transaction transaction) {
        final Integer index = indices.get(transaction);
        if (index == null) {
            return Optional.empty();
        }
        return Optional.of(transactions.get(index));
    }

    /**
     * Filters the transactions in the Budget object based on description and/or start date and end date.
     *
     * @param transactions the transactions to filter
     * @param description  the description to filter the transactions by, can be null
     * @param startDate    the start date to filter the transactions by, can be null
     * @param endDate      the end date to filter the transactions by, can be null
     * @return a List of filtered transactions
     */
    public List<Transaction> filterTransactions(List<Transaction> transactions, String description,
                                                LocalDateTime startDate, LocalDateTime endDate) {
        Objects.requireNonNull(transactions, "Transactions must not be null");

        if (description != null && description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty or whitespace");
        }

        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        return transactions.stream()
                .filter(t -> (description == null || t.getDescription()
                        .toLowerCase().contains(description.toLowerCase()))
                        && (startDate == null || t.getTransactionDate().isAfter(startDate))
                        && (endDate == null || t.getTransactionDate().isBefore(endDate)))
                .collect(Collectors.toList());
    }

    public void removeAllTransactions() {
        transactions.clear();
        indices.clear();
    }

    /**
     * Returns the total amount of all transactions in the budget
     *
     * @return the total amount of all transactions in the budget
     */
    public float getTotalAmount() {
        return transactions
                .stream().map(Transaction::getAmount)
                .reduce(0f, Float::sum);
    }

    /**
     * Returns the transaction with the given id.
     *
     * @param id The id of the transaction to retrieve.
     * @return The transaction with the given id, or null if not found.
     */
    public Transaction getTransactionById(int id) {
        for (Transaction transaction : transactions) {
            if (transaction.getId() == id) {
                return transaction;
            }
        }
        return null;
    }

    /**
     * Returns a list of all transaction ids in the budget.
     *
     * @return A list of all transaction ids in the budget.
     */
    public List<Integer> getTransactionIds() {
        return transactions.stream().map(Transaction::getId).collect(Collectors.toList());
    }
}