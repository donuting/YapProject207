package view;

import interfaceadapter.profile.UserProfileController;
import interfaceadapter.profile.UserProfileState;
import interfaceadapter.profile.UserProfileViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The User Profile View displays and allows editing of user profile information.
 */
public class UserProfileView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "user profile";
    private final UserProfileViewModel userProfileViewModel;

    // UI Components
    private final JLabel titleLabel;
    private final JTextField usernameField;
    private final JTextField userIdField;
    private final JTextArea bioField;
    private final JTextField dobField;
    private final JButton backButton;
    private final JButton saveButton;
    private final JButton editButton;

    private UserProfileController userProfileController;
    private boolean isEditing = false;

    public UserProfileView(UserProfileViewModel userProfileViewModel) {
        this.userProfileViewModel = userProfileViewModel;
        this.userProfileViewModel.addPropertyChangeListener(this);

        // Initialize components
        titleLabel = new JLabel("User Profile");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        usernameField = new JTextField(20);
        userIdField = new JTextField(20);
        bioField = new JTextArea(4, 20);
        dobField = new JTextField(20);
        backButton = new JButton("Back");
        saveButton = new JButton("Save");
        editButton = new JButton("Edit");

        // Set initial state
        setFieldsEditable(false);
        bioField.setLineWrap(true);
        bioField.setWrapStyleWord(true);
        userIdField.setEditable(false); // ID should never be editable

        // Add action listeners
        backButton.addActionListener(this);
        saveButton.addActionListener(this);
        editButton.addActionListener(this);

        setupLayout();
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        // User ID
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("User ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(userIdField, gbc);

        // Bio
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Bio:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        JScrollPane bioScrollPane = new JScrollPane(bioField);
        bioScrollPane.setPreferredSize(new Dimension(300, 100));
        formPanel.add(bioScrollPane, gbc);

        // Date of Birth
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        formPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        formPanel.add(dobField, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(backButton);
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);

        // Initially hide save button
        saveButton.setVisible(false);

        // Add panels to main layout
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setFieldsEditable(boolean editable) {
        usernameField.setEditable(editable);
        bioField.setEditable(editable);
        dobField.setEditable(editable);

        // Change appearance based on edit state
        Color bgColor = editable ? Color.WHITE : new Color(245, 245, 245);
        usernameField.setBackground(bgColor);
        bioField.setBackground(bgColor);
        dobField.setBackground(bgColor);

        isEditing = editable;
        editButton.setVisible(!editable);
        saveButton.setVisible(editable);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(backButton)) {
            if (userProfileController != null) {
                userProfileController.goBack();
            }
        } else if (evt.getSource().equals(editButton)) {
            setFieldsEditable(true);
        } else if (evt.getSource().equals(saveButton)) {
            if (userProfileController != null) {
                String oldUsername = userProfileViewModel.getState().getUsername();
                String username = usernameField.getText().trim();
                String bio = bioField.getText().trim();
                String dob = dobField.getText().trim();

                // Basic validation
                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Username cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!isValidDateFormat(dob) && !dob.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter date in YYYY-MM-DD format or leave empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                userProfileController.saveProfile(oldUsername, username, bio, dob);
                setFieldsEditable(false);
            }
        }
    }

    private boolean isValidDateFormat(String date) {
        if (date == null || date.trim().isEmpty()) {
            return true; // Empty date is allowed
        }

        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final UserProfileState state = (UserProfileState) evt.getNewValue();
            updateFields(state);
        }
    }

    private void updateFields(UserProfileState state) {
        usernameField.setText(state.getUsername());
        userIdField.setText(state.getUserId());
        bioField.setText(state.getBio());
        dobField.setText(state.getDateOfBirth());

        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (state.getSuccessMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getSuccessMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setUserProfileController(UserProfileController controller) {
        this.userProfileController = controller;
    }
}