package interface_adapter.add_friend;

import interface_adapter.ViewManagerModel;
import use_case.add_friend.AddFriendOutputBoundary;
import use_case.add_friend.AddFriendOutputData;

public class AddFriendPresenter implements AddFriendOutputBoundary {

    private AddFriendViewModel addFriendViewModel;
    private ViewManagerModel viewManagerModel;

    public AddFriendPresenter(ViewManagerModel viewManagerModel, AddFriendViewModel addFriendViewModel) {
        this.addFriendViewModel = addFriendViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(AddFriendOutputData outputData) {
        final AddFriendState addFriendState = addFriendViewModel.getState();
        addFriendState.setSuccessMessage(outputData.getFriendUsername() + " has been added");
        addFriendState.setErrorMessage(null);
        addFriendState.setFriendUsername("");
        addFriendState.setFriendUserID("");
        addFriendViewModel.setState(addFriendState);
        addFriendViewModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String errorMessage) {
        addFriendViewModel.setErrorMessage(errorMessage);
        addFriendViewModel.firePropertyChanged();

    }

    @Override
    public void switchToMainMenuView() {
        viewManagerModel.setState("main menu");
        viewManagerModel.firePropertyChanged();
    }
}
