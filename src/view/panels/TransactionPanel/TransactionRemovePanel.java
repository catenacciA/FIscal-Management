package view.panels.TransactionPanel;

import view.components.BudgetTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;

/**
 * {@link TransactionRemovePanel} is a {@link view.panels.TransactionPanel.TransactionPanelBase}
 * that displays a table of transactions and provides
 * buttons to remove, clear, and close the panel.
 *
 * @author Alessandro Catenacci
 */
public class TransactionRemovePanel extends TransactionPanelBase {

    private final int PADDING_TOP = 20;
    private final int PADDING_BOTTOM = 10;
    private final int HORIZONTAL_GAP = 20;
    private final int VERTICAL_GAP = 10;
    private final int ROW_HEIGHT = 25;
    private final int COLUMN_WIDTH = 150;

    private JTable table;
    private JButton removeButton;
    private JButton clearButton;
    private JButton closeButton;
    private BudgetTableModel tableModel;

    /**
     * Constructs a new {@link TransactionRemovePanel} with the given table model.
     *
     * @param tableModel the {@link BudgetTableModel} to display in the table
     */
    public TransactionRemovePanel(BudgetTableModel tableModel) {

        super("Remove", "Close");
        this.tableModel = tableModel;

        // Create the table
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Set table properties
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        table.setShowVerticalLines(false);
        table.setRowHeight(ROW_HEIGHT);

        // Center table cell content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Set table header properties
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(Color.WHITE);
        tableHeader.setBorder(new EmptyBorder(PADDING_TOP, COLUMN_WIDTH, PADDING_BOTTOM, COLUMN_WIDTH));
        tableHeader.setFont(UIManager.getFont("TableHeader.font"));
        tableHeader.setForeground(Color.BLACK);

        // Set column properties
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(COLUMN_WIDTH);
        columnModel.getColumn(1).setPreferredWidth(COLUMN_WIDTH);
        columnModel.getColumn(2).setPreferredWidth(COLUMN_WIDTH);


        super.getActionButton().setForeground(Color.RED);
        super.getCancelButton().setForeground(Color.BLACK);

        clearButton = new JButton("Clear All");

        // Lay out the components
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.add(table.getTableHeader());
        tablePanel.add(table);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, HORIZONTAL_GAP, VERTICAL_GAP));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(super.getActionButton());
        buttonPanel.add(clearButton);
        buttonPanel.add(super.getCancelButton());

        setLayout(new BorderLayout());
        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public JButton getActionButton() {
        return super.getActionButton();
    }

    public JButton getClearButton() {
        return clearButton;
    }

    @Override
    public JButton getCancelButton() {
        return super.getCancelButton();
    }

    public int getSelectedRow(){
        return table.getSelectedRow();
    }
}