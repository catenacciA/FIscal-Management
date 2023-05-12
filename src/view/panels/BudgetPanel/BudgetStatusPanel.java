package view.panels.BudgetPanel;

import model.Budget;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * A panel that displays the current status of a Budget object, including the number of transactions and the total amount spent.
 *
 * @author Alessandro Catenacci
 */
public class BudgetStatusPanel extends JPanel {
    private JPanel transactionCountPanel;
    private JPanel totalAmountSpentPanel;
    private JLabel transactionCountLabel;
    private JLabel totalAmountSpentLabel;
    private Budget budget;

    private static final int DIGIT_GROUPING_SIZE = 3;
    private static final int HORIZONTAL_GAP = 10;

    /**
     * Creates a new BudgetStatusPanel with the specified Budget object.
     *
     * @param budget the Budget object to display the status of
     */
    public BudgetStatusPanel(Budget budget) {
        this.budget = budget;
        setLayout(new GridLayout(1, 2, HORIZONTAL_GAP, 0)); // Use GridLayout to place two panels side by side with 10 pixels of horizontal gap
        createTransactionCountPanel();
        createTotalAmountSpentPanel();
        updateBudget();
    }

    /**
     * Creates the panel that displays the number of transactions.
     */
    private void createTransactionCountPanel() {
        transactionCountPanel = new JPanel();
        transactionCountLabel = new JLabel();
        transactionCountPanel.add(transactionCountLabel);
        add(transactionCountPanel);
    }

    /**
     * Creates the panel that displays the total amount spent.
     */
    private void createTotalAmountSpentPanel() {
        totalAmountSpentPanel = new JPanel();
        totalAmountSpentLabel = new JLabel();

        // Create a decimal format with digit group separators
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance();
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(DIGIT_GROUPING_SIZE);

        totalAmountSpentPanel.add(totalAmountSpentLabel);
        add(totalAmountSpentPanel);
    }

    /**
     * Updates the status of the Budget object displayed on this panel.
     * If the Budget object is null, or has no transactions, the labels will display empty strings.
     */
    public void updateBudget() {
        if (budget != null && budget.getTransactions() != null) {
            transactionCountLabel.setText("Number of Transactions: " + budget.getTransactions().size());

            // Format the total amount with digit group separators
            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance();
            decimalFormat.setGroupingUsed(true);
            decimalFormat.setGroupingSize(DIGIT_GROUPING_SIZE);
            String formattedTotalAmount = decimalFormat.format(budget.getTotalAmount());
            totalAmountSpentLabel.setText("Total Amount Spent: " + formattedTotalAmount);
        } else {
            transactionCountLabel.setText("");
            totalAmountSpentLabel.setText("");
        }
    }

    /**
     * Sets the Budget object displayed on this panel to the specified object and updates the status labels.
     *
     * @param budget the new Budget object to display the status of
     */
    public void setBudget(Budget budget) {
        this.budget = budget;
        updateBudget();
    }
}
