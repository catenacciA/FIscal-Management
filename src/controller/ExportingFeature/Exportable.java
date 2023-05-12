package controller.ExportingFeature;

import model.Budget;

/**
 * The Exportable interface defines a contract for classes that can export a Budget object
 * to a specified file.
 *
 * @author Alessandro Catenacci
 */
public interface Exportable {
    /**
     * Export the specified Budget object to a file with the given file name.
     *
     * @param fileName the name of the file to which the Budget object should be exported.
     * @param budget the Budget object to be exported.
     */
    void export(String fileName, Budget budget);
}
