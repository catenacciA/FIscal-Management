package view.panels.TransactionPanel;

import javax.swing.*;
import java.awt.*;

/**
 * A base panel that contains common properties and methods for transaction panels.
 * It extends JPanel and provides a consistent layout and UI components.
 */
public abstract class TransactionPanelBase extends JPanel {

    protected JButton actionButton;
    protected JButton cancelButton;

    /**
     * Constructs a new TransactionPanelBase with default UI components and layout.
     * Initializes the action and cancel buttons.
     * @param actionButtonLabel the label for the action button
     * @param cancelButtonLabel the label for the cancel button
     */
    public TransactionPanelBase(String actionButtonLabel, String cancelButtonLabel) {
        setLayout(new GridLayout(2, 2));

        actionButton = new JButton(actionButtonLabel);
        actionButton.setForeground(Color.BLACK);
        cancelButton = new JButton(cancelButtonLabel);
        cancelButton.setForeground(Color.RED);

        add(actionButton);
        add(cancelButton);
    }

    public JButton getActionButton() {
        return actionButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}