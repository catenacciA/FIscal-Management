package controller.BudgetController;

import model.Budget;
import model.Transaction;
import view.components.BudgetTableModel;
import view.panels.BudgetPanel.BudgetFilterPanel;
import view.panels.BudgetPanel.BudgetStatusPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link BudgetFilterController} class is responsible for managing the filtering
 * of transactions in the budget application.
 *
 * @author Alessandro Catenacci
 */
public class BudgetFilterController {

    private final BudgetFilterPanel filterPanel;
    private final BudgetTableModel tableModel;
    private final BudgetStatusPanel statusPanel;
    private Budget budget;
    private final List<Transaction> originalTransactions;

    /**
     * Constructs a new BudgetFilterController with the given parameters.
     *
     * @param filterPanel   The {@link BudgetFilterPanel} to be controlled by this controller.
     * @param tableModel    The {@link BudgetTableModel} to be updated based on the applied filters.
     * @param statusPanel   The {@link BudgetStatusPanel} to be updated based on the filtered transactions.
     * @param budget        The {@link Budget} instance containing the transactions to be filtered.
     */
    public BudgetFilterController(BudgetFilterPanel filterPanel, BudgetTableModel tableModel,
                                  BudgetStatusPanel statusPanel, Budget budget) {
        this.filterPanel = filterPanel;
        this.tableModel = tableModel;
        this.statusPanel = statusPanel;
        this.budget = budget;
        this.originalTransactions = new ArrayList<>(budget.getTransactions());

        setEditable(false);

        // Add item listener to preset combo box
        filterPanel.getPresetComboBox().addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedPreset = (String) e.getItem();
                setEditable("Custom".equals(selectedPreset));
            }
        });

        // Add action listener to filter button
        filterPanel.getFilterButton().addActionListener((ActionEvent e) -> {
            String selectedPreset = (String) filterPanel.getPresetComboBox().getSelectedItem();
            if ("Custom".equals(selectedPreset)) {
                filterByCustomDateRange();
            } else if (selectedPreset != null){
                filterByPreset(selectedPreset);
            }
            tableModel.fireTableDataChanged();
            statusPanel.updateBudget();
        });

        // Add action listener to reset button
        filterPanel.getResetButton().addActionListener((ActionEvent e) -> {
            budget.setTransactions(new ArrayList<>(originalTransactions));
            tableModel.fireTableDataChanged();
            statusPanel.updateBudget();
        });

        filterPanel.getCloseButton().addActionListener( e -> disposeDialog(filterPanel));
    }

    /**
     * Filters the transactions in the budget by the given date range.
     */
    private void filterByCustomDateRange() {
        String fromDateStr = filterPanel.getFromDate();
        String toDateStr = filterPanel.getToDate();
        LocalDateTime fromDate;
        LocalDateTime toDate;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            fromDate = LocalDate.parse(fromDateStr, formatter).atStartOfDay();
            toDate = LocalDate.parse(toDateStr, formatter).atTime(LocalTime.MAX);

            if (fromDate.isAfter(toDate)) {
                JOptionPane.showMessageDialog(filterPanel, "Invalid date range. " +
                                "'From' date cannot be after 'To' date.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(filterPanel, "Invalid date format",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Transaction> filteredTransactions = budget.filterTransactions(originalTransactions,
                null, fromDate, toDate);
        budget.setTransactions(filteredTransactions);
    }


    /**
     * Filters the transactions in the budget by the given preset.
     * @param preset The preset to filter by.
     */
    private void filterByPreset(String preset) {
        LocalDateTime toDate = LocalDateTime.now();
        LocalDateTime fromDate;

        switch (preset) {
            case "Last Day" -> fromDate = toDate.minusDays(1);
            case "Last Week" -> fromDate = toDate.minusWeeks(1);
            case "Last Month" -> fromDate = toDate.minusMonths(1);
            case "Last Year" -> fromDate = toDate.minusYears(1);
            default -> {
                JOptionPane.showMessageDialog(filterPanel, "Invalid preset",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        List<Transaction> filteredTransactions = budget.filterTransactions(originalTransactions,
                null, fromDate, toDate);
        budget.setTransactions(filteredTransactions);
    }

    /**
     * Sets the editable state of the date fields.
     *
     * @param editable The editable state to set.
     */
    private void setEditable(boolean editable) {
        filterPanel.getFromDateField().setEditable(editable);
        filterPanel.getToDateField().setEditable(editable);
    }

    /**
     * Disposes the dialog associated with the given panel.
     *
     * @param panel The panel to dispose of.
     */
    private void disposeDialog(JPanel panel) {
        JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(panel);
        dialog.dispose();
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}
