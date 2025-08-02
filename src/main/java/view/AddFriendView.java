package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.add_friend.AddFriendController;
import interface_adapter.add_friend.AddFriendState;
import interface_adapter.add_friend.AddFriendViewModel;


/**
 * The View for the AddFriend Use Case.
 */
public class AddFriendView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "add friend";
    private final AddFriendViewModel addFriendViewModel;

    private final JTextField currentUsernameInputField = new JTextField(15);
    private final JTextField friendUsernameInputField = new JTextField(15);
    private final JTextField friendIDInputField = new JTextField(15);
    // can lower text field to 8 probably since all ID's will be 8 digits

    private final JButton addFriendButton;
    private final JButton cancelButton;

    private AddFriendController addFriendController;

    public AddFriendView(AddFriendViewModel addFriendViewModel) {
        this.addFriendViewModel  = addFriendViewModel;
        addFriendViewModel.addPropertyChangeListener(this);

        // Title
        final JLabel title = new JLabel(AddFriendViewModel.TITLE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Labels
        final LabelTextPanel currentUsernameInfo = new LabelTextPanel(
                new JLabel(AddFriendViewModel.YOUR_USERNAME), currentUsernameInputField);
        final LabelTextPanel friendUsernameInfo = new LabelTextPanel(
                new JLabel(AddFriendViewModel.FRIEND_USERNAME), friendUsernameInputField);
        final LabelTextPanel friendIDInfo = new LabelTextPanel(
                new JLabel(AddFriendViewModel.FRIEND_ID), friendIDInputField);

        // Buttons
        addFriendButton = new JButton(AddFriendViewModel.ADD_BUTTON);
        addFriendButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton = new JButton(AddFriendViewModel.CANCEL_BUTTON);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        final JPanel buttons = new JPanel();
        buttons.add(addFriendButton);
        buttons.add(cancelButton);

        // Action Listeners
        addFriendButton.addActionListener(this);
        cancelButton.addActionListener(this);



        // Adding components to layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(currentUsernameInfo);
        this.add(friendUsernameInfo);
        this.add(friendIDInfo);
        this.add(buttons);


    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        if (addFriendController != null) {
            if (evt.getSource() == cancelButton) {
                addFriendController.switchToMainMenu();
            }
            else if (evt.getSource() == addFriendButton) {
                addFriendController.execute(currentUsernameInputField.getText(),
                        friendUsernameInputField.getText(), friendIDInputField.getText());
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final AddFriendState state = (AddFriendState) evt.getNewValue();
        setFields(state);
        if (state.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage());
        }
    }

    private void setFields(AddFriendState state) {
        currentUsernameInputField.setText(state.getCurrentUsername());
        friendUsernameInputField.setText(state.getFriendUsername());
        friendIDInputField.setText(state.getFriendUserID());
    }

    public String getViewName() {
        return viewName;
    }

    public void setAddFriendController(AddFriendController addFriendController) {
        this.addFriendController = addFriendController;
    }
}
