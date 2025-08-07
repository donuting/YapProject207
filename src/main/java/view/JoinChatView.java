package view;

import interface_adapter.join_chat.JoinChatController;
import interface_adapter.join_chat.JoinChatState;
import interface_adapter.join_chat.JoinChatViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class JoinChatView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "join chat";
    private final JoinChatViewModel joinChatViewModel;

    private final JTextField channelUrlInputField = new JTextField(15);

    private final JButton joinButton;
    private final JButton cancelButton;

    private JoinChatController joinChatController;

    public JoinChatView(JoinChatViewModel joinChatViewModel) {
        this.joinChatViewModel = joinChatViewModel;
        joinChatViewModel.addPropertyChangeListener(this);

        // Title
        final JLabel title = new JLabel(JoinChatViewModel.TITLE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Labels
        final LabelTextPanel channelUrlInfo = new LabelTextPanel(
                new JLabel(JoinChatViewModel.CHANNEL_URL), channelUrlInputField);

        // Buttons
        cancelButton = new JButton(JoinChatViewModel.CANCEL_BUTTON);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        joinButton = new JButton(JoinChatViewModel.JOIN_BUTTON);
        joinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        final JPanel buttons = new JPanel();
        buttons.add(cancelButton);
        buttons.add(joinButton);

        // Action Listeners
        cancelButton.addActionListener(this);
        joinButton.addActionListener(this);

        // Adding components to layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(channelUrlInfo);
        this.add(buttons);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param evt the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (joinChatController != null) {
            if (evt.getSource() == cancelButton) {
                joinChatController.switchToChatsView();
            }
            else if (evt.getSource() == joinButton) {
                joinChatController.execute(channelUrlInputField.getText());
            }
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
        final JoinChatState state = (JoinChatState) evt.getNewValue();
        setFields(state);
        if (state.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage());
        }
    }

    private void setFields(JoinChatState state) {
        channelUrlInputField.setText(state.getChannelUrl());
    }

    public String getViewName() {
        return viewName;
    }

    public void setJoinChatController(JoinChatController joinChatController) {
        this.joinChatController = joinChatController;
    }
}
