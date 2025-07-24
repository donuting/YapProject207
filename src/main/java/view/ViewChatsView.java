package view;

import interface_adapter.view_chats.ViewChatsController;
import interface_adapter.view_chats.ViewChatsState;
import interface_adapter.view_chats.ViewChatsViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for the view chats after coming from menu.
 */
public class ViewChatsView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "view chats";
    private final ViewChatsViewModel viewChatsViewModel;


    private final JLabel usernameLabel;
    private final JButton selfChatButton;
    private final JButton addChatButton;
    private final JButton backToMenuButton;

    private ViewChatsController viewChatsController;

    public ViewChatsView(ViewChatsViewModel viewChatsViewModel) {
        this.viewChatsViewModel = viewChatsViewModel;
        this.viewChatsViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title
        final JLabel title = new JLabel("View Chats");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        // Welcome message
        usernameLabel = new JLabel("User: ");
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Create buttons
        selfChatButton = new JButton("Self Chat");
        addChatButton = new JButton("Add Chat");
        backToMenuButton = new JButton("Back to Menu");

        // Set button properties
        setButtonProperties(selfChatButton);
        setButtonProperties(addChatButton);
        setButtonProperties(backToMenuButton);

        // Add action listeners
        selfChatButton.addActionListener(this);
        addChatButton.addActionListener(this);
        backToMenuButton.addActionListener(this);

        // Layout components
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(title);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(usernameLabel);
        this.add(Box.createRigidArea(new Dimension(0, 30)));
        this.add(selfChatButton);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(addChatButton);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(backToMenuButton);
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
        if (viewChatsController != null) {
            if (evt.getSource().equals(selfChatButton)) {
                viewChatsController.openSelfChat();
            } else if (evt.getSource().equals(addChatButton)) {
                viewChatsController.openAddChat();
            } else if (evt.getSource().equals(backToMenuButton)) {
                viewChatsController.backToMenu();
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ViewChatsState state = (ViewChatsState) evt.getNewValue();
        updateUsernameDisplay(state);
    }

    private void updateUsernameDisplay(ViewChatsState state) {
        if (state.getUsername() != null && !state.getUsername().isEmpty()) {
            usernameLabel.setText("User: " + state.getUsername());
        } else {
            usernameLabel.setText("User: ");
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewChatsController(ViewChatsController viewChatsController) {
        this.viewChatsController = viewChatsController;
    }
}