package view.panels.BudgetPanel;

import javax.swing.*;
import java.awt.*;

/**
 * A {@code BudgetFilterPanel} class that extends {@link javax.swing.JPanel}.
 * This panel provides UI components for filtering and resetting the budget data.
 *
 * @author Alessandro Catenacci
 */
public class BudgetFilterPanel extends JPanel {
    private final JButton filterButton;
    private final JButton resetButton;
    private final JLabel fromDateLabel;
    private final JLabel toDateLabel;
    private final JTextField fromDateField;
    private final JTextField toDateField;
    private final JComboBox<String> presetComboBox;
    private final JButton closeButton;

    /**
     * Constructs a new {@code BudgetFilterPanel} with default UI components and layout.
     * Initializes the filter and reset buttons, date labels and fields, preset combo box, and close button.
     */
    public BudgetFilterPanel() {
        filterButton = new JButton("Filter");
        filterButton.setForeground(Color.BLACK);
        resetButton = new JButton("Reset");
        fromDateLabel = new JLabel("From:");
        toDateLabel = new JLabel("To:");
        fromDateField = new JTextField(10);
        toDateField = new JTextField(10);
        presetComboBox = new JComboBox<>();
        closeButton = new JButton("Close");
        closeButton.setForeground(Color.RED);

        presetComboBox.addItem("Last Day");
        presetComboBox.addItem("Last Week");
        presetComboBox.addItem("Last Month");
        presetComboBox.addItem("Last Year");
        presetComboBox.addItem("Custom");

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(presetComboBox);
        topPanel.add(filterButton);
        topPanel.add(resetButton);
        topPanel.add(closeButton);
        add(topPanel);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        bottomPanel.add(fromDateLabel);
        bottomPanel.add(fromDateField);
        bottomPanel.add(toDateLabel);
        bottomPanel.add(toDateField);
        add(bottomPanel);
    }

    public JTextField getFromDateField(){
        return fromDateField;
    }

    public JTextField getToDateField(){
        return toDateField;
    }

    public JButton getFilterButton() {
        return filterButton;
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public JComboBox<String> getPresetComboBox() {
        return presetComboBox;
    }

    public String getFromDate() {
        return fromDateField.getText();
    }

    public String getToDate() {
        return toDateField.getText();
    }
}
