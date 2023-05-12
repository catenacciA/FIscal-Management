package view.panels.BudgetPanel;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * The BudgetPersistencePanel class represents a JPanel that contains a file button
 * and a popup menu for file-related actions.
 *
 * @author Alessandro Catenacci
 */
public class BudgetPersistencePanel extends JPanel {
    private JButton fileButton;
    private JPopupMenu fileMenu;

    /**
     * Constructs a BudgetPersistencePanel object with a file button and a popup menu for file-related actions.
     * The popup menu contains "Open", "Save as", and "Print" menu items.
     */
    public BudgetPersistencePanel() {
        fileButton = new JButton("File");
        fileMenu = new JPopupMenu();

        JMenuItem openItem = new JMenuItem("Open");
        fileMenu.add(openItem);

        JMenuItem saveAsItem = new JMenuItem("Save as");
        fileMenu.add(saveAsItem);

        JMenuItem printItem = new JMenuItem("Print");
        fileMenu.add(printItem);

        add(fileButton);
    }

    public JButton getFileButton() {
        return fileButton;
    }

    public JPopupMenu getFileMenu() {
        return fileMenu;
    }

    /**
     * Adds an action listener to the "Open" menu item.
     *
     * @param listener the action listener to be added
     */
    public void addOpenActionListener(ActionListener listener) {
        ((JMenuItem) fileMenu.getSubElements()[0]).addActionListener(listener);
    }

    /**
     * Adds an action listener to the "Save as" menu item.
     *
     * @param listener the action listener to be added
     */
    public void addSaveAsActionListener(ActionListener listener) {
        ((JMenuItem) fileMenu.getSubElements()[1]).addActionListener(listener);
    }

    /**
     * Adds an action listener to the "Print" menu item.
     *
     * @param listener the action listener to be added
     */
    public void addPrintActionListener(ActionListener listener) {
        ((JMenuItem) fileMenu.getSubElements()[2]).addActionListener(listener);
    }
}
