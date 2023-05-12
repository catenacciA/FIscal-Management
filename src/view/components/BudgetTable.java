package view.components;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The BudgetTable class extends the JScrollPane class and is used to display
 * a budget table model in a scrollable format.
 * This class also provides methods for highlighting specific cells or rows in the table.
 *
 * @author Alessandro Catenacci
 */
public class BudgetTable extends JScrollPane {

    private JTable table;

    /**
     * Constructs a new BudgetTable object using the given BudgetTableModel.
     *
     * @param tableModel The model to be displayed in the table.
     */
    public BudgetTable(BudgetTableModel tableModel) {
        super();
        this.table = new JTable(tableModel);
        setViewportView(table);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row == -1) { // user clicked on the table but not on any rows
                    removeHighlight();
                }
            }
        });
    }

    /**
     * Highlights a specific cell in the table by setting its background color.
     *
     * @param row The row index of the cell to highlight.
     * @param column The column index of the cell to highlight.
     * @param color The background color to use for highlighting.
     */
    public void highlightCell(int row, int column, Color color) {
        TableCellRenderer renderer = table.getCellRenderer(row, column);
        Component component = table.prepareRenderer(renderer, row, column);
        component.setBackground(color);
    }

    /**
     * Highlights a specific row in the table by setting its background color.
     *
     * @param row The row index to highlight.
     * @param color The background color to use for highlighting.
     */
    public void highlightRow(int row, Color color) {
        table.setRowSelectionInterval(row, row);
        table.setSelectionBackground(color);
        table.setSelectionForeground(Color.BLACK);
    }

    /**
     * Returns the current table model being used by the BudgetTable object.
     *
     * @return The BudgetTableModel object currently displayed in the table.
     */
    public BudgetTableModel getTableModel() {
        return (BudgetTableModel) table.getModel();
    }

    /**
     * Removes the highlight from the currently highlighted cell or row, if there is one.
     */
    public void removeHighlight() {
        table.clearSelection();
        table.setBackground(null);
    }

    public JTable getTable() {
        return table;
    }
}
