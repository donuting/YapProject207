package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
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

    private final JTextField FriendUsernameInputField = new JTextField(15);
    private final JTextField FriendIDInputField = new JTextField(15);
    // can lower text field to 8 probably since all ID's will be 8 digits
    // private AddFriendController addFriendController;    not ready

    private final JButton addFriend;
    private final JButton cancel;

    public AddFriendView(AddFriendViewModel addFriendViewModel) {
        this.addFriendViewModel  = addFriendViewModel;
        addFriendViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Adding Friend View");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Username"), FriendUsernameInputField);
        final LabelTextPanel FriendIDInfo = new LabelTextPanel(
                new JLabel("ID"), FriendIDInputField);

        final JPanel buttons = new JPanel();
        addFriend = new JButton("Add Friend");
        buttons.add(addFriend);
        cancel = new JButton("Cancel");
        buttons.add(cancel);

        addFriend.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(addFriend)) {
                            final AddFriendState currentState = addFriendViewModel.getState();

                            // addFriendController.execute(
                            // currentState.getUsername(),
                            // currentState.getUserID()
                            // );
                        }
                    }
                }
        );
    }

    public String getViewName() {
        return viewName;
    }
}
