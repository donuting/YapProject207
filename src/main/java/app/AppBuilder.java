package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import dataaccess.InMemorySelfChatUserDataAccessObject;
import dataaccess.SendBirdUserDataAccessObject;
import dataaccess.MessageDataAccessObject;
import entity.CommonUserFactory;
import entity.GroupChatFactory;
import entity.UserFactory;
import interfaceadapter.ViewManagerModel;

import interfaceadapter.add_chat.AddChatController;
import interfaceadapter.add_chat.AddChatPresenter;
import interfaceadapter.add_chat.AddChatViewModel;
import interfaceadapter.add_friend.AddFriendController;
import interfaceadapter.add_friend.AddFriendPresenter;
import interfaceadapter.add_friend.AddFriendViewModel;
import interfaceadapter.change_password.ChangePasswordController;
import interfaceadapter.change_password.ChangePasswordPresenter;
import interfaceadapter.change_password.LoggedInViewModel;
import interfaceadapter.login.LoginController;
import interfaceadapter.login.LoginPresenter;
import interfaceadapter.login.LoginViewModel;
import interfaceadapter.logout.LogoutController;
import interfaceadapter.logout.LogoutPresenter;
import interfaceadapter.main_menu.MainMenuController;
import interfaceadapter.main_menu.MainMenuViewModel;
import interfaceadapter.profile_and_settings.PandScontroller;
import interfaceadapter.profile_and_settings.PandSpresenter;
import interfaceadapter.profile_and_settings.PandSviewModel;
import interfaceadapter.self_chat.SelfChatController;
import interfaceadapter.self_chat.SelfChatPresenter;
import interfaceadapter.self_chat.SelfChatViewModel;
import interfaceadapter.signup.SignupController;
import interfaceadapter.signup.SignupPresenter;
import interfaceadapter.signup.SignupViewModel;
import interfaceadapter.view_chats.ViewChatsController;
import interfaceadapter.view_chats.ViewChatsViewModel;
import interfaceadapter.view_friends.ViewFriendsController;
import interfaceadapter.view_friends.ViewFriendsPresenter;
import interfaceadapter.view_friends.ViewFriendsViewModel;
import interfaceadapter.view_group_chats.ViewGroupChatsController;
import interfaceadapter.view_group_chats.ViewGroupChatsPresenter;
import interfaceadapter.view_group_chats.ViewGroupChatsViewModel;
import interfaceadapter.chat.ChatController;
import interfaceadapter.chat.ChatPresenter;
import interfaceadapter.chat.ChatViewModel;
import interfaceadapter.profile.UserProfileController;
import interfaceadapter.profile.UserProfilePresenter;
import interfaceadapter.profile.UserProfileViewModel;

import usecase.add_Bio.AddBioInputBoundary;
import usecase.add_Bio.AddBioInteractor;
import usecase.add_DOB.AddDobInputBoundary;
import usecase.add_DOB.AddDobInteractor;
import usecase.add_friend.AddFriendInputBoundary;
import usecase.add_friend.AddFriendInteractor;
import usecase.add_friend.AddFriendOutputBoundary;
import usecase.block_friend.BlockFriendInputBoundary;
import usecase.block_friend.BlockFriendInteractor;
import usecase.change_password.ChangePasswordInputBoundary;
import usecase.change_password.ChangePasswordInteractor;
import usecase.change_password.ChangePasswordOutputBoundary;
import usecase.create_chat.CreateChatInputBoundary;
import usecase.create_chat.CreateChatInteractor;
import usecase.create_chat.CreateChatOutputBoundary;
import usecase.delete_account.DeleteAccountInputBoundary;
import usecase.delete_account.DeleteAccountInteractor;
import usecase.delete_message.DeleteMessageInputBoundary;
import usecase.delete_message.DeleteMessageInteractor;
import usecase.join_chat.JoinChatInputBoundary;
import usecase.join_chat.JoinChatInteractor;
import usecase.leave_chat.LeaveChatInputBoundary;
import usecase.leave_chat.LeaveChatInteractor;
import usecase.load_friends.LoadFriendsInputBoundary;
import usecase.load_friends.LoadFriendsInteractor;
import usecase.load_group_chats.LoadGroupChatsInputBoundary;
import usecase.load_group_chats.LoadGroupChatsInteractor;
import usecase.login.LoginInputBoundary;
import usecase.login.LoginInteractor;
import usecase.login.LoginOutputBoundary;
import usecase.logout.LogoutInputBoundary;
import usecase.logout.LogoutInteractor;
import usecase.logout.LogoutOutputBoundary;
import usecase.remove_friend.RemoveFriendInputBoundary;
import usecase.remove_friend.RemoveFriendInteractor;
import usecase.self_chat.SelfChatInputBoundary;
import usecase.self_chat.SelfChatInteractor;
import usecase.self_chat.SelfChatOutputBoundary;
import usecase.signup.SignupInputBoundary;
import usecase.signup.SignupInteractor;
import usecase.signup.SignupOutputBoundary;
import usecase.send_message.SendMessageInputBoundary;
import usecase.send_message.SendMessageInteractor;
import usecase.profile.UserProfileInputBoundary;
import usecase.profile.UserProfileInteractor;
import usecase.profile.UserProfileOutputBoundary;
import usecase.update_chat.UpdateChatInputBoundary;
import usecase.update_chat.UpdateChatInteractor;
import view.*;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final UserFactory userFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private final boolean USE_SENDBIRD = true; // Set to true to use SendBird, false for in-memory

    // private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();
    // To do: Add the DAOs to the App Builder (so far just SendBirdUserDataAccessObject)
    private final SendBirdUserDataAccessObject userDataAccessObject = new SendBirdUserDataAccessObject();

    private final InMemorySelfChatUserDataAccessObject selfChatDataAccessObject =
            new InMemorySelfChatUserDataAccessObject(userDataAccessObject);
    private final MessageDataAccessObject messageDataAccessObject = new MessageDataAccessObject();

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
  
    private MainMenuView mainMenuView;
    private MainMenuViewModel mainMenuViewModel;
    private ViewChatsView viewChatsView;
    private ViewChatsViewModel viewChatsViewModel;
    private LogoutController logoutController;

    private AddChatView addChatView;
    private AddChatViewModel addChatViewModel;
    private AddFriendView addFriendView;
    private AddFriendViewModel addFriendViewModel;
    private SelfChatView selfChatView;
    private SelfChatViewModel selfChatViewModel;

    private ChatView chatView;
    private ChatViewModel chatViewModel;
    private UserProfileView userProfileView;
    private UserProfileViewModel userProfileViewModel;

    private ProfileandSettingView profileandSettingView;
    private PandSviewModel profileandSettingViewModel;

    private ViewGroupChatsView viewGroupChatsView;
    private ViewGroupChatsViewModel viewGroupChatsViewModel;

    private ViewFriendsView viewFriendsView;
    private ViewFriendsViewModel viewFriendsViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    /**
     * Adds the Main Menu View to the application.
     * @return this builder
     */
    public AppBuilder addMainMenuView() {
        mainMenuViewModel = new MainMenuViewModel();
        mainMenuView = new MainMenuView(mainMenuViewModel);
        cardPanel.add(mainMenuView, mainMenuView.getViewName());
        return this;
    }

    /**
     * Adds the View Chats View to the application.
     * @return this builder
     */
    public AppBuilder addViewChatsView() {
        viewChatsViewModel = new ViewChatsViewModel();
        viewChatsView = new ViewChatsView(viewChatsViewModel);
        cardPanel.add(viewChatsView, viewChatsView.getViewName());
        return this;
    }

    /**
     * Adds the View Friends View to the application.
     * @return this builder
     */
    public AppBuilder addViewFriendsViewModel() {
        viewFriendsViewModel = new ViewFriendsViewModel();
        viewFriendsView = new ViewFriendsView(viewFriendsViewModel);
        cardPanel.add(viewFriendsView, viewFriendsView.getViewName());
        return this;
    }

    /**
     * Adds the View Group Chats View to the application.
     * @return this builder
     */
    public AppBuilder addViewGroupChatsView() {
        viewGroupChatsViewModel = new ViewGroupChatsViewModel();
        viewGroupChatsView = new ViewGroupChatsView(viewGroupChatsViewModel);
        cardPanel.add(viewGroupChatsView, viewGroupChatsView.getViewName());
        return this;
    }

    /**
     * Adds the AddChat View to the application.
     * @return this builder
     */
    public AppBuilder addAddChatView() {
        addChatViewModel = new AddChatViewModel();
        addChatView = new AddChatView(addChatViewModel);
        cardPanel.add(addChatView, addChatView.getViewName());
        return this;
    }

    /**
     * Adds the AddFriend View to the application.
     * @return this builder
     */
    public AppBuilder addFriendView() {
        addFriendViewModel = new AddFriendViewModel();
        addFriendView = new AddFriendView(addFriendViewModel);
        cardPanel.add(addFriendView, addFriendView.getViewName());
        return this;
    }

    /**
     * Adds the Self Chat View to the application.
     * @return this builder
     */
    public AppBuilder addSelfChatView() {
        selfChatViewModel = new SelfChatViewModel();
        selfChatView = new SelfChatView(selfChatViewModel);
        cardPanel.add(selfChatView, selfChatView.getViewName());
        return this;
    }

    /**
     * Adds the Chat View to the application.
     * @return this builder
     */
    public AppBuilder addChatView() {
        chatViewModel = new ChatViewModel();
        chatView = new ChatView(chatViewModel);
        cardPanel.add(chatView, chatView.getViewName());
        return this;
    }

    /**
     * Adds the User Profile View to the application.
     * @return this builder
     */
    public AppBuilder addUserProfileView() {
        userProfileViewModel = new UserProfileViewModel();
        userProfileView = new UserProfileView(userProfileViewModel);
        cardPanel.add(userProfileView, userProfileView.getViewName());
        return this;
    }

    /**
     * Adds the Profile and Settings View to the application.
     * @return this builder
     */
    public AppBuilder addProfileandSettingsView() {
        profileandSettingViewModel = new PandSviewModel();
        profileandSettingView = new ProfileandSettingView(profileandSettingViewModel);
        cardPanel.add(profileandSettingView, profileandSettingView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        // Changed to navigate to main menu
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                mainMenuViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Change Password Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
                new ChangePasswordPresenter(loggedInViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);

        final ChangePasswordController changePasswordController =
                new ChangePasswordController(changePasswordInteractor);

        loggedInView.setChangePasswordController(changePasswordController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
        return this;
    }

    /**
     * Adds the AddChat Use Case to the application.
     * @return this builder
     */
    public AppBuilder addAddChatUseCase() {
        final CreateChatOutputBoundary createChatOutputBoundary =
                new AddChatPresenter(viewManagerModel, addChatViewModel);
        final CreateChatInputBoundary createChatInteractor =
                new CreateChatInteractor(userDataAccessObject, createChatOutputBoundary, new GroupChatFactory());
        final AddChatController addChatController = new AddChatController(createChatInteractor, viewManagerModel);
        addChatView.setAddChatController(addChatController);
        return this;
    }

    /**
     * Adds the Add Friend Use Case to the application.
     * @return this builder
     */
    public AppBuilder addAddFriendUseCase() {
        final AddFriendOutputBoundary addFriendOutputBoundary = new AddFriendPresenter(viewManagerModel, addFriendViewModel);

        final AddFriendInputBoundary addFriendInteractor = new AddFriendInteractor(userDataAccessObject,
                addFriendOutputBoundary);

        final AddFriendController addFriendController = new AddFriendController(//viewManagerModel,
                addFriendInteractor);

        addFriendView.setAddFriendController(addFriendController);
        return this;
    }

    /**
     * Adds the Chat Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChatUseCase() {
        final ChatPresenter chatPresenter = new ChatPresenter(viewManagerModel, chatViewModel);

        final DeleteMessageInputBoundary deleteMessageInteractor = new DeleteMessageInteractor(
                userDataAccessObject, chatPresenter);

        final SendMessageInputBoundary sendMessageInteractor = new SendMessageInteractor(
                userDataAccessObject, chatPresenter, new entity.CommonMessageFactory());

        final UpdateChatInputBoundary updateChatInteractor = new UpdateChatInteractor(
                userDataAccessObject, chatPresenter);

        final JoinChatInputBoundary joinChatInteractor = new JoinChatInteractor(
                userDataAccessObject, chatPresenter);

        final ChatController chatController = new ChatController(
                sendMessageInteractor, deleteMessageInteractor, updateChatInteractor, joinChatInteractor, viewManagerModel, viewChatsViewModel);

        chatView.setChatController(chatController);
        return this;
    }

    /**
     * Adds the User Profile Use Case to the application.
     * @return this builder
     */
    public AppBuilder addUserProfileUseCase() {
        final UserProfileOutputBoundary userProfileOutputBoundary =
                new UserProfilePresenter(userProfileViewModel);

        final UserProfileInputBoundary userProfileInteractor =
                new UserProfileInteractor(userDataAccessObject, userProfileOutputBoundary);

        final UserProfileController userProfileController =
                new UserProfileController(userProfileInteractor, viewManagerModel);

        userProfileView.setUserProfileController(userProfileController);
        return this;
    }

    /**
     * Adds the Profile and Settings Use Case to the application.
     * @return this builder
     */
    public AppBuilder addProfileandSettingsUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
                new ChangePasswordPresenter(loggedInViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);

        final PandSpresenter profileAndSettingsPresenter =
                new PandSpresenter(profileandSettingViewModel);

        final AddBioInputBoundary addBioInputBoundary =
                new AddBioInteractor(userDataAccessObject, profileAndSettingsPresenter);

        final AddDobInputBoundary addDateOfBirthInputBoundary =
                new AddDobInteractor(userDataAccessObject, profileAndSettingsPresenter);

        final DeleteAccountInputBoundary deleteAccountInputBoundary =
                new DeleteAccountInteractor(userDataAccessObject, profileAndSettingsPresenter);

        final PandScontroller profileAndSettingsController = new PandScontroller(viewManagerModel,
                mainMenuViewModel,
                profileandSettingViewModel,
                changePasswordInteractor,
                addBioInputBoundary,
                addDateOfBirthInputBoundary,
                deleteAccountInputBoundary);

        profileandSettingView.setPandSController(profileAndSettingsController);
        return this;
    }

    /**
     * Adds the Main Menu Use Case to the application.
     * @return this builder
     */
    public AppBuilder addMainMenuUseCase() {
        final MainMenuController mainMenuController = new MainMenuController(viewManagerModel,
                logoutController,
                viewChatsViewModel,
                mainMenuViewModel,
                viewFriendsViewModel,
                profileandSettingViewModel);
        mainMenuView.setMainMenuController(mainMenuController);
        return this;
    }

    /**
     * Adds the View Chats Use Case to the application.
     * @return this builder
     */
    public AppBuilder addViewChatsUseCase() {
        final ViewChatsController viewChatsController = new ViewChatsController(viewManagerModel,
                addChatViewModel,
                viewChatsViewModel,
                selfChatViewModel,
                viewGroupChatsViewModel);
        viewChatsView.setViewChatsController(viewChatsController);
        return this;
    }

    /**
     * Adds the View Friends Use Case to the application.
     * @return this builder
     */
    public AppBuilder addViewFriendsUseCase() {
        final ViewFriendsPresenter viewFriendsPresenter =
                new ViewFriendsPresenter(viewFriendsViewModel);
        final RemoveFriendInputBoundary removeFriendInputBoundary =
                new RemoveFriendInteractor(userDataAccessObject, viewFriendsPresenter);
        final BlockFriendInputBoundary blockFriendInputBoundary =
                new BlockFriendInteractor(userDataAccessObject, viewFriendsPresenter);
        final LoadFriendsInputBoundary loadFriendsInputBoundary =
                new LoadFriendsInteractor(userDataAccessObject, viewFriendsPresenter);
        final ViewFriendsController viewFriendsController =
                new ViewFriendsController(removeFriendInputBoundary, blockFriendInputBoundary,
                        loadFriendsInputBoundary, chatViewModel, viewManagerModel);
        viewFriendsView.setViewFriendsController(viewFriendsController);
        return this;
    }

    /**
     * Adds the View Group Chats Use Case to the application.
     * @return this builder
     */
    public AppBuilder addViewGroupChatsUseCase() {
        final ViewGroupChatsPresenter viewGroupChatsPresenter =
                new ViewGroupChatsPresenter(viewGroupChatsViewModel);
        final JoinChatInputBoundary joinChatInputBoundary =
                new JoinChatInteractor(userDataAccessObject, viewGroupChatsPresenter);
        final LeaveChatInputBoundary leaveChatInputBoundary =
                new LeaveChatInteractor(userDataAccessObject, viewGroupChatsPresenter);
        final LoadGroupChatsInputBoundary loadGroupChatsInputBoundary =
                new LoadGroupChatsInteractor(userDataAccessObject, viewGroupChatsPresenter);
        final ViewGroupChatsController viewGroupChatsController =
                new ViewGroupChatsController(joinChatInputBoundary, leaveChatInputBoundary,
                        loadGroupChatsInputBoundary, chatViewModel, viewManagerModel);
        viewGroupChatsView.setViewGroupChatsController(viewGroupChatsController);
        return this;
    }

    /**
     * Adds the Self Chat Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSelfChatUseCase() {
        final SelfChatOutputBoundary selfChatOutputBoundary = new SelfChatPresenter(selfChatViewModel);
        final SelfChatInputBoundary selfChatInteractor = new SelfChatInteractor(
                selfChatDataAccessObject, selfChatOutputBoundary);

        final SelfChatController selfChatController = new SelfChatController(
                selfChatInteractor, viewManagerModel);
        selfChatView.setSelfChatController(selfChatController);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Yap App");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
