package controller.ExportingFeature;

import model.Budget;
import model.Transaction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * The CsvExporter class implements the Exportable interface and exports the given budget to a CSV file.
 *
 * @author Alessandro Catenacci
 */
public class CsvExporter implements Exportable {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Exports the given budget to a CSV file with the given file name.
     *
     * @param fileName the name of the CSV file to export to
     * @param budget   the budget to export
     */
    @Override
    public void export(String fileName, Budget budget) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // write header row
            writer.write("ID,Date,Description,Amount");
            writer.newLine();

            // write each transaction to a separate line in the file
            for (Transaction transaction : budget.getTransactions()) {
                writer.write(String.format("%d,%s,%s,%s",
                        transaction.getId(),
                        transaction.getTransactionDate().format(DATE_FORMAT),
                        transaction.getDescription(),
                        transaction.getAmount()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
