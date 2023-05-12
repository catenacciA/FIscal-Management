package controller.ExportingFeature;

import model.Budget;
import model.Transaction;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The ExcelExporter class is a class that implements the Exportable interface and provides the functionality
 * to export a Budget to an Excel file.
 *
 * @author Alessandro Catenacci
 */
public class ExcelExporter implements Exportable {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Exports the given budget data to an Excel file with the specified filename.
     *
     * @param fileName the name of the Excel file to be created
     * @param budget the budget data to be exported to Excel
     */
    @Override
    public void export(String fileName, Budget budget) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Budget");

            // create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Date");
            headerRow.createCell(2).setCellValue("Description");
            headerRow.createCell(3).setCellValue("Amount");

            // format header row
            CellStyle headerCellStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerCellStyle.setFont(headerFont);
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                headerRow.getCell(i).setCellStyle(headerCellStyle);
            }

            // create data rows
            List<Transaction> transactions = budget.getTransactions();
            for (int i = 0; i < transactions.size(); i++) {
                Transaction transaction = transactions.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(transaction.getId());
                row.createCell(1).setCellValue(transaction.getTransactionDate().format(DATE_FORMAT));
                row.createCell(2).setCellValue(transaction.getDescription());
                Cell amountCell = row.createCell(3);
                amountCell.setCellValue(transaction.getAmount());
                CellStyle amountCellStyle = workbook.createCellStyle();
                amountCellStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));
                amountCell.setCellStyle(amountCellStyle);
            }

            // adjust column widths
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                sheet.autoSizeColumn(i);
            }

            // save workbook to file
            try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
