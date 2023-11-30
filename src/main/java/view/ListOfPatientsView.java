package view;

import interface_adapter.swap_views.load_patients.LoadPatientsController;
import interface_adapter.choosepatient.ChoosePatientViewModel;
import interface_adapter.choosepatient.ChoosePatientController;
import interface_adapter.choosepatient.ChoosePatientState;
import interface_adapter.swap_views.update.doctor.SwapToDoctorUpdateController;
import interface_adapter.swap_views.welcome.SwapToWelcomeController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ListOfPatientsView extends JPanel {
    public String viewName;
    private final JButton modifyButton;

    public ListOfPatientsView(ChoosePatientController choosePatientController,
                              ChoosePatientViewModel choosePatientViewModel,
                              SwapToWelcomeController swapController,
                              LoadPatientsController loadPatientsController,
                              SwapToDoctorUpdateController swapToDoctorUpdateController) {
        this.viewName = choosePatientViewModel.getViewName();

        // Create and do settings for main panel
        this.setLayout(new BorderLayout());
//        this.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 20));
        this.setBackground(Color.lightGray);
        this.setPreferredSize(ChoosePatientViewModel.PANEL_DIMENSION);

        // Create the upper sub-panel that will contain the button to log out, listOfPatients label, and modify button
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        upperPanel.setBackground(Color.lightGray);
        this.add(upperPanel, BorderLayout.NORTH);

        // Create the button for logging out of the profile
        JButton logOutButton = new JButton(ChoosePatientViewModel.LOGOUT_BUTTON_LABEL);
        logOutButton.setFont(ChoosePatientViewModel.BUTTON_FONT);
        logOutButton.setFocusable(false);
        logOutButton.addActionListener(e -> swapController.execute());
        logOutButton.setPreferredSize(ChoosePatientViewModel.BUTTON_DIMENSION);
        upperPanel.add(logOutButton);

        // Create and add label of List of Patients to the upper panel
        JLabel listOfPatientsLabel = new JLabel(ChoosePatientViewModel.MAIN_LABEL);
        listOfPatientsLabel.setFont(ChoosePatientViewModel.MAIN_LABEL_FONT);
        upperPanel.add(listOfPatientsLabel);

        // Create the button for modifying the profile
        modifyButton = new JButton(ChoosePatientViewModel.MODIFY_BUTTON_LABEL);
        modifyButton.setFont(ChoosePatientViewModel.BUTTON_FONT);
        modifyButton.setFocusable(false);
        modifyButton.addActionListener(
                e -> {
                    if(e.getSource().equals(modifyButton)){
                        ChoosePatientState currentState = choosePatientViewModel.getState();
                        swapToDoctorUpdateController.execute(currentState.getUsername());
                    }
                });
        // USING WRONG BUTTON DIMENSINO RN
        modifyButton.setPreferredSize(ChoosePatientViewModel.BUTTON_DIMENSION);  //TODO NEED BUTTON DIMENSION IN CONVOVIEWMODEL
        upperPanel.add(modifyButton);

        // Create the sub-panel in the middle that contains the list of buttons linking to the patients
        JPanel midPanel = new JPanel();
        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));
        midPanel.setBackground(Color.lightGray);

        // get a list of patients
        List<String> patients = loadPatientsController.execute();
        // Add the buttons that will link doctor to the chat with the patients
        for(String p: patients) {
            JButton button = new JButton(p);
            button.setFont(ChoosePatientViewModel.BUTTON_FONT);
            button.setBackground(Color.white);
            button.setFocusable(false);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.addActionListener(e -> {
                if(e.getSource().equals(modifyButton)){
                    ChoosePatientState currentState = choosePatientViewModel.getState();
                    choosePatientController.execute(currentState.getUsername(), p);
                }
            });
            midPanel.add(button);
        }

        // Add midPanel to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(midPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane);
    }

}
