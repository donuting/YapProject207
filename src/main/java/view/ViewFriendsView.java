package view;

import interfaceadapter.view_friends.ViewFriendsController;
import interfaceadapter.view_friends.ViewFriendsState;
import interfaceadapter.view_friends.ViewFriendsViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public class ViewFriendsView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "view friends";
    private final ViewFriendsViewModel viewFriendsViewModel;

    private final JPanel viewFriendsPanel;
    private final JScrollPane viewFriendsScrollPane;
    private final JButton backToMenuButton;
    private final JButton addFriendButton;
    // A map from a JButton to the channel URL it brings the user to
    private final Map<JButton, String> viewPersonalChatButtons;
    // A map from a JButton to the user ID it will remove from the current user's friend list
    private final Map<JButton, String> removeFriendButtons;
    // A map from a JButton to the user ID the current user will block
    private final Map<JButton, String> blockFriendButtons;

    private ViewFriendsController viewFriendsController;

    public ViewFriendsView(ViewFriendsViewModel viewFriendsViewModel) {
        this.viewFriendsViewModel = viewFriendsViewModel;
        this.viewFriendsViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title
        final JLabel title = new JLabel("View Friends");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        // Create Buttons
        backToMenuButton = addActionListener(setButtonProperties(new JButton("Back to Menu")));
        addFriendButton = addActionListener(setButtonProperties(new JButton("Add Friend")));
        viewPersonalChatButtons = new HashMap<>();
        removeFriendButtons = new HashMap<>();
        blockFriendButtons = new HashMap<>();

        // Create display area
        viewFriendsPanel = new JPanel();
        viewFriendsPanel.setLayout(new BoxLayout(viewFriendsPanel, BoxLayout.Y_AXIS));
        viewFriendsPanel.setFont(new Font("Arial", Font.PLAIN, 14));
        viewFriendsPanel.setBackground(new Color(248, 248, 248));
        viewFriendsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        viewFriendsScrollPane = new JScrollPane(viewFriendsPanel);
        viewFriendsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        viewFriendsScrollPane.setPreferredSize(new Dimension(600, 400));

        this.add(title, BorderLayout.NORTH);
        this.add(addFriendButton);
        this.add(backToMenuButton);
        this.add(viewFriendsScrollPane, BorderLayout.SOUTH);
    }

    private JButton setButtonProperties(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        return button;
    }

    private JButton setViewPersonalChatButtonProperties(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(600, 40));
        button.setMaximumSize(new Dimension(600, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        return button;
    }

    private JButton setRemoveFriendButtonProperties(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(600, 20));
        button.setMaximumSize(new Dimension(600, 20));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        return button;
    }

    private JButton setBlockFriendButtonProperties(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(600, 20));
        button.setMaximumSize(new Dimension(600, 20));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        return button;
    }

    private void updateFriendsDisplay() {
        // Load friend info from state object into the display
        Map<String, String> channelToUserIdData = viewFriendsViewModel.getState().getChannelToUserIdData();
        for (Map.Entry<String, String> friendData: channelToUserIdData.entrySet()) {
            String personalChannelUrl = friendData.getKey();
            String friendId = friendData.getValue();
            if (!viewPersonalChatButtons.containsValue(personalChannelUrl)) {
                // Create Buttons
                JButton viewPersonalChannelButton =
                        addActionListener(setViewPersonalChatButtonProperties(new JButton("Message " + friendId)));
                viewPersonalChatButtons.put(viewPersonalChannelButton, personalChannelUrl);

                JButton removeFriendButton =
                        addActionListener(setRemoveFriendButtonProperties(new JButton("Remove Friend")));
                removeFriendButtons.put(removeFriendButton, friendId);

                JButton blockFriendButton =
                        addActionListener(setBlockFriendButtonProperties(new JButton("Block Friend")));
                blockFriendButtons.put(blockFriendButton, friendId);

                // Add the buttons to a new JPanel
                JPanel newFriendPanel = new JPanel();
                newFriendPanel.setLayout(new BoxLayout(newFriendPanel, FlowLayout.CENTER));
                newFriendPanel.setFont(new Font("Arial", Font.PLAIN, 14));
                newFriendPanel.setBackground(new Color(248, 248, 248));
                newFriendPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                newFriendPanel.setPreferredSize(new Dimension(600, 400));
                newFriendPanel.add(viewPersonalChannelButton);
                newFriendPanel.add(removeFriendButton);
                newFriendPanel.add(blockFriendButton);

                // Add the new JPanel to the display
                viewFriendsPanel.add(newFriendPanel);
            }
        }

        viewFriendsPanel.revalidate();
        viewFriendsPanel.repaint();
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
        if (viewFriendsController != null) {
            if (e.getSource() == backToMenuButton) {
                viewFriendsController.switchToMainMenu();
            } else if (e.getSource() == addFriendButton) {
                viewFriendsController.switchToAddFriend();
            } else if (viewPersonalChatButtons.containsKey(e.getSource())) {
                String channelUrl = viewPersonalChatButtons.get(e.getSource());
                String chatName = viewFriendsViewModel.getState().getChannelToUserIdData().get(channelUrl);
                handleViewChat(channelUrl, chatName);
            } else if (removeFriendButtons.containsKey(e.getSource())) {
                String removedId = removeFriendButtons.get(e.getSource());
                handleRemoveFriend(removedId);

            } else if (blockFriendButtons.containsKey(e.getSource())) {
                String blockedId = blockFriendButtons.get(e.getSource());
                handleBlockFriend(blockedId);
            }
        }
    }

    private void handleBlockFriend(String blockedId) {
        viewFriendsController.block(blockedId);
    }

    private void handleRemoveFriend(String removedId) {
        viewFriendsController.removeFriend(removedId);
    }

    private void handleViewChat(String channelUrl, String chatName) {
        viewFriendsController.switchToViewChat(channelUrl, chatName);
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ViewFriendsState state = (ViewFriendsState) evt.getNewValue();

        // Check whether the chats need to be updated, and if so, update them
        if (viewFriendsController != null && state.getNeedsFriendInfo()) {
            viewPersonalChatButtons.clear();
            removeFriendButtons.clear();
            blockFriendButtons.clear();
            viewFriendsPanel.removeAll();
            viewFriendsController.loadFriends();
            updateFriendsDisplay();
            state.setNeedsFriendInfo(false);

        }

        // Handle any error messages
        if (state.getErrorMessage() != null && !state.getErrorMessage().isEmpty()) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewFriendsController(ViewFriendsController viewFriendsController) {
        this.viewFriendsController = viewFriendsController;
    }
}
