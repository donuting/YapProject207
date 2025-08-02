package interface_adapter.add_friend;

import interface_adapter.login.LoginViewModel;
import use_case.add_friend.AddFriendOutputBoundary;
import use_case.add_friend.AddFriendOutputData;

public class AddFriendPresenter implements AddFriendOutputBoundary {

    private LoginViewModel loginViewModel;
    private AddFriendViewModel addFriendViewModel;

    public AddFriendPresenter(LoginViewModel loginViewModel,
                              AddFriendViewModel addFriendViewModel) {
        this.loginViewModel = loginViewModel;
        this.addFriendViewModel = addFriendViewModel;
    }

    @Override
    public void prepareSuccessView(AddFriendOutputData response) {
        // todo
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final AddFriendState addFriendState = addFriendViewModel.getState();
        addFriendState.setAddFriendError(errorMessage);
        addFriendViewModel.firePropertyChanged();
    }


}
