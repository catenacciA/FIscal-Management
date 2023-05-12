package controller.ExportingFeature;

import model.Budget;
import model.Transaction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Exports the budget data to a text file.
 *
 * @author Alessandro Catenacci
 */
public class TextExporter implements Exportable {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Exports the budget data to a text file.
     * <p>
     * Calculates the column widths and separator length for the text file, creates the format string,
     * writes the header row and separator, and then writes each transaction to a separate line in the file.
     * Finally, it writes the footer separator to the file.
     *
     * @param fileName The name of the file to export to.
     * @param budget The budget to export.
     */
    @Override
    public void export(String fileName, Budget budget) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // calculate the column widths and separator length
            List<Transaction> transactions = budget.getTransactions();
            int idWidth = Math.max("ID".length(), transactions.stream()
                    .mapToInt(t -> Integer.toString(t.getId()).length()).max().orElse(0));
            int dateWidth = Math.max("Date".length(), transactions.stream()
                    .mapToInt(t -> t.getTransactionDate().format(DATE_FORMAT).length()).max().orElse(0));
            int descriptionWidth = Math.max("Description".length(), transactions.stream()
                    .mapToInt(t -> t.getDescription().length()).max().orElse(0));
            int amountWidth = Math.max("Amount".length(), transactions.stream()
                    .mapToInt(t -> formatAmount(t.getAmount()).length()).max().orElse(0));
            int separatorLength = idWidth + dateWidth + descriptionWidth + amountWidth + 6;

            // create the format string
            String format = String.format("%%-%ds  %%-%ds  %%-%ds  %%-%ds",
                    idWidth, dateWidth, descriptionWidth, amountWidth);

            // write header row and separator
            writer.write(String.format(format, "ID", "Date", "Description", "Amount"));
            writer.newLine();
            String separator = String.format("%0" + separatorLength + "d", 0).replace("0", "-");
            writer.write(separator);
            writer.newLine();

            // write each transaction to a separate line in the file
            for (Transaction transaction : transactions) {
                writer.write(String.format(format,
                        transaction.getId(),
                        transaction.getTransactionDate().format(DATE_FORMAT),
                        transaction.getDescription(),
                        formatAmount(transaction.getAmount())));
                writer.newLine();
            }

            // write footer separator
            writer.write(separator);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Formats the given amount as a string with commas for the thousands separator and two decimal places.
     *
     * @param amount The amount to format.
     * @return The formatted amount as a string.
     */
    private String formatAmount(double amount) {
        DecimalFormat decimalFormat;
        if (amount == (int) amount) {
            decimalFormat = new DecimalFormat("#,##0");
        } else {
            decimalFormat = new DecimalFormat("#,##0.00");
        }
        return decimalFormat.format(amount);
    }
}