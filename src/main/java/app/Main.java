package app;

import javax.swing.JFrame;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main (String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        // add the Logout Use Case to the app using the appBuilder
        final JFrame application = appBuilder
                                            .addLoginView()
                                            .addSignupView()
                                            .addLoggedInView()
                                            .addAddChatView()
                                            .addFriendView()
                                            .addMainMenuView()
                                            .addViewChatsView()
                                            .addViewFriendsViewModel()
                                            .addViewGroupChatsView()
                                            .addSelfChatView()
                                            .addChatView()
                                            .addUserProfileView()
                                            .addProfileandSettingsView()
                                            .addSignupUseCase()
                                            .addLoginUseCase()
                                            .addChangePasswordUseCase()
                                            .addLogoutUseCase()
                                            .addMainMenuUseCase()
                                            .addViewChatsUseCase()
                                            .addViewFriendsUseCase()
                                            .addViewGroupChatsUseCase()
                                            .addAddFriendUseCase()
                                            .addAddChatUseCase()
                                            .addSelfChatUseCase()
                                            .addChatUseCase()
                                            .addUserProfileUseCase()
                                            .addProfileandSettingsUseCase()
                                            .build();

        application.pack();
        application.setVisible(true);
    }
}
