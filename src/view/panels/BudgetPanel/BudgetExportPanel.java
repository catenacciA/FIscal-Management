package view.panels.BudgetPanel;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * A JPanel extension that provides an interface for exporting budget data in various formats.
 * The panel contains an export button that triggers a JPopupMenu with options to export
 * the data as text, Excel, or CSV files.
 *
 * @see JPanel
 * @see JPopupMenu
 *
 * @author Alessandro Catenacci
 */
public class BudgetExportPanel extends JPanel {
    private JButton exportButton;
    private JPopupMenu exportMenu;

    /**
     * Constructs a new BudgetExportPanel with an export button and a JPopupMenu containing
     * options to export the data as text, Excel, or CSV files.
     */
    public BudgetExportPanel() {
        exportButton = new JButton("Export");
        exportMenu = new JPopupMenu();

        JMenuItem textItem = new JMenuItem("Text");
        exportMenu.add(textItem);

        JMenuItem excelItem = new JMenuItem("Excel");
        exportMenu.add(excelItem);

        JMenuItem csvItem = new JMenuItem("CSV");
        exportMenu.add(csvItem);

        add(exportButton);
    }

    public JButton getExportButton() {
        return exportButton;
    }

    public JPopupMenu getExportMenu() {
        return exportMenu;
    }

    /**
     * Adds an ActionListener to the JMenuItem responsible for exporting the data as text.
     *
     * @param listener the ActionListener to be added
     * @see ActionListener
     */
    public void addTextExportActionListener(ActionListener listener) {
        ((JMenuItem) exportMenu.getSubElements()[0]).addActionListener(listener);
    }

    /**
     * Adds an ActionListener to the JMenuItem responsible for exporting the data as an Excel file.
     *
     * @param listener the ActionListener to be added
     * @see ActionListener
     */
    public void addExcelExportActionListener(ActionListener listener) {
        ((JMenuItem) exportMenu.getSubElements()[1]).addActionListener(listener);
    }

    /**
     * Adds an ActionListener to the JMenuItem responsible for exporting the data as a CSV file.
     *
     * @param listener the ActionListener to be added
     * @see ActionListener
     */
    public void addCsvExportActionListener(ActionListener listener) {
        ((JMenuItem) exportMenu.getSubElements()[2]).addActionListener(listener);
    }
}
