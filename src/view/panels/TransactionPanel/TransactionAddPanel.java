package view.panels.TransactionPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

/**
 * A {@link TransactionPanelBase} that displays a form for adding a transaction to the budget.
 * The form contains text fields for description and amount, and a date spinner for the transaction date.
 * The panel also contains an "Add" button to submit the transaction, and a "Cancel" button to cancel the transaction.
 *
 * @author Alessandro Catenacci
 */
public class TransactionAddPanel extends TransactionPanelBase {

    private JTextField descriptionField;
    private JTextField amountField;
    private JSpinner dateSpinner;

    /**
     * Constructs a TransactionAddPanel with a BorderLayout, and adds the form panel and button panel to it.
     */
    public TransactionAddPanel() {
        super("Add", "Cancel"); // Call the constructor of the superclass
        setLayout(new BorderLayout()); // Set the layout to BorderLayout
        add(createFormPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    /**
     * Creates the form panel that contains the transaction description, amount, and date fields.
     * The panel has a GridLayout with 3 rows and 2 columns, and a 10-pixel gap between components.
     * The panel has a white background and a 10-pixel empty border.
     *
     * @return the form panel that contains the transaction fields
     */
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(Color.WHITE);
        descriptionField = new JTextField();
        amountField = new JTextField();
        dateSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy HH:mm:ss");
        dateSpinner.setEditor(dateEditor);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descriptionField);
        formPanel.add(new JLabel("Amount:"));
        formPanel.add(amountField);
        formPanel.add(new JLabel("Date:"));
        formPanel.add(dateSpinner);

        return formPanel;
    }

    /**
     * Creates the button panel that contains the "Add" and "Cancel" buttons.
     * The panel has a GridBagLayout with two columns, and a 10-pixel empty border on the bottom and sides.
     * The panel has a white background.
     * The "Add" button has a green background color, and the "Cancel" button has a red background color.
     * The "Add" button is set to fill the horizontal space of its column, and the
     * "Cancel" button is aligned to the right of its column.
     *
     * @return the button panel that contains the "Add" and "Cancel" buttons
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 5);
        buttonPanel.add(super.getActionButton(), c);
        c.gridx = 1;
        c.weightx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 5, 0, 0);
        buttonPanel.add(super.getCancelButton(), c);
        buttonPanel.setBackground(Color.WHITE);

        return buttonPanel;
    }

    public JTextField getDescriptionField() {
        return descriptionField;
    }

    public JTextField getAmountField() {
        return amountField;
    }

    public JSpinner getDateSpinner() {
        return dateSpinner;
    }

    @Override
    public JButton getActionButton() {
        return super.getActionButton();
    }

    @Override
    public JButton getCancelButton() {
        return super.getCancelButton();
    }

    public void clearForm() {
        getDateSpinner().setValue(new Date());
        getAmountField().setText("");
        getDescriptionField().setText("");
    }
}