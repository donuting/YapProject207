package view;

import com.google.gson.JsonObject;
import interface_adapter.self_chat.SelfChatController;
import interface_adapter.self_chat.SelfChatState;
import interface_adapter.self_chat.SelfChatViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

/**
 * The View for the self chat functionality.
 */
public class SelfChatView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "self chat";
    private final SelfChatViewModel selfChatViewModel;

    private final JLabel titleLabel;
    private final JPanel chatArea;
    private final Map<JTextArea, String> messages = new HashMap<>();
    private final JScrollPane chatScrollPane;
    private final JTextField messageField;
    private final JButton sendButton;
    private final JButton backButton;
    private final JButton clearChatButton;
    private final JButton saveBirthdayButton;

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

        saveBirthdayButton = new JButton("Save Birthday");
        saveBirthdayButton.setFont(new Font("Arial", Font.PLAIN, 12));
        saveBirthdayButton.addActionListener(this);

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
        chatArea = new JPanel();
        chatArea.setLayout(new BoxLayout(chatArea, BoxLayout.Y_AXIS));
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        chatArea.setBackground(new Color(248, 248, 248));
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

        // Initialize with existing messages when controller is set
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Control buttons panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.add(saveBirthdayButton);
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
            } else if (evt.getSource().equals(saveBirthdayButton)) {
                handleSaveBirthday();
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

    private void handleSaveBirthday() {
        // Create input panel for name and date
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JLabel dateLabel = new JLabel("Date (YYYYMMDD):");
        JTextField dateField = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(dateLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        inputPanel.add(dateField, gbc);

        int result = JOptionPane.showConfirmDialog(
                this,
                inputPanel,
                "Save Birthday",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String date = dateField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (date.isEmpty() || date.length() != 8 || !date.matches("\\d{8}")) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid 8-digit date (YYYYMMDD).",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            selfChatController.saveBirthday(name, date);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SelfChatState state = (SelfChatState) evt.getNewValue();
        updateChatDisplay();

        // Handle any error messages
        if (state.getErrorMessage() != null && !state.getErrorMessage().isEmpty()) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            // Clear the error message after showing it
            selfChatViewModel.setErrorMessage("");
        }
    }

    private void updateChatDisplay() {
        SelfChatState state = selfChatViewModel.getState();
        Map<Integer, JsonObject> messageData = state.getMessages();

        if (messageData.isEmpty()) {
            chatArea.removeAll();
            messages.clear();
            chatArea.revalidate();
            chatArea.repaint();
            titleLabel.setText("Self Chat (0 messages)");
            return;
        }

        // Clear existing messages from display but keep track of what we've shown
        java.util.Set<String> existingMessageIds = new java.util.HashSet<>();
        for (String messageId : messages.values()) {
            existingMessageIds.add(messageId);
        }

        for (JsonObject jsonObject : messageData.values()) {
            String newMessageId = jsonObject.get("message_ID").getAsString();
            if (!existingMessageIds.contains(newMessageId)) {
                // Create new text area for the message
                JTextArea messageSpace = new JTextArea();
                messageSpace.setAlignmentX(Component.LEFT_ALIGNMENT);
                messageSpace.setFont(new Font("Arial", Font.PLAIN, 14));
                messageSpace.setLineWrap(true);
                messageSpace.setWrapStyleWord(true);
                messageSpace.setEditable(false);
                messageSpace.setOpaque(true);
                messageSpace.setBackground(Color.WHITE);
                messageSpace.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                        BorderFactory.createEmptyBorder(8, 8, 8, 8)
                ));

                // Set the message text
                String messageText = jsonObject.get("message").getAsString();
                String timestamp = jsonObject.has("timestamp") ? jsonObject.get("timestamp").getAsString() : "";
                if (!timestamp.isEmpty()) {
                    messageSpace.setText(messageText + "\n" + timestamp);
                } else {
                    messageSpace.setText(messageText);
                }

                // Dynamically set size based on content
                messageSpace.setPreferredSize(null);
                messageSpace.setMaximumSize(new Dimension(Integer.MAX_VALUE, messageSpace.getPreferredSize().height));

                // Add the new message to the list of messages
                messages.put(messageSpace, newMessageId);

                // Add the new message to the chat area
                chatArea.add(messageSpace);
                chatArea.add(Box.createRigidArea(new Dimension(0, 5))); // Add spacing between messages
            }
        }

        // Revalidate and repaint the chat area
        chatArea.revalidate();
        chatArea.repaint();

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
        // Load existing messages when controller is set
        if (selfChatController != null) {
            selfChatController.loadMessages();
        }
    }
}