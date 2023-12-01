package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.chat.ConversationState;
import interface_adapter.chat.DialogFlowViewModel;
import interface_adapter.choose_patient.ChoosePatientState;
import interface_adapter.choose_patient.ChoosePatientViewModel;
import org.junit.Test;
import use_case.login.LoginOutputData;

import static org.junit.Assert.*;

public class LoginPresenterTest {
    private static final String USERNAME = "LoginPresenterTestUsername";
    private static final String ERROR = "LoginPresenterTestError";
    @Test
    public void doctorLoginTest() {
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        LoginViewModel loginViewModel = new LoginViewModel();
        LoginState loginState = new LoginState();
        loginState.setDoctor(true);
        loginViewModel.setState(loginState);
        DialogFlowViewModel dialogFlowViewModel = new DialogFlowViewModel();
        ChoosePatientViewModel choosePatientViewModel = new ChoosePatientViewModel();

        LoginPresenter loginPresenter = new LoginPresenter(viewManagerModel,
                dialogFlowViewModel,
                choosePatientViewModel,
                loginViewModel);
        LoginOutputData outputData = new LoginOutputData(USERNAME, false);
        loginPresenter.prepareSuccessView(outputData);

        assertEquals(choosePatientViewModel.getViewName(), viewManagerModel.getActiveView());
        assertEquals(USERNAME, choosePatientViewModel.getState().getUsername());
        assertNull(loginViewModel.getState().getError());
    }

    @Test
    public void patientLoginTest() {
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        LoginViewModel loginViewModel = new LoginViewModel();
        LoginState loginState = new LoginState();
        loginState.setDoctor(false);
        loginViewModel.setState(loginState);
        DialogFlowViewModel dialogFlowViewModel = new DialogFlowViewModel();
        ChoosePatientViewModel choosePatientViewModel = new ChoosePatientViewModel();

        LoginPresenter loginPresenter = new LoginPresenter(viewManagerModel,
                dialogFlowViewModel,
                choosePatientViewModel,
                loginViewModel);
        LoginOutputData outputData = new LoginOutputData(USERNAME, false);
        loginPresenter.prepareSuccessView(outputData);

        assertEquals(dialogFlowViewModel.getViewName(), viewManagerModel.getActiveView());
        assertNull(loginViewModel.getState().getError());
    }

    @Test
    public void failLoginTest() {
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        LoginViewModel loginViewModel = new LoginViewModel();
        DialogFlowViewModel dialogFlowViewModel = new DialogFlowViewModel();
        ConversationState conversationState = dialogFlowViewModel.getState();
        ChoosePatientViewModel choosePatientViewModel = new ChoosePatientViewModel();
        ChoosePatientState choosePatientState = choosePatientViewModel.getState();
        viewManagerModel.setActiveView(choosePatientViewModel.getViewName());

        LoginPresenter loginPresenter = new LoginPresenter(viewManagerModel,
                dialogFlowViewModel,
                choosePatientViewModel,
                loginViewModel);
        loginPresenter.prepareFailView(ERROR);

        assertEquals(choosePatientViewModel.getViewName(), viewManagerModel.getActiveView());
        assertEquals(ERROR, loginViewModel.getState().getError());
        assertEquals(conversationState, dialogFlowViewModel.getState());
        assertEquals(choosePatientState, choosePatientViewModel.getState());
    }
}