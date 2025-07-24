package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.add_chat.AddChatController;
import interface_adapter.add_chat.AddChatState;
import interface_adapter.add_chat.AddChatViewModel;

/**
 * The View for adding new chats.
 */
public class AddChatView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "add chat";
    private final AddChatViewModel addChatViewModel;

    private final JTextField chatNameInputField = new JTextField(15);
    private final JLabel chatNameErrorField = new JLabel();
    private final JLabel userLabel = new JLabel();

    private final JButton createChatButton;
    private final JButton backToViewChatButton;

    private AddChatController addChatController;

    public AddChatView(AddChatViewModel addChatViewModel) {
        this.addChatViewModel = addChatViewModel;
        this.addChatViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title
        final JLabel title = new JLabel("Add Chat");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        // User display
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Chat name input
        final JLabel chatNameLabel = new JLabel("Chat Name:");
        chatNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        chatNameInputField.setAlignmentX(Component.CENTER_ALIGNMENT);
        chatNameInputField.setMaximumSize(new Dimension(250, 30));

        // Error field
        chatNameErrorField.setAlignmentX(Component.CENTER_ALIGNMENT);
        chatNameErrorField.setForeground(java.awt.Color.RED);

        // Buttons
        createChatButton = new JButton("Create Chat");
        backToViewChatButton = new JButton("Back to View Chat");

        setButtonProperties(createChatButton);
        setButtonProperties(backToViewChatButton);

        // Add action listeners
        createChatButton.addActionListener(this);
        backToViewChatButton.addActionListener(this);

        // Add document listener for chat name
        chatNameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final AddChatState currentState = addChatViewModel.getState();
                currentState.setChatName(chatNameInputField.getText());
                addChatViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        // Layout components
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(title);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(userLabel);
        this.add(Box.createRigidArea(new Dimension(0, 30)));

        this.add(chatNameLabel);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
        this.add(chatNameInputField);
        this.add(chatNameErrorField);
        this.add(Box.createRigidArea(new Dimension(0, 30)));

        this.add(createChatButton);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(backToViewChatButton);
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
        if (addChatController != null) {
            if (evt.getSource().equals(createChatButton)) {
                final AddChatState currentState = addChatViewModel.getState();
                addChatController.execute(currentState.getChatName(), currentState.getID());
            } else if (evt.getSource().equals(backToViewChatButton)) {
                addChatController.backToViewChat();
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final AddChatState state = (AddChatState) evt.getNewValue();
        setFields(state);

        // Display error
        chatNameErrorField.setText(state.getChatNameError());

        // Update user display
        if (state.getID() != null && !state.getID().isEmpty()) {
            userLabel.setText("User: " + state.getID());
        }

        // Show success message if chat created
        if (state.isSuccess()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Chat '" + state.getChatName() + "' created successfully!");
        }
    }

    private void setFields(AddChatState state) {
        chatNameInputField.setText(state.getChatName());
    }

    public String getViewName() {
        return viewName;
    }

    public void setAddChatController(AddChatController addChatController) {
        this.addChatController = addChatController;
    }
}