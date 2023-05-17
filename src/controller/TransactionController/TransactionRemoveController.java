package controller.TransactionController;

import model.Budget;
import model.Transaction;
import view.components.BudgetTableModel;
import view.panels.BudgetPanel.BudgetStatusPanel;
import view.panels.TransactionPanel.TransactionRemovePanel;

import javax.swing.*;

/**
 * The TransactionRemoveController class is responsible for managing the interaction between the
 * TransactionRemovePanel view and the Budget model. It allows the user to remove transactions from
 * the budget and update the budget status panel and table model accordingly.
 *
 * @author Alessandro Catenacci
 */
public class TransactionRemoveController {
    private final TransactionRemovePanel view;
    private Budget budget;
    private final BudgetTableModel tableModel;
    private final BudgetStatusPanel statusPanel;

    /**
     * Constructs a new TransactionRemoveController with the given view, budget, tableModel,
     * and statusPanel.
     *
     * @param view         the TransactionRemovePanel view
     * @param budget       the Budget model
     * @param tableModel   the BudgetTableModel used to display transactions
     * @param statusPanel  the BudgetStatusPanel used to display the budget status
     */
    public TransactionRemoveController(TransactionRemovePanel view, Budget budget, BudgetTableModel tableModel,
                                       BudgetStatusPanel statusPanel) {
        this.view = view;
        this.budget = budget;
        this.tableModel = tableModel;
        this.statusPanel = statusPanel;

        view.getActionButton().addActionListener(e -> {
            try {
                int row = view.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(view, "No row selected",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirmResult = JOptionPane.showConfirmDialog(view,
                        "Are you sure you want to delete this transaction?",
                        "Confirm Transaction Deletion", JOptionPane.YES_NO_OPTION);
                if (confirmResult != JOptionPane.YES_OPTION) {
                    return;
                }

                Transaction transaction = tableModel.getTransaction(row);
                if (transaction == null) {
                    JOptionPane.showMessageDialog(view, "No transaction selected",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                budget.deleteTransaction(transaction);

                // Update the status panel and table model
                statusPanel.updateBudget();
                tableModel.fireTableDataChanged();

                int removeMoreResult = JOptionPane.showConfirmDialog(view,
                        "Transaction removed. Do you want to remove more transactions?",
                        "Remove More Transactions", JOptionPane.YES_NO_OPTION);

                if (removeMoreResult != JOptionPane.YES_OPTION) {
                    disposeDialog(view);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error occurred: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        view.getClearButton().addActionListener(e -> {
            try {
                int confirmResult = JOptionPane.showConfirmDialog(view,
                        "Are you sure you want to delete all transactions?",
                        "Confirm All Transactions Deletion", JOptionPane.YES_NO_OPTION);
                if (confirmResult != JOptionPane.YES_OPTION) {
                    return;
                }

                budget.removeAllTransactions();
                //Update the status panel
                tableModel.fireTableDataChanged();
                statusPanel.updateBudget();

                JOptionPane.showMessageDialog(view, "All transactions removed successfully",
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                disposeDialog(view);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error occurred: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        view.getCancelButton().addActionListener(e -> disposeDialog(view));
    }

    /**
     * Disposes the given panel.
     *
     * @param panel the panel to dispose
     */
    private void disposeDialog(JPanel panel) {
        JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(panel);
        dialog.dispose();
    }

    /**
     * Sets the budget.
     *
     * @param budget the budget
     */
    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}