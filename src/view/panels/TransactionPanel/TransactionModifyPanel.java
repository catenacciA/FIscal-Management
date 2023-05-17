package view.panels.TransactionPanel;

import javax.swing.*;
import java.awt.*;

/**
 * A {@code TransactionModifyPanel} class that extends {@link view.panels.TransactionPanel.TransactionPanelBase}.
 * This panel provides UI components for modifying transaction data.
 *
 * @author Alessandro Catenacci
 */
public class TransactionModifyPanel extends TransactionPanelBase {
    private JLabel selectTransactionLabel;
    private JComboBox<Integer> transactionComboBox;
    private JLabel amountLabel;
    private JTextField amountTextField;
    private JLabel dateLabel;
    private JTextField dateTextField;
    private JLabel descriptionLabel;
    private JTextField descriptionTextField;

    /**
     * Constructs a new {@code TransactionModifyPanel} with default UI components and layout.
     * Initializes the transaction selection label and combo box, amount and date labels and text fields,
     * description label and text field, save and cancel buttons.
     */
    public TransactionModifyPanel() {
        super("Save", "Cancel");
        setLayout(new GridLayout(5, 2));

        selectTransactionLabel = new JLabel("Select transaction:");
        transactionComboBox = new JComboBox<>();
        amountLabel = new JLabel("Amount:");
        amountTextField = new JTextField();
        dateLabel = new JLabel("Date:");
        dateTextField = new JTextField();
        descriptionLabel = new JLabel("Description:");
        descriptionTextField = new JTextField();

        add(selectTransactionLabel);
        add(transactionComboBox);
        add(amountLabel);
        add(amountTextField);
        add(dateLabel);
        add(dateTextField);
        add(descriptionLabel);
        add(descriptionTextField);
        add(super.getActionButton());
        add(super.getCancelButton());
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

    @Override
    public JButton getActionButton() {
        return super.getActionButton();
    }

    @Override
    public JButton getCancelButton() {
        return super.getCancelButton();
    }
}
