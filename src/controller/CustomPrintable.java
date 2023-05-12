package controller;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 * A custom Printable implementation that adds labels to the printable content.
 *
 * @author Alessandro Catenacci
 */
public class CustomPrintable implements Printable {

    private Printable contentPrintable;
    private String label1;
    private String label2;
    private Font labelFont;

    /**
     * Constructs a new CustomPrintable with the given printable content and labels.
     *
     * @param contentPrintable the printable content to be printed
     * @param label1 the text of the first label
     * @param label2 the text of the second label
     * @param labelFont the font of the labels
     * @throws NullPointerException if any of the input parameters are null
     * @throws IllegalArgumentException if any of the input strings are empty
     */
    public CustomPrintable(Printable contentPrintable, String label1, String label2, Font labelFont) {
        if (contentPrintable == null || label1 == null || label2 == null || labelFont == null) {
            throw new NullPointerException("Input parameters cannot be null.");
        }
        if (label1.isEmpty() || label2.isEmpty()) {
            throw new IllegalArgumentException("Label strings cannot be empty.");
        }
        this.contentPrintable = contentPrintable;
        this.label1 = label1;
        this.label2 = label2;
        this.labelFont = labelFont;
    }

    /**
     * Prints the printable content with labels.
     *
     * @param graphics the graphics context to use for printing
     * @param pageFormat the page format to use for printing
     * @param pageIndex the index of the page to print
     * @throws PrinterException if an error occurs while printing
     * @return PAGE_EXISTS if the page was printed successfully, NO_SUCH_PAGE otherwise
     */
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        int result = contentPrintable.print(graphics, pageFormat, pageIndex);

        if (result == Printable.PAGE_EXISTS) {
            Graphics2D g2d = (Graphics2D) graphics;

            // Set up font for labels
            Font labelFont = this.labelFont.deriveFont(10f);
            g2d.setFont(labelFont);

            // Calculate label dimensions
            FontMetrics fm = g2d.getFontMetrics();
            fm.stringWidth(label1);
            int label1Width = fm.stringWidth(label1);
            int label2Width = fm.stringWidth(label2);
            int labelHeight = fm.getHeight();

            // Calculate label positions
            double x1 = pageFormat.getImageableX() + pageFormat.getImageableWidth() - label2Width;
            double y1 = pageFormat.getImageableY() + pageFormat.getImageableHeight() - labelHeight;
            double x2 = pageFormat.getImageableX();

            // Draw labels
            g2d.setColor(Color.BLACK);
            g2d.drawString(label2, (float) x1, (float) y1);
            g2d.drawString(label1, (float) x2, (float) y1);
        }

        return result;
    }
}
