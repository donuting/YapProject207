package view;

import interface_adapter.main_menu.MainMenuController;
import interface_adapter.main_menu.MainMenuState;
import interface_adapter.main_menu.MainMenuViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for the main menu after successful login.
 */
public class MainMenuView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "main menu";
    private final MainMenuViewModel mainMenuViewModel;

    private final JButton viewChatsButton;
    private final JButton profileSettingsButton;
    private final JButton viewFriendsButton;
    private final JButton logoutButton;

    private MainMenuController mainMenuController;

    public MainMenuView(MainMenuViewModel mainMenuViewModel) {
        this.mainMenuViewModel = mainMenuViewModel;
        this.mainMenuViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title
        final JLabel title = new JLabel("Main Menu");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        // Welcome message
        final JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Create buttons
        viewChatsButton = new JButton("View Chats");
        profileSettingsButton = new JButton("Profile & Settings");
        viewFriendsButton = new JButton("View Friends");
        logoutButton = new JButton("Logout");

        // Set button properties
        setButtonProperties(viewChatsButton);
        setButtonProperties(profileSettingsButton);
        setButtonProperties(viewFriendsButton);
        setButtonProperties(logoutButton);

        // Add action listeners
        viewChatsButton.addActionListener(this);
        profileSettingsButton.addActionListener(this);
        viewFriendsButton.addActionListener(this);
        logoutButton.addActionListener(this);

        // Layout components
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(title);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(welcomeLabel);
        this.add(Box.createRigidArea(new Dimension(0, 30)));
        this.add(viewChatsButton);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(profileSettingsButton);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(viewFriendsButton);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(logoutButton);
        this.add(Box.createVerticalGlue());
    }

    private void setButtonProperties(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (mainMenuController != null) {
            if (evt.getSource().equals(viewChatsButton)) {
                mainMenuController.switchToViewChats();
            } else if (evt.getSource().equals(profileSettingsButton)) {
                mainMenuController.switchToProfileSettings();
            } else if (evt.getSource().equals(viewFriendsButton)) {
                mainMenuController.switchToViewFriends();
            } else if (evt.getSource().equals(logoutButton)) {
                mainMenuController.switchToLogout();
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final MainMenuState state = (MainMenuState) evt.getNewValue();
        // Update UI based on state if needed
        // For example, update welcome message with username
        if (state.getUsername() != null && !state.getUsername().isEmpty()) {
            // You could update the welcome label here if needed
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }
}