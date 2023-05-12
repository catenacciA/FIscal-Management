package controller.BudgetController;

import controller.CustomPrintable;
import model.AutoSaveThread;
import model.Budget;
import view.BudgetManagementUI;
import view.panels.BudgetPanel.BudgetPersistencePanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * The BudgetPersistenceController class manages the saving and loading of budgets in the application.
 * It handles actions related to the BudgetPersistencePanel and the Budget object.
 *
 * @author Alessandro Catenacci
 */
public class BudgetPersistenceController {
    private final BudgetPersistencePanel panel;
    private final Budget budget;

    /**
     * Constructs a new BudgetPersistenceController object with the specified panel and budget.
     *
     * @param panel The panel used to manage transactions.
     * @param budget The budget object being managed.
     */
    public BudgetPersistenceController(BudgetPersistencePanel panel, Budget budget) {
        this.panel = panel;
        this.budget = budget;

        panel.getFileButton().addActionListener(e -> panel.getFileMenu().show(panel.getFileButton(),
                0, panel.getFileButton().getHeight()));

        panel.addOpenActionListener(e -> {
            Budget loadedBudget = open();
            if(loadedBudget != null){

                // Get the current UI, dispose it and stop its auto-save thread
                BudgetManagementUI currentUi = BudgetManagementUI.getInstance();
                currentUi.dispose();

                AutoSaveThread thread;
                thread = new AutoSaveThread(loadedBudget);
                BudgetManagementUI newUi = new BudgetManagementUI(loadedBudget);
                newUi.launch();
                thread.start();
            }
        });

        panel.addSaveAsActionListener(e -> saveAs());

        panel.addPrintActionListener(e -> print());
    }


    /**
     * Opens a serialized budget file and returns the budget object.
     *
     * @return The budget object loaded from the file, or null if the operation was cancelled or failed.
     */
    public Budget open() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Serialized Budget files", "bma"));

        int result = fileChooser.showOpenDialog(panel);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith(".bma")) {
                JOptionPane.showMessageDialog(panel,
                        "Invalid file type selected. Please select a .bma file.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Budget loadedBudget = (Budget) ois.readObject();
                JOptionPane.showMessageDialog(panel, "Budget loaded from " + file.getAbsolutePath());
                return loadedBudget;
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(panel, "Error loading file: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    /**
     * Saves the budget object to a file selected by the user.
     */
    public void saveAs() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Serialized Budget files", "bma"));

        int result = fileChooser.showSaveDialog(panel);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith(".bma")) {
                file = new File(file.getPath() + ".bma");
            }
            if (file.exists()) {
                int response = JOptionPane.showConfirmDialog(panel,
                        "The file already exists. Do you want to overwrite it?",
                        "Confirm Overwrite", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (response == JOptionPane.NO_OPTION) {
                    return;
                }
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(budget);
                JOptionPane.showMessageDialog(panel, "Budget saved to " + file.getAbsolutePath());
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(panel, "Error saving file: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Prints the budget data using the system-configured printer in landscape mode.
     */
    public void print() {
        JTable budgetTable = BudgetManagementUI.getInstance().getTable().getTable();

        try {
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            PageFormat pageFormat = printerJob.defaultPage();
            pageFormat.setOrientation(PageFormat.LANDSCAPE);

            Printable tablePrintable = budgetTable.getPrintable(JTable.PrintMode.FIT_WIDTH,
                    null, null);

            String label1 = "Number of Transactions: " + budget.getTransactions().size();

            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance();
            String formattedTotalAmount = decimalFormat.format(budget.getTotalAmount());
            String label2 = "Total Amount Spent: " + formattedTotalAmount;

            Font labelFont = new Font("Arial", Font.PLAIN, 12);

            CustomPrintable customPrintable = new CustomPrintable(tablePrintable, label2, label1, labelFont);
            printerJob.setPrintable(customPrintable, pageFormat);

            if (printerJob.printDialog()) {
                printerJob.print();
                JOptionPane.showMessageDialog(panel, "Budget printed successfully.");
            }
        } catch (PrinterException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Error printing budget: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
