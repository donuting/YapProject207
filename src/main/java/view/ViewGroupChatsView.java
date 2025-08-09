package view;

import interface_adapter.view_group_chats.ViewGroupChatsController;
import interface_adapter.view_group_chats.ViewGroupChatsState;
import interface_adapter.view_group_chats.ViewGroupChatsViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public class ViewGroupChatsView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "view group chats";
    private final ViewGroupChatsViewModel viewGroupChatsViewModel;

    private final JPanel viewGroupChatsPanel;
    private final JScrollPane viewGroupChatsScrollPane;
    private final JButton backToMenuButton;
    private final Map<JButton, String> viewGroupChatButtons;
    private final Map<JButton, String> removeGroupChatButtons;
    private final JButton joinChatButton;

    private ViewGroupChatsController viewGroupChatsController;

    public ViewGroupChatsView(ViewGroupChatsViewModel viewGroupChatsViewModel) {
        this.viewGroupChatsViewModel = viewGroupChatsViewModel;
        this.viewGroupChatsViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title
        final JLabel title = new JLabel("View Group Chats");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        // Create Buttons
        backToMenuButton = addActionListener(setButtonProperties(new JButton("Back to Menu")));
        joinChatButton = addActionListener(setButtonProperties(new JButton("Join Chat")));
        viewGroupChatButtons = new HashMap<>();
        removeGroupChatButtons = new HashMap<>();

        // Create display area
        viewGroupChatsPanel = new JPanel();
        viewGroupChatsPanel.setLayout(new BoxLayout(viewGroupChatsPanel, BoxLayout.Y_AXIS));
        viewGroupChatsPanel.setFont(new Font("Arial", Font.PLAIN, 14));
        viewGroupChatsPanel.setBackground(new Color(248, 248, 248));
        viewGroupChatsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        viewGroupChatsScrollPane = new JScrollPane(viewGroupChatsPanel);
        viewGroupChatsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        viewGroupChatsScrollPane.setPreferredSize(new Dimension(600, 400));

        // Add Buttons and Title
        this.add(title, BorderLayout.NORTH);
        this.add(joinChatButton);
        this.add(backToMenuButton);
        this.add(viewGroupChatsScrollPane, BorderLayout.SOUTH);
    }

    private JButton setButtonProperties(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        return button;
    }

    private JButton setViewGroupChatButtonProperties(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(400, 40));
        button.setMaximumSize(new Dimension(400, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        return button;
    }

    private JButton setDeleteGroupChatButtonProperties(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        return button;
    }

    private void updateGroupChatDisplay() {
        // Load group chat info from state object into the display
        Map<String, String> channelInfo = viewGroupChatsViewModel.getState().getChannelInfo();
        for (Map.Entry<String, String> channelData: channelInfo.entrySet()) {
            String channelUrl = channelData.getKey();
            String channelName = channelData.getValue();
            if (!viewGroupChatButtons.containsValue(channelUrl)) {
                // Create Buttons
                JButton newViewGroupChatButton = addActionListener(setViewGroupChatButtonProperties(new JButton(channelName)));
                viewGroupChatButtons.put(newViewGroupChatButton, channelUrl);
                JButton newRemoveGroupChatButton = addActionListener(setDeleteGroupChatButtonProperties(new JButton("Delete")));
                removeGroupChatButtons.put(newRemoveGroupChatButton, channelUrl);

                // Add them to a new JPanel
                JPanel newGroupChatPanel = new JPanel();
                newGroupChatPanel.setLayout(new BoxLayout(newGroupChatPanel, FlowLayout.CENTER));
                newGroupChatPanel.setFont(new Font("Arial", Font.PLAIN, 14));
                newGroupChatPanel.setBackground(new Color(248, 248, 248));
                newGroupChatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                newGroupChatPanel.setPreferredSize(new Dimension(400, 100));
                newGroupChatPanel.add(newViewGroupChatButton);
                newGroupChatPanel.add(newRemoveGroupChatButton);

                // Add the new JPanel to the display
                viewGroupChatsPanel.add(newGroupChatPanel);
            }
        }

        viewGroupChatsPanel.revalidate();
        viewGroupChatsPanel.repaint();
    }

    private JButton addActionListener(JButton jButton) {
        jButton.addActionListener(this);
        return jButton;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (viewGroupChatsController != null) {
            if (e.getSource().equals(backToMenuButton)) {
                viewGroupChatsController.switchToViewChats();
            } else if (e.getSource().equals(joinChatButton)) {
                handleJoinChat();
            } else if (viewGroupChatButtons.containsKey(e.getSource())) {
                String channelUrl = viewGroupChatButtons.get(e.getSource());
                String chatName = viewGroupChatsViewModel.getState().getChannelInfo().get(channelUrl);
                handleViewChat(channelUrl, chatName);
            } else if (removeGroupChatButtons.containsKey(e.getSource())) {
                handleDeleteChat(removeGroupChatButtons.get(e.getSource()));
            }
        }
    }

    private void handleDeleteChat(String channelUrl) {
        viewGroupChatsController.leaveChat(channelUrl);
    }

    private void handleViewChat(String channelUrl, String chatName) {
        viewGroupChatsController.switchToViewChat(channelUrl, chatName);
    }

    private void handleJoinChat() {
        String result = JOptionPane.showInputDialog(viewGroupChatsPanel, "Enter the URL of the chat you want to join", JOptionPane.OK_CANCEL_OPTION);
        if (result != null) {
            viewGroupChatsController.joinChat(result);
        }
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ViewGroupChatsState state = (ViewGroupChatsState) evt.getNewValue();

        // Check whether the chats need to be updated, and if so, update them
        if (viewGroupChatsController != null && state.getNeedsGroupChatInfo()) {
            viewGroupChatButtons.clear();
            removeGroupChatButtons.clear();
            viewGroupChatsPanel.removeAll();
            viewGroupChatsController.loadGroupChats();
            updateGroupChatDisplay();
            state.setNeedsGroupChatInfo(false);
        }

        // Handle any error messages
        if (state.getErrorMessage() != null && !state.getErrorMessage().isEmpty()) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewGroupChatsController(ViewGroupChatsController viewGroupChatsController) {
        this.viewGroupChatsController = viewGroupChatsController;
    }
}
