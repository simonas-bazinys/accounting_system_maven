package com.accouting.controllers;


import com.accouting.hibernate.UserHibernate;
import com.accouting.model.Category;
import com.accouting.model.SystemRoot;
import com.accouting.model.User;
import com.accouting.utilities.AlertBox;
import com.accouting.utilities.DataValidity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {


    @FXML
    public Label registerPrompt;
    @FXML public Label loginPrompt;
    @FXML public Label usernameLoginRequired;
    @FXML public Label passwordLoginRequired;
    @FXML public Label nameRegisterRequired;
    @FXML public Label usernameRegisterRequired;
    @FXML public Label passwordRegisterRequired;
    @FXML public RadioButton individualUserToggle;
    @FXML public RadioButton legalUserToggle;
    @FXML public Button loginBtn;
    @FXML public TextField nameTextFieldRegister;
    @FXML public TextField usernameTextFieldRegister;
    @FXML public PasswordField passwordFieldRegister;
    @FXML public TextField usernameTextFieldLogin;
    @FXML public PasswordField passwordFieldLogin;
    @FXML public Button registerBtn;
    @FXML public TextField addressTextFieldRegister;
    @FXML public TextField companyCodeTextFieldRegister;
    @FXML public TextField surnameTextFieldRegister;
    @FXML public ToggleGroup radioButtonsForUserTypes;
    @FXML public TextField emailTextFieldRegister;
    @FXML public Label emailLabel;
    @FXML public Label phoneLabel;
    @FXML public Label userTypeLabel;
    @FXML public TextField phoneTextFieldRegister;


    private SystemRoot systemRoot;

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("AccountingSystem_GUI_DB");
    private UserHibernate userHibernate = new UserHibernate(factory);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setSystemRoot(SystemRoot systemRoot) {
        this.systemRoot = systemRoot;

        radioButtonsForUserTypes = new ToggleGroup();

        individualUserToggle.setToggleGroup(radioButtonsForUserTypes);
        legalUserToggle.setToggleGroup(radioButtonsForUserTypes);

        individualUserToggle.setSelected(true);

        systemRoot.setUsers(userHibernate.getUsersList());
    }

    public void validateUser(ActionEvent actionEvent) throws IOException {

        System.out.println(systemRoot.getUsers());

        if (systemRoot.getUsers().size() > 0)
        {
            systemRoot.
                    getUsers().
                    forEach(
                            user -> {
                                if (user.getUsername().equals(usernameTextFieldLogin.getText()) &&
                                    user.getPassword().equals(passwordFieldLogin.getText()))
                                {
                                    try {
                                        loadMainWindow(user);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

            });
            loginPrompt.setText("Username or password is wrong");
        }
        else
        {
            loginPrompt.setText("There are no users in the system");
        }
        loginPrompt.setVisible(true);
    }

    private void loadMainWindow(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/SystemRootPage.fxml"));

        Parent root = loader.load();

        SystemRootPageController systemRootPageController = loader.getController();
        systemRootPageController.setSystemRoot(systemRoot, user);

        Stage stage = (Stage) loginBtn.getScene().getWindow();

        stage.setTitle("Accounting system");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void createUser(ActionEvent actionEvent) {

        User userBeingRegistered = null;

        hideRequiredDataStars();

        if (DataValidity.isValidEmailAddress(emailTextFieldRegister.getText())) {
            if (requiredFieldsFilled()) {
                if (individualUserToggle.isSelected()) {
                    userBeingRegistered = new User(nameTextFieldRegister.getText(),
                            surnameTextFieldRegister.getText(),
                            emailTextFieldRegister.getText(),
                            phoneTextFieldRegister.getText(),
                            null, null,
                            usernameTextFieldRegister.getText(),
                            passwordFieldRegister.getText(),
                            new ArrayList<Category>(),
                            "Individual");

                    clearTextFieldsForIndividual();
                } else if (legalUserToggle.isSelected()) {
                    System.out.println("Legal user");
                    userBeingRegistered = new User(nameTextFieldRegister.getText(),
                            null,
                            emailTextFieldRegister.getText(),
                            phoneTextFieldRegister.getText(),
                            addressTextFieldRegister.getText(),
                            companyCodeTextFieldRegister.getText(),
                            usernameTextFieldRegister.getText(),
                            passwordFieldRegister.getText(),
                            new ArrayList<Category>(),
                            "Legal");

                    clearTextFieldsLegal();
                }

                userHibernate.create(userBeingRegistered);
                systemRoot.getUsers().add(userBeingRegistered);
                registerPromtWhenRegisterSuccessful(userBeingRegistered);
            }
            else registerPromptWhenDataMissing();
        }
        else {
            AlertBox alertBox = new AlertBox();
            Stage stage = (Stage) registerBtn.getScene().getWindow();
            alertBox.displayError(stage, "Email not valid","Email is not in valid format, please enter email correctly.");
        }

    }

    private void clearTextFieldsLegal() {
        nameTextFieldRegister.clear();
        emailTextFieldRegister.clear();
        phoneTextFieldRegister.clear();
        addressTextFieldRegister.clear();
        companyCodeTextFieldRegister.clear();
        usernameTextFieldRegister.clear();
        passwordFieldRegister.clear();
    }

    private void clearTextFieldsForIndividual() {
        nameTextFieldRegister.clear();
        surnameTextFieldRegister.clear();
        emailTextFieldRegister.clear();
        phoneTextFieldRegister.clear();
        usernameTextFieldRegister.clear();
        passwordFieldRegister.clear();
    }

    private void registerPromptWhenDataMissing() {
        registerPrompt.setText("Required data is missing.");
        registerPrompt.setTextFill(Color.RED);
        registerPrompt.setVisible(true);
    }

    private void registerPromtWhenRegisterSuccessful(User userBeingRegistered) {
        registerPrompt.setText("User " + userBeingRegistered.getUsername() + " successfully signed up.");
        registerPrompt.setTextFill(Color.GREEN);
        registerPrompt.setVisible(true);
    }

    private void hideRequiredDataStars() {
        nameRegisterRequired.setVisible(false);
        usernameRegisterRequired.setVisible(false);
        passwordRegisterRequired.setVisible(false);
    }

    private boolean requiredFieldsFilled() {
        boolean isNotEmpty = true;

        if (nameTextFieldRegister.getText().equals(""))
        {
            nameRegisterRequired.setVisible(true);
            isNotEmpty = false;
        }

        if (usernameTextFieldRegister.getText().equals(""))
        {
            usernameRegisterRequired.setVisible(true);
            isNotEmpty = false;
        }

        if (passwordFieldRegister.getText().equals(""))
        {
            passwordRegisterRequired.setVisible(true);
            isNotEmpty = false;
        }

        return isNotEmpty;
    }

    public void toggleIndividualUser(ActionEvent actionEvent) {

        surnameTextFieldRegister.setText("");
        surnameTextFieldRegister.setEditable(true);
        surnameTextFieldRegister.setDisable(false);
        surnameTextFieldRegister.setOpacity(1);


        addressTextFieldRegister.setText("Only for legal user");
        companyCodeTextFieldRegister.setText("Only for legal user");

        addressTextFieldRegister.setEditable(false);
        companyCodeTextFieldRegister.setEditable(false);

        addressTextFieldRegister.setDisable(true);
        companyCodeTextFieldRegister.setDisable(true);

        addressTextFieldRegister.setOpacity(0.3);
        companyCodeTextFieldRegister.setOpacity(0.3);
    }

    public void toggleLegalUser(ActionEvent actionEvent)
    {
        surnameTextFieldRegister.setText("Only for individual user");
        surnameTextFieldRegister.setEditable(false);
        surnameTextFieldRegister.setDisable(true);
        surnameTextFieldRegister.setOpacity(0.3);


        addressTextFieldRegister.setText("");
        companyCodeTextFieldRegister.setText("");

        addressTextFieldRegister.setEditable(true);
        companyCodeTextFieldRegister.setEditable(true);

        addressTextFieldRegister.setDisable(false);
        companyCodeTextFieldRegister.setDisable(false);

        addressTextFieldRegister.setOpacity(1);
        companyCodeTextFieldRegister.setOpacity(1);
    }
}
