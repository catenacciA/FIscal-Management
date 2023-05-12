package controller.BudgetController;

import controller.ExportingFeature.CsvExporter;
import controller.ExportingFeature.ExcelExporter;
import controller.ExportingFeature.Exportable;
import controller.ExportingFeature.TextExporter;
import model.Budget;
import view.panels.BudgetPanel.BudgetExportPanel;

import javax.swing.*;
import java.io.File;

/**
 * A controller class for handling budget export operations.
 * <p>
 * This class is responsible for managing the interaction between the {@link BudgetExportPanel}
 * and the {@link Budget} model.
 * <p>
 * It allows the user to export the budget data in various formats, such as TXT, CSV, and XLSX.
 *
 * @author Alessandro Catenacci
 */
public class BudgetExportController {
    private final BudgetExportPanel panel;
    private final Budget budget;

    /**
     * Constructs a new {@link BudgetExportController} with the specified {@link BudgetExportPanel} and {@link Budget}.
     *
     * @param panel  the panel for exporting budget data
     * @param budget the budget model to be exported
     */
    public BudgetExportController(BudgetExportPanel panel, Budget budget) {
        this.panel = panel;
        this.budget = budget;

        panel.getExportButton().addActionListener(e -> panel.getExportMenu().show(
                panel.getExportButton(), 0, panel.getExportButton().getHeight()));

        panel.addTextExportActionListener(e -> handleExportAction(new TextExporter(), "budget.txt"));
        panel.addCsvExportActionListener(e -> handleExportAction(new CsvExporter(), "budget.csv"));
        panel.addExcelExportActionListener(e -> handleExportAction(new ExcelExporter(), "budget.xlsx"));
    }

    /**
     * Handles the export action by creating an {@link Exportable} object with the specified exporter and default file name.
     * It opens a file chooser dialog, allowing the user to select a location to save the exported file.
     *
     * @param exporter        the {@link Exportable} object responsible for exporting the budget data
     * @param defaultFileName the default file name to be used when saving the exported file
     */
    private void handleExportAction(Exportable exporter, String defaultFileName) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As");
        fileChooser.setSelectedFile(new File(defaultFileName));

        int userSelection = fileChooser.showSaveDialog(panel);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (fileToSave.exists()) {
                int result = JOptionPane.showConfirmDialog(panel,
                        "The file already exists. Do you want to overwrite it?",
                        "Confirm Overwrite", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    saveToFile(fileToSave, exporter);
                }
            } else {
                saveToFile(fileToSave, exporter);
            }
        }
    }

    /**
     * Saves the budget data to the specified file.
     *
     * @param file       the file to save the budget data
     * @param exporter   the {@link Exportable} object responsible for exporting the budget data
     */
    private void saveToFile(File file, Exportable exporter) {
        try {
            exporter.export(file.getAbsolutePath(), budget);
            JOptionPane.showMessageDialog(panel, "Budget saved to " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel, "An error occurred while saving the budget: "
                            + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}