package use_case.signup;

import entity.people.*;

public class SignupInteractor implements SignupInputBoundary {
    final SignupUserDataAccessInterface userDataAccessObject;
    final SignupOutputBoundary signupPresenter;
    final DoctorUserFactory doctorUserFactory;
    final PatientUserFactory patientUserFactory;

    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary,
                            DoctorUserFactory doctorUserFactory,
                            PatientUserFactory patientUserFactory) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.signupPresenter = signupOutputBoundary;
        this.doctorUserFactory = doctorUserFactory;
        this.patientUserFactory = patientUserFactory;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        String username = signupInputData.getUsername();
        String password = signupInputData.getPassword();
        String repeatedPassword = signupInputData.getRepeatPassword();
        boolean isDoctor = signupInputData.isDoctor();
        try {
            if (userDataAccessObject.existsByName(isDoctor, username)) {
                signupPresenter.prepareFailView("User already exists.");
            } else if (!password.equals(repeatedPassword)) {
                signupPresenter.prepareFailView("Passwords don't match.");
            } else {
                if (isDoctor) {
                    IDoctor doctor = doctorUserFactory.create(username, password);
                    userDataAccessObject.save(doctor);
                } else {
                    IPatient patient = patientUserFactory.create(username, password);
                    userDataAccessObject.save(patient);
                }
                SignupOutputData signupOutputData = new SignupOutputData(username, false);
                signupPresenter.prepareSuccessView(signupOutputData);
            }
        } catch (Exception e) {
            signupPresenter.prepareFailView(e.getMessage());
        }
    }
}
