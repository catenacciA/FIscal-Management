package view;

import controller.BudgetController.BudgetSearchController;
import model.AutoSaveThread;
import model.Budget;
import view.components.BudgetTable;
import view.components.BudgetTableModel;
import view.panels.BudgetPanel.BudgetButtonPanel;
import view.panels.BudgetPanel.BudgetSearchPanel;
import view.panels.BudgetPanel.BudgetStatusPanel;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the main user interface for the Budget Management application.
 * It contains the main components of the UI, such as the table, button panel, status panel,
 * and search panel. It also manages the AutoSaveThread and BudgetSearchController.
 *
 * @author Alessandro Catenacci
 */
public class BudgetManagementUI {
    private JFrame frame;
    private BudgetTable table;
    private BudgetTableModel tableModel;
    private BudgetButtonPanel buttonPanel;
    private BudgetStatusPanel statusPanel;
    private BudgetSearchPanel searchPanel;
    private BudgetSearchController budgetSearchController;
    private AutoSaveThread autosaveThread;
    private static BudgetManagementUI instance;

    /**
     * Constructs a new BudgetManagementUI with the provided Budget.
     * Initializes the table model, table, status panel, button panel, search panel,
     * and BudgetSearchController.
     *
     * @param budget The budget to be managed by this UI.
     */
    public BudgetManagementUI(Budget budget) {
        tableModel = new BudgetTableModel(budget);
        table = new BudgetTable(tableModel);
        statusPanel = new BudgetStatusPanel(budget);
        buttonPanel = new BudgetButtonPanel(budget, tableModel, frame, statusPanel);
        searchPanel = new BudgetSearchPanel();
        budgetSearchController = new BudgetSearchController(budget, searchPanel, table);
        instance = this;

        // Create a panel to hold the buttonPanel and searchPanel
        JPanel topPanel = new JPanel(new BorderLayout());

        // Add the buttonPanel to the left side of the topPanel
        topPanel.add(buttonPanel, BorderLayout.WEST);

        // Add the searchPanel container to the right side of the topPanel
        topPanel.add(searchPanel, BorderLayout.EAST);

        // Add the topPanel, table, and statusPanel to the frame
        frame = new JFrame("Budget Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(table, BorderLayout.CENTER);
        frame.add(statusPanel, BorderLayout.SOUTH);
    }

    /**
     * Launches the UI.
     */
    public void launch() {
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public void setAutoSaveThread(AutoSaveThread autosaveThread) {
        this.autosaveThread = autosaveThread;
    }

    public static BudgetManagementUI getInstance() {
        return instance;
    }

    /**
     * Disposes the UI.
     */
    public void dispose(){
        frame.dispose();
        autosaveThread.interrupt();
    }

    public BudgetTable getTable() {
        return table;
    }
}
