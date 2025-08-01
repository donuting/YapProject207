package view;

import interface_adapter.profile_and_settings.PandSController;
import interface_adapter.profile_and_settings.PandSState;
import interface_adapter.profile_and_settings.PandSViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
/**
 * The View for the Profile and Settings after clicking the "Profile and settings" button from Main menu.
 */
public class ProfileandSettingView extends JPanel implements PropertyChangeListener, ActionListener {
    private final PandSViewModel pandSViewModel;
    private final String viewName = "Profile And Settings";
    private final JLabel username;
    private final JButton changePasswordButton;
    private final JButton addBioButton;
    private final JButton addDOBButton;
    private final JButton backButton;
    private final JButton logoutButton;
    private  final JTextField changePassword = new JTextField();
    private final JTextField addBio = new JTextField();
    private final JTextField addDOB = new JTextField();
    private final JLabel UID;
    private final JLabel title;

    private PandSController pandSController;

    public ProfileandSettingView(PandSViewModel pandSViewModel) {
        this.pandSViewModel = pandSViewModel;
        this.pandSViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Title
        title = new JLabel(viewName);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(title);

        //Create buttons
        changePasswordButton = new JButton("Change Password");
        addBioButton = new JButton("Add Bio");
        addDOBButton = new JButton("Add DOB");
        backButton = new JButton("Back to Main Menu");
        logoutButton = new JButton("Logout");

        //Create Other components
        UID = new JLabel("UID =" + pandSViewModel.getState().getUID());
        username = new JLabel("Username =" + pandSViewModel.getState().getUsername());

        //Add action listeners
        changePasswordButton.addActionListener(this);
        addBioButton.addActionListener(this);
        addDOBButton.addActionListener(this);
        backButton.addActionListener(this);
        logoutButton.addActionListener(this);

        //Set component properties
        setProperties();

        //Creating and adding rows
        CreateAndAddRow(List.of(username, UID));
        CreateAndAddRow(List.of(changePasswordButton, changePassword));
        CreateAndAddRow(List.of(addBioButton, addBio));
        CreateAndAddRow(List.of(addDOBButton, addDOB));
        CreateAndAddRow(List.of(backButton, logoutButton));

    }

    /**
     * Helper method to set properties for all the JComponents.
     */
    private void setProperties() {
        setComponentProperties(username);
        setComponentProperties(changePasswordButton);
        setComponentProperties(addBioButton);
        setComponentProperties(addDOBButton);
        setComponentProperties(backButton);
        setComponentProperties(logoutButton);
        setComponentProperties(UID);
        setComponentProperties(changePassword);
        setComponentProperties(addBio);
        setComponentProperties(addDOB);
    }

    /**
     * Helper to set the property of a given component.
     */
    private void setComponentProperties(Component component) {
        component.setPreferredSize(new Dimension(200, 40));
        component.setMaximumSize(new Dimension(200, 40));
        component.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    private void CreateAndAddRow(List<Component> componentList){
        JPanel frame = new JPanel();
        for (Component component : componentList) {
            frame.add(component);
        }
        this.add(frame);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("Profile And Settings")) {
            PandSState pandSState = pandSViewModel.getState();
            UID.setText("UID =" + pandSViewModel.getState().getUID());
            username.setText("Username =" + pandSViewModel.getState().getUsername());
            changePassword.setText(pandSState.getChangePasswordText());
            addBio.setText(pandSState.getAddBioText());
            addDOB.setText(pandSState.getAddDOBText());
            this.revalidate();
            this.repaint();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (pandSController != null) {
             if (e.getSource() == changePasswordButton) {
                pandSController.changePassword(changePassword.getText());
            } else if (e.getSource() == addBioButton) {
                pandSController.addBio(addBio.getText());
            } else if (e.getSource() == addDOBButton) {
                pandSController.addDOB(addDOB.getText());
            } else if (e.getSource() == backButton) {
                pandSController.switchToMenu();
            } else if (e.getSource() == logoutButton) {
                pandSController.logout();
            }
        }
    }

    /**
     * A setter for the PandSController
     * @param pandSController is the controller to be set.
     */

    public void setPandSController(PandSController pandSController) {
        this.pandSController = pandSController;
    }

    public String getViewName() {
        return viewName;
    }
}
