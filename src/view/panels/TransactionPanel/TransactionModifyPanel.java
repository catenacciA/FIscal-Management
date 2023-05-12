package view.panels.TransactionPanel;

import javax.swing.*;
import java.awt.*;

/**
 * A {@code TransactionModifyPanel} class that extends {@link javax.swing.JPanel}.
 * This panel provides UI components for modifying transaction data.
 *
 * @author Alessandro Catenacci
 */
public class TransactionModifyPanel extends JPanel {
    private JLabel selectTransactionLabel;
    private JComboBox<Integer> transactionComboBox;
    private JLabel amountLabel;
    private JTextField amountTextField;
    private JLabel dateLabel;
    private JTextField dateTextField;
    private JLabel descriptionLabel;
    private JTextField descriptionTextField;
    private JButton saveButton;
    private JButton cancelButton;

    /**
     * Constructs a new {@code TransactionModifyPanel} with default UI components and layout.
     * Initializes the transaction selection label and combo box, amount and date labels and text fields,
     * description label and text field, save and cancel buttons.
     */
    public TransactionModifyPanel() {
        setLayout(new GridLayout(5, 2));

        selectTransactionLabel = new JLabel("Select transaction:");
        transactionComboBox = new JComboBox<>();
        amountLabel = new JLabel("Amount:");
        amountTextField = new JTextField();
        dateLabel = new JLabel("Date:");
        dateTextField = new JTextField();
        descriptionLabel = new JLabel("Description:");
        descriptionTextField = new JTextField();
        saveButton = new JButton("Save");
        saveButton.setForeground(Color.BLACK);
        cancelButton = new JButton("Cancel");
        cancelButton.setForeground(Color.RED);

        add(selectTransactionLabel);
        add(transactionComboBox);
        add(amountLabel);
        add(amountTextField);
        add(dateLabel);
        add(dateTextField);
        add(descriptionLabel);
        add(descriptionTextField);
        add(saveButton);
        add(cancelButton);
    }

    public JComboBox<Integer> getTransactionComboBox() {
        return transactionComboBox;
    }

    public JTextField getAmountTextField() {
        return amountTextField;
    }

    public JTextField getDateTextField() {
        return dateTextField;
    }

    public JTextField getDescriptionTextField() {
        return descriptionTextField;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}
