package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.InMemoryUserDataAccessObject;
import data_access.InMemorySelfChatUserDataAccessObject;
import data_access.MessageDataAccessObject;
import entity.CommonUserFactory;
import entity.MessageFactory;
import entity.UserFactory;

import interface_adapter.add_chat.AddChatController;
import interface_adapter.add_chat.AddChatPresenter;
import interface_adapter.add_chat.AddChatViewModel;
import interface_adapter.add_friend.AddFriendController;
import interface_adapter.add_friend.AddFriendPresenter;
import interface_adapter.add_friend.AddFriendViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.main_menu.MainMenuController;
import interface_adapter.main_menu.MainMenuViewModel;
import interface_adapter.profile_and_settings.PandSController;
import interface_adapter.profile_and_settings.PandSPresenter;
import interface_adapter.profile_and_settings.PandSViewModel;
import interface_adapter.view_chats.ViewChatsController;
import interface_adapter.view_chats.ViewChatsViewModel;
import interface_adapter.self_chat.SelfChatController;
import interface_adapter.self_chat.SelfChatPresenter;
import interface_adapter.self_chat.SelfChatViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;


import use_case.add_friend.AddFriendInputBoundary;
import use_case.add_friend.AddFriendInteractor;
import use_case.add_friend.AddFriendUserDataAccessInterface;

import use_case.add_Bio.AddBioInputBoundary;
import use_case.add_Bio.AddBioInteractor;
import use_case.add_Bio.AddBioOutputBoundary;
import use_case.add_DOB.AddDOBInputBoundary;
import use_case.add_DOB.AddDOBInteractor;

import use_case.create_chat.CreateChatInputBoundary;
import use_case.create_chat.CreateChatInteractor;
import use_case.create_chat.CreateChatOutputBoundary;
import use_case.add_friend.AddFriendOutputBoundary;
import use_case.self_chat.SelfChatInputBoundary;
import use_case.self_chat.SelfChatInteractor;
import use_case.self_chat.SelfChatOutputBoundary;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.send_message.SendMessageDataAccessInterface;
import use_case.send_message.SendMessageInputBoundary;
import use_case.send_message.SendMessageInteractor;
import use_case.send_message.SendMessageOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;

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

    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject(); // Todo: Add the DAOs to the App Builder (so far just SendBirdUserDataAccessObject and GroupChatDataAccessObject)
    private final InMemorySelfChatUserDataAccessObject selfChatDataAccessObject = new InMemorySelfChatUserDataAccessObject();
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

    private ProfileandSettingView profileandSettingView;
    private PandSViewModel profileandSettingViewModel;


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

    public AppBuilder addProfileandSettingsView(){
        profileandSettingViewModel = new PandSViewModel();
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
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                mainMenuViewModel, loginViewModel);  // Changed to navigate to main menu
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


//    /**
//     * Adds the AddChat Use Case to the application.
//     * @return this builder
//     */
//    public AppBuilder addAddChatUseCase() {
//        final CreateChatOutputBoundary createChatOutputBoundary = new AddChatPresenter(viewManagerModel, addChatViewModel);
//        final CreateChatInputBoundary createChatInteractor = new CreateChatInteractor(groupChatDataAccessObject, groupChatPresenter, chatFactory);
//        final AddChatController addChatController = new AddChatController(createChatInteractor);
//        addChatView.setAddChatController(addChatController);
//        return this;
//    }

    /**
     * Adds the Add Friend Use Case to the application.
     * @return this builder
     */
    public AppBuilder addAddFriendUseCase() {
        final AddFriendOutputBoundary addFriendOutputBoundary = new AddFriendPresenter(addFriendViewModel);

        final AddFriendInputBoundary addFriendInteractor = new AddFriendInteractor(userDataAccessObject,
                addFriendOutputBoundary);

        final AddFriendController addFriendController = new AddFriendController(viewManagerModel,
                addFriendInteractor);

        addFriendView.setAddFriendController(addFriendController);
        return this;
    }

    public AppBuilder addProfileandSettingsUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
                new ChangePasswordPresenter(loggedInViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);

        final PandSPresenter pandSPresenter =
                new PandSPresenter(profileandSettingViewModel);

        final AddBioInputBoundary addBioInputBoundary =
                new AddBioInteractor(userDataAccessObject, pandSPresenter, userFactory);

        final AddDOBInputBoundary addDOBInputBoundary =
                new AddDOBInteractor(userDataAccessObject, pandSPresenter, userFactory);


        final PandSController pandSController = new PandSController(viewManagerModel,mainMenuViewModel,profileandSettingViewModel, changePasswordInteractor, addBioInputBoundary, addDOBInputBoundary);
        profileandSettingView.setPandSController(pandSController);
        return this;
    }

    /**
     * Adds the Main Menu Use Case to the application.
     * @return this builder
     */
    public AppBuilder addMainMenuUseCase() {
        final MainMenuController mainMenuController = new MainMenuController(viewManagerModel, logoutController, viewChatsViewModel, mainMenuViewModel, addFriendViewModel, profileandSettingViewModel);
        mainMenuView.setMainMenuController(mainMenuController);
        return this;
    }

    /**
     * Adds the View Chats Use Case to the application.
     * @return this builder
     */
    public AppBuilder addViewChatsUseCase() {
        final ViewChatsController viewChatsController = new ViewChatsController(viewManagerModel, addChatViewModel, viewChatsViewModel, selfChatViewModel);
        viewChatsView.setViewChatsController(viewChatsController);
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
