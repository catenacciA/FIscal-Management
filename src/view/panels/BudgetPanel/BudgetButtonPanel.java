package view.panels.BudgetPanel;

import controller.BudgetController.BudgetExportController;
import controller.BudgetController.BudgetFilterController;
import controller.BudgetController.BudgetPersistenceController;
import controller.TransactionController.TransactionAddController;
import controller.TransactionController.TransactionModifyController;
import controller.TransactionController.TransactionRemoveController;
import model.Budget;
import view.components.BudgetTableModel;
import view.panels.TransactionPanel.TransactionAddPanel;
import view.panels.TransactionPanel.TransactionModifyPanel;
import view.panels.TransactionPanel.TransactionRemovePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * A custom panel containing buttons for managing budget transactions and file operations.
 * This panel extends JToolBar and provides buttons for adding, editing, removing,
 * and filtering transactions, as well as managing file and export operations.
 *
 * @author Alessandro Catenacci
 */
public class BudgetButtonPanel extends JToolBar {
    private final JButton addButton;
    private final JButton deleteButton;
    private final JButton editButton;
    private final JButton filterButton;
    private final BudgetStatusPanel budgetStatusPanel;
    private JButton fileButton;
    private JButton exportButton;
    private Budget budget;

    /**
     * Constructs a new BudgetButtonPanel with the specified budget, table model, frame, and budget status panel.
     *
     * @param budget            the Budget instance this panel will manage
     * @param tableModel        the table model that represents the budget transactions
     * @param frame             the parent frame containing this panel
     * @param budgetStatusPanel the BudgetStatusPanel that displays budget information
     */
    public BudgetButtonPanel(Budget budget, BudgetTableModel tableModel, JFrame frame,
                             BudgetStatusPanel budgetStatusPanel) {
        setFloatable(false);

        this.budgetStatusPanel = budgetStatusPanel;
        this.budget = budget;

        addButton = createButton("Add", e -> showAddTransactionDialog(budget, tableModel, frame));
        deleteButton = createButton("Delete", e -> showRemoveTransactionDialog(budget, tableModel, frame));
        editButton = createButton("Edit", e -> showEditTransactionDialog(budget, tableModel, frame));
        filterButton = createButton("Filter", e -> showFilterTransactionDialog(budget, tableModel, frame));

        fileButton = createButton("File", e -> showFileTransactionDialog(fileButton, budget));
        exportButton = createButton("Export", e -> showExportTransactionDialog(exportButton, budget));

        // Set the button margins and minimum sizes
        Insets buttonMargin = new Insets(2, 2, 2, 2);
        Dimension buttonMinSize = new Dimension(30, 14);

        addButton.setMargin(buttonMargin);
        addButton.setMinimumSize(buttonMinSize);
        deleteButton.setMargin(buttonMargin);
        deleteButton.setMinimumSize(buttonMinSize);
        editButton.setMargin(buttonMargin);
        editButton.setMinimumSize(buttonMinSize);
        filterButton.setMargin(buttonMargin);
        filterButton.setMinimumSize(buttonMinSize);
        fileButton.setMargin(buttonMargin);
        fileButton.setMinimumSize(buttonMinSize);
        exportButton.setMargin(buttonMargin);
        exportButton.setMinimumSize(buttonMinSize);

        // Add all the buttons to the toolbar
        add(addButton);
        add(editButton);
        add(deleteButton);
        add(filterButton);
        addSeparator();
        add(fileButton);
        addSeparator();
        add(exportButton);

    }

    /**
     * Creates a new JButton with the specified label and action listener.
     *
     * @param label    the label to display on the button
     * @param listener the action listener for the button
     * @return the new JButton instance
     */
    private JButton createButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        return button;
    }

    private void showAddTransactionDialog(Budget budget, BudgetTableModel tableModel, JFrame frame) {
        TransactionAddPanel transactionAddPanel = new TransactionAddPanel();
        TransactionAddController transactionAddController = new TransactionAddController(budget, tableModel,
                transactionAddPanel, budgetStatusPanel);
        JDialog addTransactionDialog = createAddTransactionDialog(frame, transactionAddPanel);
        addTransactionDialog.setVisible(true);
    }

    private JDialog createAddTransactionDialog(JFrame frame, TransactionAddPanel transactionAddPanel) {
        JDialog dialog = new JDialog(frame, "Add Transaction", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());
        dialog.add(transactionAddPanel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);

        // Set the default button on the root pane
        dialog.getRootPane().setDefaultButton(transactionAddPanel.getAddButton());

        return dialog;
    }

    private void showEditTransactionDialog(Budget budget, BudgetTableModel tableModel, JFrame frame) {
        TransactionModifyPanel modifyTransactionPanel = new TransactionModifyPanel();
        TransactionModifyController modifyTransactionController = new TransactionModifyController(budget, modifyTransactionPanel,
                budgetStatusPanel, tableModel);
        JDialog modifyTransactionDialog = createModifyTransactionDialog(frame, modifyTransactionPanel);
        modifyTransactionDialog.setVisible(true);
    }

    private JDialog createModifyTransactionDialog(JFrame frame, TransactionModifyPanel modifyTransactionPanel) {
        JDialog dialog = new JDialog(frame, "Edit Transaction", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());
        dialog.add(modifyTransactionPanel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);

        // Set the default button on the root pane
        dialog.getRootPane().setDefaultButton(modifyTransactionPanel.getSaveButton());

        return dialog;
    }

    private void showRemoveTransactionDialog(Budget budget, BudgetTableModel tableModel, JFrame frame) {
        TransactionRemovePanel transactionRemovePanel = new TransactionRemovePanel(tableModel);
        TransactionRemoveController transactionRemoveController
                = new TransactionRemoveController(transactionRemovePanel, budget, tableModel, budgetStatusPanel);
        JDialog removeTransactionDialog = createRemoveTransactionDialog(frame, transactionRemovePanel);
        removeTransactionDialog.setVisible(true);
    }

    private JDialog createRemoveTransactionDialog(JFrame frame, TransactionRemovePanel transactionRemovePanel) {
        JDialog dialog = new JDialog(frame, "Remove Transaction", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());
        dialog.add(transactionRemovePanel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);

        // Set the default button on the root pane
        dialog.getRootPane().setDefaultButton(transactionRemovePanel.getRemoveButton());

        return dialog;
    }

    private void showFilterTransactionDialog(Budget budget, BudgetTableModel tableModel, JFrame frame){
        BudgetFilterPanel budgetFilterPanel = new BudgetFilterPanel();
        BudgetFilterController budgetFilterController = new BudgetFilterController(budgetFilterPanel, tableModel,
                budgetStatusPanel, budget);
        JDialog budgetFilterDialog = createFilterTransactionDialog(frame, budgetFilterPanel);
        budgetFilterDialog.setVisible(true);
    }

    private JDialog createFilterTransactionDialog(JFrame frame, BudgetFilterPanel budgetFilterPanel){
        JDialog dialog = new JDialog(frame, "Filter Transaction", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());
        dialog.add(budgetFilterPanel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);

        // Set the default button on the root pane
        dialog.getRootPane().setDefaultButton(budgetFilterPanel.getFilterButton());

        return dialog;
    }

    private void showFileTransactionDialog(Component component, Budget budget) {
        BudgetPersistencePanel budgetPersistencePanel = new BudgetPersistencePanel();

        BudgetPersistenceController budgetPersistenceController
                = new BudgetPersistenceController(budgetPersistencePanel, budget);

        JPopupMenu popupMenu = budgetPersistencePanel.getFileMenu();
        popupMenu.show(component, 0, component.getHeight());
    }

    private void showExportTransactionDialog(Component component, Budget budget){
        BudgetExportPanel budgetExportPanel = new BudgetExportPanel();

        BudgetExportController budgetExportController = new BudgetExportController(budgetExportPanel, budget);

        JPopupMenu popupMenu = budgetExportPanel.getExportMenu();
        popupMenu.show(component, 0, component.getHeight());
    }

    /**
     * Sets a new budget for the panel.
     *
     * @param budget the new budget to manage
     */
    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}