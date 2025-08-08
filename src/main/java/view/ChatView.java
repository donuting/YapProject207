package view;

import entity.Chat;
import entity.User;
import interface_adapter.chat.ChatController;
import interface_adapter.chat.ChatState;
import interface_adapter.chat.ChatViewModel;
import entity.Message;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * The View for when the user is in a chat.
 */
public class ChatView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "chat";
    private final ChatViewModel chatViewModel;

    private final JLabel chatNameLabel;
    private final JPanel messagesPanel;
    private final JScrollPane messagesScrollPane;
    private final JTextArea messageInputField;
    private final JButton sendButton;
    private final JButton backButton;
    private final JButton chatProfileButton;

    private ChatController chatController;

    public ChatView(ChatViewModel chatViewModel) {
        this.chatViewModel = chatViewModel;
        this.chatViewModel.addPropertyChangeListener(this);

        // Main panel setup
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        // Top panel with chat name and buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.LIGHT_GRAY);

        // Back button
        backButton = new JButton(ChatViewModel.BACK_BUTTON_LABEL);
        backButton.addActionListener(this);

        // Chat name
        chatNameLabel = new JLabel("Chat", SwingConstants.CENTER);
        chatNameLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Chat profile button
        chatProfileButton = new JButton(ChatViewModel.CHAT_PROFILE_BUTTON_LABEL);
        chatProfileButton.addActionListener(this);

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(chatNameLabel, BorderLayout.CENTER);
        topPanel.add(chatProfileButton, BorderLayout.EAST);

        // Messages panel (scrollable)
        messagesPanel = new JPanel();
        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));
        messagesPanel.setBackground(Color.WHITE);
        messagesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        messagesScrollPane = new JScrollPane(messagesPanel);
        messagesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        messagesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Bottom panel with message input and send button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        bottomPanel.setBackground(Color.WHITE);

        messageInputField = new JTextArea(3, 30);
        messageInputField.setLineWrap(true);
        messageInputField.setWrapStyleWord(true);
        messageInputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JScrollPane inputScrollPane = new JScrollPane(messageInputField);
        inputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        sendButton = new JButton(ChatViewModel.SEND_BUTTON_LABEL);
        sendButton.addActionListener(this);

        bottomPanel.add(inputScrollPane, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        // Add all components to main panel
        this.add(topPanel, BorderLayout.NORTH);
        this.add(messagesScrollPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());

        if (evt.getSource().equals(sendButton)) {
            final ChatState currentState = chatViewModel.getState();
            final String text = messageInputField.getText();

            if (currentState.getCurrentChat() != null && !text.trim().isEmpty()) {
                // Get the current user ID (you may need to adjust this based on your user management)
                User sender = null; // TODO: somehow bring sender
                Chat chat = currentState.getCurrentChat();

                chatController.execute(sender, text, chat);

                messageInputField.setText(""); // Clear the input field
            }
        } else if (evt.getSource().equals(backButton)) {
            chatController.switchToViewChatsView();
        } else if (evt.getSource().equals(chatProfileButton)) {
            // TODO: Implement chat profile functionality
            JOptionPane.showMessageDialog(this, "Chat Profile functionality to be implemented");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ChatState state = (ChatState) evt.getNewValue();
        updateChatDisplay(state);
    }

    private void updateChatDisplay(ChatState state) {
        // Update chat name
        if (state.getChatName() != null) {
            chatNameLabel.setText(state.getChatName());
        }

        // Update messages
        messagesPanel.removeAll();
        List<Message> messages = state.getMessages();

        for (Message message : messages) {
            JPanel messagePanel = createMessagePanel(message);
            messagesPanel.add(messagePanel);
            messagesPanel.add(Box.createVerticalStrut(5)); // Add spacing between messages
        }

        // Show error if any
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        messagesPanel.revalidate();
        messagesPanel.repaint();

        // Scroll to bottom
        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalScrollBar = messagesScrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
        });
    }

    private JPanel createMessagePanel(Message message) {
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(Color.WHITE);
        messagePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Sender info
        JLabel senderLabel = new JLabel(message.GetSenderId());
        senderLabel.setFont(new Font("Arial", Font.BOLD, 12));
        senderLabel.setForeground(Color.BLUE);

        // Message text
        JTextArea messageText = new JTextArea(message.GetText());
        messageText.setEditable(false);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setBackground(Color.WHITE);
        messageText.setBorder(null);

        // Timestamp (if available)
        JLabel timestampLabel = new JLabel(String.valueOf(message.getTimestamp()));
        timestampLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        timestampLabel.setForeground(Color.GRAY);

        messagePanel.add(senderLabel);
        messagePanel.add(messageText);
        messagePanel.add(timestampLabel);

        // Add a border to distinguish messages
        messagePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));

        return messagePanel;
    }

    public String getViewName() {
        return viewName;
    }

    public void setChatController(ChatController chatController) {
        this.chatController = chatController;
    }
}