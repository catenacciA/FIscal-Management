import model.AutoSaveThread;
import model.Budget;
import view.BudgetManagementUI;

/**
 * A class that launches the budget management application.
 *
 * @author Alessandro Catenacci
 */
public class BudgetManagementApp {
    /**
     * The main method that creates and starts the UI and the auto-save thread for a budget object.
     *
     * @param args the command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Create a new budget object
        Budget budget = new Budget();
        AutoSaveThread thread;

        // Try to retrieve a budget object from a temporary file
        Budget retrievedBudget = new AutoSaveThread(budget).retrieveBudgetFromTempFile();
        if (retrievedBudget != null) {
            // Use the retrieved budget if it exists
            budget = retrievedBudget;
        }

        // Create and start a new auto-save thread for the budget object
        thread = new AutoSaveThread(budget);

        // Create and launch a new UI for the budget object
        BudgetManagementUI ui = new BudgetManagementUI(budget);

        // Set the auto-save thread for the UI
        ui.setAutoSaveThread(thread);

        ui.launch();

        // Start the auto-save thread
        thread.start();
    }

}