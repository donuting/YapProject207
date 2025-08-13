package interfaceadapter.add_friend;

import interfaceadapter.ViewManagerModel;
import usecase.add_friend.AddFriendOutputBoundary;
import usecase.add_friend.AddFriendOutputData;

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
