package controller.TransactionController;

import model.Budget;
import model.Transaction;
import view.components.BudgetTableModel;
import view.panels.BudgetPanel.BudgetStatusPanel;
import view.panels.TransactionPanel.TransactionAddPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Controller class for adding transactions to the budget.
 * Handles adding new transactions, updating the budget table, and updating budget status panel.
 * Implements ActionListener to handle user actions.
 *
 @author Alessandro Catenacci
 */
public class TransactionAddController implements ActionListener {

    private static final String ADD = "Add";
    private static final String CANCEL = "Cancel";

    private Budget budget;
    private BudgetTableModel tableModel;
    private TransactionAddPanel transactionAddPanel;
    private BudgetStatusPanel budgetStatusPanel;

    /**
     * Constructs an TransactionAddController with the given Budget, BudgetTableModel, TransactionAddPanel,
     * and BudgetStatusPanel.
     *
     * @param budget the budget to add transactions to
     * @param tableModel the table model for the budget table
     * @param transactionAddPanel the panel for adding transactions
     * @param budgetStatusPanel the panel for displaying budget status
     */
    public TransactionAddController(Budget budget, BudgetTableModel tableModel,
                                    TransactionAddPanel transactionAddPanel, BudgetStatusPanel budgetStatusPanel) {
        this.budget = budget;
        this.tableModel = tableModel;
        this.transactionAddPanel = transactionAddPanel;
        this.budgetStatusPanel = budgetStatusPanel;

        transactionAddPanel.getActionButton().addActionListener(this);
        transactionAddPanel.getCancelButton().addActionListener(this);
    }

    /**
     * Handles user actions. If the Add button is clicked, gets the form values and adds a new transaction
     * to the budget. If the Cancel button is clicked, disposes of the TransactionAddPanel.
     *
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ADD)) {
            // Get form values and add new transaction to budget
            Date date = (Date) transactionAddPanel.getDateSpinner().getValue();
            Instant instant = date.toInstant();
            LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
            float amount;
            try {
                amount = Float.parseFloat(transactionAddPanel.getAmountField().getText());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null,
                        "Invalid amount entered", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            String description = transactionAddPanel.getDescriptionField().getText();
            budget.addTransaction(new Transaction(amount, localDateTime, description));
            tableModel.fireTableDataChanged();
            // Update the entriesLabel and totalLabel in the BudgetStatusPanel
            budgetStatusPanel.updateBudget();

            int selection = JOptionPane.showOptionDialog(null,
                    "Transaction added successfully. Do you want to add another transaction?",
                    "Add Transaction", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (selection == JOptionPane.YES_OPTION) {
                transactionAddPanel.clearForm();
            } else {
                disposeDialog(transactionAddPanel);
            }
        } else if (e.getActionCommand().equals(CANCEL)) {
            disposeDialog(transactionAddPanel);
        }
    }

    /**
     * Disposes of the JDialog that contains the given JPanel.
     *
     * @param panel the panel to dispose of
     */
    private void disposeDialog(JPanel panel) {
        JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(panel);
        dialog.dispose();
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}




