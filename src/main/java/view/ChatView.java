package view;

import interfaceadapter.chat.ChatController;
import interfaceadapter.chat.ChatState;
import interfaceadapter.chat.ChatViewModel;
import entity.Message;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The View for when the user is in a chat.
 */
public class ChatView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "chat";
    private final ChatViewModel chatViewModel;

    private final JLabel chatNameLabel;
    private final JPanel messagesPanel;
    private final JPanel usersPanel;
    private final JScrollPane messagesScrollPane;
    private final JScrollPane usersScrollPane;

    // A list of message IDs corresponding to messages in this chat.
    private final List<Integer> messages = new ArrayList<>();

    // A map from a JButton to the message ID of the message it corresponds to.
    private final Map<JButton, Integer> deleteMessageButtons = new HashMap<>();
    private final JTextArea messageInputField;
    private final JButton sendButton;
    private final JButton backButton;
    private final JButton addMemberButton;

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

        // Add Member button
        addMemberButton = new JButton(ChatViewModel.ADD_MEMBER_BUTTON_LABEL);
        addMemberButton.addActionListener(this);


        // Chat name
        chatNameLabel = new JLabel("Chat", SwingConstants.CENTER);
        chatNameLabel.setFont(new Font("Arial", Font.BOLD, 18));

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(addMemberButton, BorderLayout.EAST);
        topPanel.add(chatNameLabel, BorderLayout.CENTER);

        // Center panel with message display and users display

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        centerPanel.setBackground(Color.WHITE);

        // Messages panel (scrollable)
        messagesPanel = new JPanel();
        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));
        messagesPanel.setBackground(Color.WHITE);
        messagesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        messagesScrollPane = new JScrollPane(messagesPanel);
        messagesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        messagesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        messagesScrollPane.setPreferredSize(new Dimension(400, 400));

        // Users panel (scrollable)
        usersPanel = new JPanel();
        usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS));
        usersPanel.setBackground(Color.WHITE);
        usersPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        usersScrollPane = new JScrollPane(usersPanel);
        usersScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        usersScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        usersScrollPane.setPreferredSize(new Dimension(100, 400));

        centerPanel.add(usersScrollPane);
        centerPanel.add(messagesScrollPane);

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
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());

        if (evt.getSource().equals(sendButton)) {
            final String text = messageInputField.getText();

            if (!text.trim().isEmpty()) {
                // The current chat and user are stored in memory, so all we need to input is the text.
                chatController.sendMessage(text);
                messageInputField.setText(""); // Clear the input field
            }
        } else if (evt.getSource().equals(backButton)) {
            chatController.switchToMainMenu();
        } else if (deleteMessageButtons.containsKey(evt.getSource())) {
            Integer messageId = deleteMessageButtons.get(evt.getSource());
            chatController.deleteMessage(messageId);
        } else if (evt.getSource().equals(addMemberButton)) {
            String newUsername = JOptionPane.showInputDialog(this, "Enter the username of the user you want to add", "", JOptionPane.OK_CANCEL_OPTION);
            String channelUrl = chatViewModel.getState().getCurrentChannelUrl();
            chatController.joinChat(channelUrl, newUsername);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ChatState state = (ChatState) evt.getNewValue();
        updateChatDisplay(state);
    }

    private void updateChatDisplay(ChatState state) {
        // Check if the chat is being actively updated, if not then start actively updating
        if (chatController != null) {
            if (state.getNeedsUpdate()) {
                // Clear the memory and messages before loading messages
                clearChatAndMemberList(state);
                state.setNeedsUpdate(false);
                chatController.updateChat(state.getCurrentChannelUrl());
            }
            if (state.getNeedsClearChat()) {
                // Clear the chat without loading messages
                clearChatAndMemberList(state);
                state.setNeedsClearChat(false);
            }
        }

        // Show add member button if the current chat is a group chat
        if (state.getIsGroupChat()) {
            addMemberButton.setVisible(true);
            addMemberButton.setEnabled(true);
        } else {
            addMemberButton.setVisible(false);
            addMemberButton.setEnabled(false);
        }

        // Update chat name
        if (state.getChatName() != null) {
            chatNameLabel.setText(state.getChatName());
        }

        // Update messages
        List<Message> updatedMessages = state.getMessages();
        List<Integer> messageIdsSentByUser = new ArrayList<>();
        for (Message message : state.getMessagesSentByUser()) {
            messageIdsSentByUser.add(message.GetMID());
        }

        for (Message message : updatedMessages) {
            // if the message is new
            if (!messages.contains(message.GetMID())) {
                JPanel messagePanel = createMessagePanel(message);

                // if the user sent the message, add a delete message button
                if (messageIdsSentByUser.contains(message.GetMID())) {
                    JButton deleteButton = new JButton("Delete Message");
                    deleteButton.addActionListener(this);
                    messagePanel.add(deleteButton);

                    // Save the delete message button
                    deleteMessageButtons.put(deleteButton, message.GetMID());
                }
                // Save the message ID
                messages.add(message.GetMID());

                messagesPanel.add(messagePanel);
                messagesPanel.add(Box.createVerticalStrut(5)); // Add spacing between messages
            }
        }

        // Update members of the chat
        usersPanel.removeAll();
        for (String username : state.getUsernames()) {
            JButton usernameButton = new JButton(username);
            usernameButton.addActionListener(this);
            usersPanel.add(usernameButton);
        }

        // Show error if any
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        messagesPanel.revalidate();
        messagesPanel.repaint();
        usersPanel.revalidate();
        usersPanel.repaint();

        // Scroll to bottom
        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalScrollBar = messagesScrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
        });
    }

    private void clearChatAndMemberList(ChatState state) {
        messages.clear();
        deleteMessageButtons.clear();
        messagesPanel.removeAll();

        usersPanel.removeAll();

        state.setMessages(new ArrayList<>());
        state.setMessagesSentByUser(new ArrayList<>());
        state.setUsernames(new ArrayList<>());

        state.setUsernames(new ArrayList<>());
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