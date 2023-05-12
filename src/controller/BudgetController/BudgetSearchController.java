package controller.BudgetController;

import model.Budget;
import model.Transaction;
import view.components.BudgetTable;
import view.panels.BudgetPanel.BudgetSearchPanel;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The {@code BudgetSearchController} class is responsible for handling the search functionality
 * in the {@link BudgetTable} component. It interacts with the {@link BudgetSearchPanel} and
 * {@link Budget} to filter and navigate through the transactions based on the search criteria.
 *
 * @author Alessandro Catenacci
 */
public class BudgetSearchController {
    private final Budget budget;
    private final BudgetSearchPanel searchPanel;
    private final BudgetTable budgetTable;
    private List<Transaction> filteredTransactions;
    private int currentTransactionIndex = -1;
    private boolean isSearchActive = false;

    /**
     * Constructs a new {@code BudgetSearchController} with the specified {@link Budget},
     * {@link BudgetSearchPanel}, and {@link BudgetTable}.
     *
     * @param budget      the {@link Budget} instance to perform searches on
     * @param searchPanel the {@link BudgetSearchPanel} instance to handle user input
     * @param budgetTable the {@link BudgetTable} instance to display the search results
     */
    public BudgetSearchController(Budget budget, BudgetSearchPanel searchPanel, BudgetTable budgetTable) {
        this.budget = budget;
        this.searchPanel = searchPanel;
        this.budgetTable = budgetTable;

        // disable the text field and buttons by default
        setSearchPanelEnabled(false);

        searchPanel.getSearchButton().addActionListener(e -> {
            if (isSearchActive) {
                // disable the search panel and remove the highlight
                setSearchPanelEnabled(false);
                searchPanel.getSearchTextField().setText("");
                budgetTable.removeHighlight();
                isSearchActive = false;
            } else {
                // enable the search panel and start the search
                setSearchPanelEnabled(true);
                isSearchActive = true;
                searchPanel.getSearchTextField().requestFocus();
            }
        });

        searchPanel.getSearchTextField().addActionListener(e -> {
            String searchText = searchPanel.getSearchTextField().getText();
            Optional<List<Transaction>> optionalFilteredTransactions =
                    Optional.ofNullable(budget.filterTransactions(budget.getTransactions(),
                            searchText, null, null));
            if (optionalFilteredTransactions.isPresent()) {
                filteredTransactions = optionalFilteredTransactions.get();
                currentTransactionIndex = 0;
                moveToTransaction(currentTransactionIndex);
            } else {
                filteredTransactions = Collections.emptyList();
                currentTransactionIndex = -1;
            }
        });

        searchPanel.getUpButton().addActionListener(e -> {
            if (hasFilteredTransactions() && currentTransactionIndex > 0) {
                currentTransactionIndex--;
                moveToTransaction(currentTransactionIndex);
            }
        });

        searchPanel.getDownButton().addActionListener(e -> {
            if (hasFilteredTransactions() && currentTransactionIndex < filteredTransactions.size() - 1) {
                currentTransactionIndex++;
                moveToTransaction(currentTransactionIndex);
            }
        });
    }

    /**
     * Sets the enabled state of the search panel components.
     *
     * @param enabled {@code true} to enable the search panel components, {@code false} to disable them
     */
    private void setSearchPanelEnabled(boolean enabled) {
        searchPanel.getSearchTextField().setEnabled(enabled);
        searchPanel.getUpButton().setEnabled(enabled);
        searchPanel.getDownButton().setEnabled(enabled);
    }

    /**
     * Moves to the transaction at the specified index and updates the search panel text field.
     *
     * @param index the index of the transaction to move to
     */
    private void moveToTransaction(int index) {
        Transaction transaction = filteredTransactions.get(index);
        searchPanel.getSearchTextField().setText(transaction.getDescription());
        highlightTransaction(transaction);
    }

    /**
     * Checks if there are any filtered transactions.
     *
     * @return {@code true} if there are filtered transactions, {@code false} otherwise
     */
    private boolean hasFilteredTransactions() {
        return !filteredTransactions.isEmpty();
    }

    /**
     * Highlights the specified transaction in the {@link BudgetTable}.
     *
     * @param transaction the {@link Transaction} to highlight
     */
    private void highlightTransaction(Transaction transaction) {
        int rowIndex = budgetTable.getTableModel().getRowIndex(transaction);
        budgetTable.removeHighlight();
        budgetTable.highlightRow(rowIndex, Color.YELLOW);
    }
}
