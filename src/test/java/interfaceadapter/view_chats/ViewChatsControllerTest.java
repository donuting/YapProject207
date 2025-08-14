package interfaceadapter.view_chats;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.add_chat.AddChatViewModel;
import interfaceadapter.self_chat.SelfChatViewModel;
import interfaceadapter.view_group_chats.ViewGroupChatsViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ViewChatsControllerTest {
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    final AddChatViewModel addChatViewModel = new AddChatViewModel();
    final ViewChatsViewModel viewChatsViewModel = new ViewChatsViewModel();
    final SelfChatViewModel selfChatViewModel = new SelfChatViewModel();
    final ViewGroupChatsViewModel viewGroupChatsViewModel = new ViewGroupChatsViewModel();

    @Test
    void openSelfChatTest() {
        final SelfChatViewModel selfChatViewModelTest = new SelfChatViewModel() {
            @Override
            public void firePropertyChanged() {
                Assertions.assertTrue(true);
            }
        };

        final ViewManagerModel viewManagerModelTest = new ViewManagerModel() {
            @Override
            public void firePropertyChanged() {
                Assertions.assertEquals("self chat", getState());
            }

        };
        final ViewChatsController viewChatsController = new ViewChatsController(viewManagerModelTest,
                addChatViewModel,
                viewChatsViewModel,
                selfChatViewModelTest,
                viewGroupChatsViewModel);
        viewChatsController.openSelfChat();

    }

    @Test
    void openAddChatTest() {
        final AddChatViewModel addChatViewModelTest = new AddChatViewModel() {
            @Override
            public void firePropertyChanged() {
                Assertions.assertEquals("username", getState().getID());
            }
        };

        final ViewManagerModel viewManagerModelTest = new ViewManagerModel() {
            @Override
            public void firePropertyChanged() {
                Assertions.assertEquals("add chat", getState());
            }

        };
        final ViewChatsViewModel viewChatsViewModelTest = new ViewChatsViewModel();
        viewChatsViewModelTest.getState().setUsername("username");

        final ViewChatsController viewChatsController = new ViewChatsController(viewManagerModelTest,
                addChatViewModelTest,
                viewChatsViewModelTest,
                selfChatViewModel,
                viewGroupChatsViewModel);
        viewChatsController.openAddChat();

    }

    @Test
    void backToMenuTest() {
        final ViewManagerModel viewManagerModelTest = new ViewManagerModel() {
            @Override
            public void firePropertyChanged() {
                Assertions.assertEquals("main menu", getState());
            }
        };
        final ViewChatsController viewChatsController = new ViewChatsController(viewManagerModelTest,
                addChatViewModel,
                viewChatsViewModel,
                selfChatViewModel,
                viewGroupChatsViewModel);
        viewChatsController.backToMenu();
    }

    @Test
    void openGroupChatsTest() {
        final ViewGroupChatsViewModel viewGroupChatsViewModelTest = new ViewGroupChatsViewModel() {
        @Override
            public void firePropertyChanged() {
                Assertions.assertEquals("username", getState().getUsername());
                Assertions.assertTrue(getState().getNeedsGroupChatInfo());
            }
        };

        final ViewManagerModel viewManagerModelTest = new ViewManagerModel() {
            @Override
            public void firePropertyChanged() {
                Assertions.assertEquals("view group chats", getState());
            }

        };
        final ViewChatsViewModel viewChatsViewModelTest = new ViewChatsViewModel();
        viewChatsViewModelTest.getState().setUsername("username");

        final ViewChatsController viewChatsController = new ViewChatsController(viewManagerModelTest,
                addChatViewModel,
                viewChatsViewModelTest,
                selfChatViewModel,
                viewGroupChatsViewModelTest);
        viewChatsController.openGroupChats();
    }
}
