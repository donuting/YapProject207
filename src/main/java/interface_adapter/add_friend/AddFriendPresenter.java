package interface_adapter.add_friend;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.main_menu.MainMenuViewModel;
import use_case.add_friend.AddFriendOutputBoundary;
import use_case.add_friend.AddFriendOutputData;

public class AddFriendPresenter implements AddFriendOutputBoundary {

    private AddFriendViewModel addFriendViewModel;

    public AddFriendPresenter(AddFriendViewModel addFriendViewModel) {
        this.addFriendViewModel = addFriendViewModel;
    }

    @Override
    public void prepareSuccessView(AddFriendOutputData outputData) {
        final AddFriendState addFriendState = addFriendViewModel.getState();
        addFriendState.setSuccessMessage(outputData.getFriendUsername() + " added");
        addFriendState.setErrorMessage(null);
        addFriendState.setFriendUsername("");
        addFriendState.setFriendUserID("");
        addFriendViewModel.setState(addFriendState);
        addFriendViewModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String errorMessage, AddFriendOutputData outputData) {
        addFriendViewModel.setErrorMessage(errorMessage);
        addFriendViewModel.firePropertyChanged();

    }


}
