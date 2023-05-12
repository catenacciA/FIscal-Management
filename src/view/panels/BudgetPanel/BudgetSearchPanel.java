package view.panels.BudgetPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BudgetSearchPanel extends JPanel {

    private JTextField searchTextField;
    private JButton upButton;
    private JButton downButton;
    private JButton searchButton;

    public BudgetSearchPanel() {
        // Set the layout manager for this panel
        setLayout(new GridBagLayout());

        // Create the text field and buttons
        searchTextField = new JTextField(20);
        upButton = new JButton("▲");
        downButton = new JButton("▼");

        ImageIcon searchIcon = new ImageIcon(Objects.requireNonNull(getClass().
                getResource("/resources/search.png")));
        searchButton = new JButton(searchIcon);

        // Set the margins and minimum size of the buttons
        Insets buttonMargin = new Insets(2, 2, 2, 2);
        Dimension buttonMinSize = new Dimension(30, 14);
        upButton.setMargin(buttonMargin);
        upButton.setMinimumSize(buttonMinSize);
        downButton.setMargin(buttonMargin);
        downButton.setMinimumSize(buttonMinSize);
        searchButton.setMargin(buttonMargin);
        searchButton.setMinimumSize(buttonMinSize);

        // Add the components to the panel using GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        add(searchButton, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 5, 0, 5);
        add(searchTextField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridheight = 1;
        add(upButton, gbc);

        gbc.gridy = 1;
        add(downButton, gbc);
    }

    public JTextField getSearchTextField() {
        return searchTextField;
    }

    public JButton getUpButton() {
        return upButton;
    }

    public JButton getDownButton() {
        return downButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }
}