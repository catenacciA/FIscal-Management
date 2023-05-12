package view.components;

import model.Budget;
import model.Transaction;

import javax.swing.table.AbstractTableModel;
import java.io.Serial;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The class representing the table model for a budget.
 *
 * @author Alessandro Catenacci
 */
public class BudgetTableModel extends AbstractTableModel {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final List<String> COLUMN_NAMES = List.of("ID", "Date", "Description", "Amount");
    private Budget budget;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Constructs a new BudgetTableModel for the specified budget.
     *
     * @param budget the budget for which the table model is being created
     */
    public BudgetTableModel(Budget budget) {
        this.budget = budget;
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.size();
    }

    @Override
    public int getRowCount() {
        return budget.getTransactions().size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES.get(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        List<Transaction> transactions = budget.getTransactions();
        if (transactions.isEmpty()) {
            return null;
        }
        Transaction transaction = transactions.get(rowIndex);
        if (columnIndex == 0) {
            return "T00" + transaction.getId();
        } else {
            return switch (columnIndex) {
                case 1 -> transaction.getTransactionDate().format(DATE_FORMATTER);
                case 2 -> transaction.getDescription();
                case 3 -> transaction.getAmount();
                default -> null;
            };
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        List<Transaction> transactions = budget.getTransactions();
        if (transactions.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Transaction getTransaction(int rowIndex) {
        return budget.getTransactions().get(rowIndex);
    }

    public int getRowIndex(Transaction transaction) {
        return budget.getTransactions().indexOf(transaction);
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

}
