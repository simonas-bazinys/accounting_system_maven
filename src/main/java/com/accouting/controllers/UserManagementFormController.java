package com.accouting.controllers;


import com.accouting.hibernate.CategoryHibernate;
import com.accouting.hibernate.UserHibernate;
import com.accouting.model.Category;
import com.accouting.model.SystemRoot;
import com.accouting.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserManagementFormController implements Initializable {

    public Label userLabel;
    public TextField nameTextField;
    public Button nameEditBtn;
    public TextField surnameTextField;
    public Button surnameEditBtn;
    public TextField emailTextField;
    public Button emailEditBtn;
    public TextField usernameTextField;
    public Button usernameEditBtn;
    public TextField passwordTextField;
    public Button passwordEditBtn;
    public TextField addressTextField;
    public Button addressEditBtn;
    public TextField companyCodeTextField;
    public Button companyCodeEditBtn;
    public Button nameConfirmBtn;
    public Button surnameConfirmBtn;
    public Button emailConfirmBtn;
    public Button usernameConfirmBtn;
    public Button passwordConfirmBtn;
    public Button addressConfirmBtn;
    public Button companyCodeConfirmBtn;
    public TextField phoneTextField;
    public Button phoneEditBtn;
    public Button phoneConfirmBtn;
    public Button goBackBtn;
    public ListView categoriesListView;
    public Button addCategoryResponsibleBtn;
    public Button removeCategoryResponsibleBtn;


    private User currentUser;
    private User userBeingEdited;
    private SystemRoot systemRoot;

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("AccountingSystem_GUI_DB");
    private UserHibernate userHibernate = new UserHibernate(factory);

    private CategoryHibernate categoryHibernate = new CategoryHibernate(factory);




    public User getUserBeingEdited() {
        return userBeingEdited;
    }

    public void setUserBeingEdited(User userBeingEdited) {
        this.userBeingEdited = userBeingEdited;

        nameTextField.setText(userBeingEdited.getName());
        nameTextField.setEditable(false);
        nameConfirmBtn.setDisable(true);

        emailTextField.setText(userBeingEdited.getEmail());
        emailTextField.setEditable(false);
        emailConfirmBtn.setDisable(true);

        phoneTextField.setText(userBeingEdited.getPhone());
        phoneTextField.setEditable(false);
        phoneConfirmBtn.setDisable(true);

        usernameTextField.setText(userBeingEdited.getUsername());
        usernameTextField.setEditable(false);
        usernameConfirmBtn.setDisable(true);

        passwordTextField.setText(userBeingEdited.getPassword());
        passwordTextField.setEditable(false);
        passwordConfirmBtn.setDisable(true);

        if (userBeingEdited.getUserType().equals("Individual"))
        {
            companyCodeTextField.setText("User is individual");
            companyCodeTextField.setEditable(false);
            companyCodeEditBtn.setDisable(true);
            companyCodeConfirmBtn.setDisable(true);

            addressTextField.setText("User is individual");
            addressTextField.setEditable(false);
            addressEditBtn.setDisable(true);
            addressConfirmBtn.setDisable(true);

            surnameTextField.setText(userBeingEdited.getSurname());
            surnameTextField.setEditable(false);
            surnameConfirmBtn.setDisable(true);
        }
        else
        {
            surnameTextField.setText("User is legal");
            surnameTextField.setEditable(false);
            surnameTextField.setDisable(true);
            surnameEditBtn.setDisable(true);
            surnameConfirmBtn.setDisable(true);


            companyCodeTextField.setText(userBeingEdited.getCompanyCode());
            companyCodeTextField.setEditable(false);
            companyCodeTextField.setDisable(false);
            companyCodeConfirmBtn.setDisable(false);

            addressTextField.setText(userBeingEdited.getAddress());
            addressTextField.setEditable(false);
            addressTextField.setDisable(false);
            addressConfirmBtn.setDisable(false);
        }
    }



    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        userLabel.setText("User: " + user.getName());

        refreshCategoriesResponsibleListView();
    }

    public void setSystemRoot(SystemRoot systemRoot) {
        this.systemRoot = systemRoot;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    private void loadMainWindow(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/SystemRootPage.fxml"));

        Parent root = loader.load();

        SystemRootPageController systemRootPageController = loader.getController();
        systemRootPageController.setSystemRoot(systemRoot, user);

        Stage stage = (Stage) goBackBtn.getScene().getWindow();

        stage.setTitle("Accounting system");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void goBackToRoot(ActionEvent actionEvent) throws IOException {
        loadMainWindow(currentUser);
    }

    public void editName(ActionEvent actionEvent) {
        nameTextField.setEditable(true);
        nameConfirmBtn.setDisable(false);
    }

    public void editSurname(ActionEvent actionEvent) {
        surnameTextField.setEditable(true);
        surnameConfirmBtn.setDisable(false);
    }

    public void editEmail(ActionEvent actionEvent) {
        emailTextField.setEditable(true);
        emailConfirmBtn.setDisable(false);
    }

    public void editUsername(ActionEvent actionEvent) {
        usernameTextField.setEditable(true);
        usernameConfirmBtn.setDisable(false);
    }

    public void editPassword(ActionEvent actionEvent) {
        passwordTextField.setEditable(true);
        passwordConfirmBtn.setDisable(false);
    }

    public void editAddress(ActionEvent actionEvent) {
        addressTextField.setEditable(true);
        addressConfirmBtn.setDisable(false);
    }

    public void editCompanyCode(ActionEvent actionEvent) {
        companyCodeTextField.setEditable(true);
        companyCodeConfirmBtn.setDisable(false);
    }

    public void confirmName(ActionEvent actionEvent) {
        userBeingEdited.setName(nameTextField.getText());
        nameTextField.setEditable(false);
        nameConfirmBtn.setDisable(true);

        userHibernate.edit(userBeingEdited);
    }

    public void confirmSurname(ActionEvent actionEvent) {
        userBeingEdited.setSurname(surnameTextField.getText());
        surnameTextField.setEditable(false);
        surnameConfirmBtn.setDisable(true);

        userHibernate.edit(userBeingEdited);
    }

    public void confirmEmail(ActionEvent actionEvent) {
        userBeingEdited.setEmail(emailTextField.getText());
        emailTextField.setEditable(false);
        emailConfirmBtn.setDisable(true);

        userHibernate.edit(userBeingEdited);
    }

    public void confirmUsername(ActionEvent actionEvent) {
        userBeingEdited.setUsername(usernameTextField.getText());
        usernameTextField.setEditable(false);
        usernameConfirmBtn.setDisable(true);

        userHibernate.edit(userBeingEdited);
    }

    public void confirmPassword(ActionEvent actionEvent) {
        userBeingEdited.setPassword(passwordConfirmBtn.getText());
        passwordTextField.setEditable(false);
        passwordConfirmBtn.setDisable(true);

        userHibernate.edit(userBeingEdited);
    }

    public void confirmAddress(ActionEvent actionEvent) {
        userBeingEdited.setAddress(addressTextField.getText());
        addressTextField.setEditable(false);
        addressConfirmBtn.setDisable(true);

        userHibernate.edit(userBeingEdited);
    }

    public void confirmCompanyCode(ActionEvent actionEvent) {
        userBeingEdited.setCompanyCode(companyCodeTextField.getText());
        companyCodeTextField.setEditable(false);
        companyCodeConfirmBtn.setDisable(true);

        userHibernate.edit(userBeingEdited);
    }

    public void editPhone(ActionEvent actionEvent) {
        phoneTextField.setEditable(true);
        phoneConfirmBtn.setDisable(false);

        userHibernate.edit(userBeingEdited);
    }

    public void confirmPhone(ActionEvent actionEvent) {
        userBeingEdited.setPhone(phoneTextField.getText());
        phoneTextField.setEditable(false);
        phoneConfirmBtn.setDisable(true);

        userHibernate.edit(userBeingEdited);
    }


    public void addCategoryResponsible(ActionEvent actionEvent) throws Exception {
        ArrayList<Category> categories = (ArrayList<Category>) categoryHibernate.getCategoryList();

        ChoiceDialog<Category> dialog = new ChoiceDialog<Category>(categories.get(0), categories);
        dialog.setTitle("Add category responsible");
        dialog.setHeaderText(null);
        dialog.setContentText("Choose category: ");

        Optional<Category> result = dialog.showAndWait();
        if (result.isPresent()) {
            userHibernate.addCategoryResponsible(result.get().getCategoryID(), userBeingEdited.getUserID());
            refreshCategoriesResponsibleListView();
        }
    }

    private void refreshCategoriesResponsibleListView() {
        if(categoriesListView.getItems().size() > 0) categoriesListView.getItems().clear();
        for (Category category : userHibernate.getCategoriesResponsible(userBeingEdited))
        {
            categoriesListView.getItems().add(category.getCategoryID() + ": " + category.getCategoryName());
        }
    }

    public void removeCategoryResponsible(ActionEvent actionEvent) throws Exception {
        List<Category> categories = userHibernate.getCategoriesResponsible(userBeingEdited);

        ChoiceDialog<Category> dialog = new ChoiceDialog<Category>(categories.get(0), categories);
        dialog.setTitle("Remove category responsible");
        dialog.setHeaderText(null);
        dialog.setContentText("Choose category: ");

        Optional<Category> result = dialog.showAndWait();
        if (result.isPresent()) {
            userHibernate.removeCategoryResponsible(result.get().getCategoryID(), userBeingEdited.getUserID());
            refreshCategoriesResponsibleListView();
        }
    }
}
