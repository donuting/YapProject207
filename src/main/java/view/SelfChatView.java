package view;

import interface_adapter.self_chat.SelfChatController;
import interface_adapter.self_chat.SelfChatState;
import interface_adapter.self_chat.SelfChatViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The View for the self chat functionality.
 */
public class SelfChatView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "self chat";
    private final SelfChatViewModel selfChatViewModel;

    private final JLabel titleLabel;
    private final JTextArea chatArea;
    private final JScrollPane chatScrollPane;
    private final JTextField messageField;
    private final JButton sendButton;
    private final JButton backButton;
    private final JButton clearChatButton;

    private SelfChatController selfChatController;

    public SelfChatView(SelfChatViewModel selfChatViewModel) {
        this.selfChatViewModel = selfChatViewModel;
        this.selfChatViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());

        // Initialize title label
        titleLabel = new JLabel("Self Chat", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Initialize buttons
        clearChatButton = new JButton("Clear Chat");
        clearChatButton.setFont(new Font("Arial", Font.PLAIN, 12));
        clearChatButton.addActionListener(this);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 12));
        backButton.addActionListener(this);

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setPreferredSize(new Dimension(80, 30));
        sendButton.addActionListener(this);

        // Initialize message field
        messageField = new JTextField();
        messageField.setFont(new Font("Arial", Font.PLAIN, 14));
        messageField.setPreferredSize(new Dimension(0, 30));
        messageField.addActionListener(this); // Allow Enter key to send

        // Create top panel with title and controls
        JPanel topPanel = createTopPanel();

        // Create chat display area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        chatArea.setBackground(new Color(248, 248, 248));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        chatScrollPane = new JScrollPane(chatArea);
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatScrollPane.setPreferredSize(new Dimension(600, 400));

        // Create bottom panel with message input
        JPanel bottomPanel = createBottomPanel();

        // Add components to main panel
        this.add(topPanel, BorderLayout.NORTH);
        this.add(chatScrollPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // Initialize with existing messages
        updateChatDisplay();
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Control buttons panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.add(clearChatButton);
        controlPanel.add(backButton);

        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(controlPanel, BorderLayout.EAST);

        return topPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Instructions label
        JLabel instructionsLabel = new JLabel("Type your message and press Enter or click Send");
        instructionsLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        instructionsLabel.setForeground(Color.GRAY);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        bottomPanel.add(instructionsLabel, BorderLayout.NORTH);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 5)), BorderLayout.CENTER);
        bottomPanel.add(inputPanel, BorderLayout.SOUTH);

        return bottomPanel;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (selfChatController != null) {
            if (evt.getSource().equals(sendButton) || evt.getSource().equals(messageField)) {
                handleSendMessage();
            } else if (evt.getSource().equals(backButton)) {
                selfChatController.backToViewChats();
            } else if (evt.getSource().equals(clearChatButton)) {
                handleClearChat();
            }
        }
    }

    private void handleSendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            selfChatController.sendMessage(message);
            messageField.setText("");
            messageField.requestFocus();
        }
    }

    private void handleClearChat() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to clear all messages? This cannot be undone.",
                "Clear Chat",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            selfChatController.clearChat();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SelfChatState state = (SelfChatState) evt.getNewValue();
        updateChatDisplay();

        // Handle any error messages
        if (state.getErrorMessage() != null && !state.getErrorMessage().isEmpty()) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateChatDisplay() {
        SelfChatState state = selfChatViewModel.getState();
        List<String> messages = state.getMessages();
        List<LocalDateTime> timestamps = state.getTimestamps();

        StringBuilder chatText = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");

        for (int i = 0; i < messages.size(); i++) {
            if (i < timestamps.size()) {
                chatText.append("[").append(timestamps.get(i).format(formatter)).append("]\n");
            }
            chatText.append(messages.get(i)).append("\n\n");
        }

        chatArea.setText(chatText.toString());

        // Auto-scroll to bottom
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = chatScrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });

        // Update title with message count
        int messageCount = messages.size();
        titleLabel.setText("Self Chat (" + messageCount + " message" + (messageCount != 1 ? "s" : "") + ")");
    }

    public String getViewName() {
        return viewName;
    }

    public void setSelfChatController(SelfChatController selfChatController) {
        this.selfChatController = selfChatController;
    }
}