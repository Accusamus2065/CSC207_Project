package interface_adapter.choosepatient;//package interface_adapter.login;



import interface_adapter.ViewManagerModel;

import interface_adapter.chat.ConversationViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

import java.util.List;


public class ChoosePatientPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private ViewManagerModel viewManagerModel;

    public ChoosePatientPresenter(ViewManagerModel viewManagerModel,
                           ConversationViewModel conversationViewModel,
                          
                          ChoosePatientViewModel choosePatientViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // On success, switch to the logged in view.

        LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setUsername(response.getUsername());
        this.loggedInViewModel.setState(loggedInState);
        this.loggedInViewModel.firePropertyChanged();

        this.viewManagerModel.setActiveView(loggedInViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        LoginState loginState = loginViewModel.getState();
        loginState.setUsernameError(error);
        loginViewModel.firePropertyChanged();
    }


}

