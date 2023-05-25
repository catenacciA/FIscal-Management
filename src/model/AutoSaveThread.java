package model;

import javax.swing.*;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.concurrent.locks.ReentrantLock;

/**
 * AutoSaveThread is a thread that saves a Budget object to a temporary file
 * at a regular interval, and provides methods for retrieving the saved Budget from the temporary file.
 *
 * @author Alessandro Catenacci
 */
public class AutoSaveThread extends Thread {
    private static final long AUTO_SAVE_INTERVAL = 10000; // 10 seconds
    private static final String TEMP_FILE_NAME = "temp_budget.bma";
    private final ReentrantLock lock;
    private Budget budget;
    private File tempFile;


    /**
     * Constructs an AutoSaveThread with the specified {@link Budget} object to be saved to a temporary file.
     *
     * @param budget the Budget object to be saved
     */
    public AutoSaveThread(Budget budget) {
        this.budget = budget;
        this.lock = new ReentrantLock();
        this.tempFile = getTempFile();
    }

    private File getTempFile() {
        File tempFolder = new File(System.getProperty("java.io.tmpdir"));
        return new File(tempFolder, TEMP_FILE_NAME);
    }


    /**
     * Starts the thread and saves the Budget object to the temporary file at a fixed interval. The save interval is
     * specified by the {@link #AUTO_SAVE_INTERVAL} constant.
     */
    @Override
    public void run() {
        boolean interrupted = false;
        while (!interrupted) {
            lock.lock();
            try {
                saveBudgetToTempFile();

                // Debug statement to print what was saved to temp file
                //System.out.println("Saved to temp file: " + budget.getTransactions());

                Thread.sleep(AUTO_SAVE_INTERVAL);
            } catch (InterruptedException e) {
                interrupted = true;
                Thread.currentThread().interrupt();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * Saves the Budget object to the temporary file. This method is called by
     * the {@link #run()} method at a fixed interval.
     *
     * @throws IOException if an I/O error occurs while writing to the temporary file
     */
    private void saveBudgetToTempFile() throws IOException {
        // Use try-with-resources to ensure that the FileOutputStream and ObjectOutputStream are properly closed
        try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
             FileChannel channel = fileOutputStream.getChannel()) {
            objectOutputStream.writeObject(budget);
            objectOutputStream.flush();
            channel.force(true);
        }
    }

    /**
     * Retrieves the saved Budget from the temporary file.
     *
     * @return the saved Budget, or null if the file does not exist or is empty
     */
    public Budget retrieveBudgetFromTempFile() {
        if (!tempFile.exists() || tempFile.length() == 0) {
            return null;
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(tempFile))) {
            Object object = inputStream.readObject();
            if (object instanceof Budget retrievedBudget && !retrievedBudget.getTransactions().isEmpty()) {
                int result = JOptionPane.showConfirmDialog(null,
                        "A temporary file has been found.\n" +
                                "Do you want to retrieve the budget from it?",
                        "Retrieve Budget", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    // Debug statement to print what was retrieved from temp file
                    //System.out.println("Retrieved from temp file: " + retrievedBudget.getTransactions());
                    return retrievedBudget;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (!tempFile.delete()) {
                System.out.println("Failed to delete temp file");
            }
        }

        return null;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setTempFile(File tempFile) {
        this.tempFile = tempFile;
    }
}