package controller.TransactionController;

import model.Budget;
import model.Transaction;
import view.components.BudgetTableModel;
import view.panels.BudgetPanel.BudgetStatusPanel;
import view.panels.TransactionPanel.TransactionModifyPanel;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The {@link TransactionModifyController} class is responsible for modifying transactions in a budget.
 *
 * @author Alessandro Catenacci
 */
public class TransactionModifyController {
    private Budget budget;
    private final TransactionModifyPanel transactionModifyPanel;
    private final BudgetStatusPanel budgetStatusPanel;
    private final BudgetTableModel tableModel;

    private static final String SELECT_TRANSACTION_MSG = "Please select a transaction to modify.";
    private static final String NO_TRANSACTION_SELECTED_MSG = "No transaction selected";

    /**
     * Constructs a new TransactionModifyController object.
     *
     * @param budget The {@link Budget} that the transactions are being modified in.
     * @param transactionModifyPanel The {@link TransactionModifyPanel}that is being used to modify the transactions.
     * @param budgetStatusPanel The {@link BudgetStatusPanel} that is being updated when transactions are modified.
     * @param tableModel The {@link  view.components.BudgetTableModel} that is being updated when
     *                   transactions are modified.
     */
    public TransactionModifyController(Budget budget, TransactionModifyPanel transactionModifyPanel,
                                       BudgetStatusPanel budgetStatusPanel,
                                       BudgetTableModel tableModel) {
        this.budget = budget;
        this.transactionModifyPanel = transactionModifyPanel;
        this.budgetStatusPanel = budgetStatusPanel;
        this.tableModel = tableModel;

        populateTransactionComboBox();

        transactionModifyPanel.getTransactionComboBox().addActionListener(event -> {
            Object selectedItem = transactionModifyPanel.getTransactionComboBox().getSelectedItem();
            if (selectedItem != null) {
                transactionModifyPanel.getActionButton().setEnabled(true);
                populateFieldsWithTransaction((int) selectedItem);
            } else {
                transactionModifyPanel.getActionButton().setEnabled(false);
                clearFields();
            }
        });

        transactionModifyPanel.getActionButton().addActionListener(event -> {
            Object selectedItem = transactionModifyPanel.getTransactionComboBox().getSelectedItem();
            if (selectedItem != null) {
                try {
                    int selectedId = (int) selectedItem;
                    Transaction selectedTransaction = budget.getTransactionById(selectedId);

                    float newAmount = Float.parseFloat(transactionModifyPanel.getAmountTextField().getText());
                    LocalDateTime newDate = LocalDateTime.parse(transactionModifyPanel.getDateTextField().getText(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                    String newDescription = transactionModifyPanel.getDescriptionTextField().getText();

                    budget.modifyTransaction(selectedTransaction, newAmount, newDate, newDescription);
                    populateTransactionComboBox();
                    tableModel.fireTableDataChanged();
                    budgetStatusPanel.updateBudget();
                    disposeDialog(transactionModifyPanel);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(transactionModifyPanel,
                            "Invalid amount. Please enter a valid number.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(transactionModifyPanel,
                            "Invalid date format. Please enter a date in the format dd/mm/yyyy HH:mm:ss.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(transactionModifyPanel,
                            "Invalid input. Please check your input and try again.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(transactionModifyPanel, SELECT_TRANSACTION_MSG,
                        NO_TRANSACTION_SELECTED_MSG,
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        transactionModifyPanel.getCancelButton().addActionListener(event -> {
            transactionModifyPanel.getTransactionComboBox().setSelectedIndex(-1);
            clearFields();
            disposeDialog(transactionModifyPanel);
        });
    }

    /**
     * Populates the transaction combo box with the transactions of the current budget.
     * If there are no transactions, a message dialog is shown, and the save button is disabled.
     * If there are transactions, the combo box is filled with the transaction IDs and the fields are populated
     * with the transaction data of the selected transaction.
     */
    private void populateTransactionComboBox() {
        if (budget.getTransactions().isEmpty()) {
            JOptionPane.showMessageDialog(transactionModifyPanel,
                    "You need to create at least one transaction before you can modify it.",
                    "No transactions", JOptionPane.INFORMATION_MESSAGE);

            transactionModifyPanel.getActionButton().setEnabled(false);
            transactionModifyPanel.getTransactionComboBox().removeAllItems();
        } else {
            transactionModifyPanel.getTransactionComboBox().removeAllItems();
            for (Transaction transaction : budget.getTransactions()) {
                transactionModifyPanel.getTransactionComboBox().addItem(transaction.getId());
            }
            if (transactionModifyPanel.getTransactionComboBox().getItemCount() > 0) {
                Integer selectedItem = (Integer) transactionModifyPanel.getTransactionComboBox().getSelectedItem();
                if (selectedItem != null) {
                    int selectedId = selectedItem;
                    populateFieldsWithTransaction(selectedId);
                } else {
                    transactionModifyPanel.getTransactionComboBox().setSelectedIndex(0);
                    populateFieldsWithTransaction((int) transactionModifyPanel.getTransactionComboBox().getSelectedItem());
                }
                transactionModifyPanel.getActionButton().setEnabled(true);
            }
        }
    }

    /**
     * Populates the fields in the transactionModifyPanel with the data of the transaction
     * with the given transactionId.
     *
     * @param transactionId the ID of the transaction to populate the fields with
     */
    private void populateFieldsWithTransaction(int transactionId) {
        Transaction selectedTransaction = budget.getTransactionById(transactionId);
        transactionModifyPanel.getAmountTextField().setText(Float.toString(selectedTransaction.getAmount()));

        // Format the transaction date as a string using a DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = selectedTransaction.getTransactionDate().format(formatter);
        transactionModifyPanel.getDateTextField().setText(formattedDate);

        transactionModifyPanel.getDescriptionTextField().setText(selectedTransaction.getDescription());
    }


    /**
     * Clears the fields in the transactionModifyPanel.
     */
    private void clearFields() {
        transactionModifyPanel.getAmountTextField().setText("");
        transactionModifyPanel.getDateTextField().setText("");
        transactionModifyPanel.getDescriptionTextField().setText("");
    }

    /**
     * Disposes of the dialog containing the given panel.
     *
     * @param panel the panel contained within the dialog to dispose of
     */
    private void disposeDialog(JPanel panel) {
        JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(panel);
        if (dialog != null) {
            dialog.dispose();
        }
    }

    /**
     * Sets the budget to be used by this class.
     *
     * @param budget the budget to be used
     */
    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}
