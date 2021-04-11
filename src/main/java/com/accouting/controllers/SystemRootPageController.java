package com.accouting.controllers;


import com.accouting.hibernate.CategoryHibernate;
import com.accouting.hibernate.UserHibernate;
import com.accouting.model.*;
import com.accouting.utilities.AlertBox;
import com.accouting.utilities.CategoryUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class SystemRootPageController implements Initializable {

    @FXML
    public Button manageUsersBtn;
    @FXML public Button showSystemInfoBtn;
    @FXML public ListView rootCategoriesList;
    @FXML public MenuItem addRootCategoryBtn;
    @FXML public MenuItem deleteRootCategoryBtn;
    @FXML public Label userLabel;
    @FXML public Button accessSelectedRootCategoryBtn;
    @FXML public ListView allUsersList;
    @FXML public Label systemCreatedLabel;
    @FXML public Label systemNameLabel;
    @FXML public Label currentUserLabel;
    @FXML public Button manageUserBtn;
    public Button userAddBtn;
    public Button userRemoveBtn;
    public Button logOutBtn;

    private SystemRoot systemRoot;
    private User user;

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("AccountingSystem_GUI_DB");
    private UserHibernate userHibernate = new UserHibernate(factory);
    private CategoryHibernate categoryHibernate = new CategoryHibernate(factory);

    public void setSystemRoot(SystemRoot systemRoot, User user) {
        this.systemRoot = systemRoot;
        setUser(user);

        systemRoot.setRootCategories(categoryHibernate.getRootCategories());

        refreshUsersList();
        refreshRootCategoriesListView();

        populateLabels(systemRoot, user);
    }

    private void populateLabels(SystemRoot systemRoot, User user) {
        systemNameLabel.setText(systemRoot.getCompanyName());
        currentUserLabel.setText("Current user: " + user.getName());
    }

    private void refreshUsersList() {
        if(allUsersList.getItems().size() > 0) allUsersList.getItems().clear();
        for (User user : userHibernate.getUsersList())
        {
            allUsersList.getItems().add(user.getUserID() + ": " + user.getUsername());
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        userLabel.setText("User: " + user.getName());
    }


    private User getSelectedUser() {

        Object selectedUser = allUsersList.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {

            String[] userData = selectedUser.toString().split(": ");

            User userToShow = systemRoot.getUsers().stream().filter(user -> user.getUserID() == Integer.parseInt(userData[0])).findFirst().orElse(null);

            return userToShow;
        }
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loadUserManagementForm(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserManagementForm.fxml"));

        Parent root = loader.load();

        UserManagementFormController userManagementFormController = loader.getController();

        User userToEdit = getSelectedUser();
        if (userToEdit != null)
        {
            userManagementFormController.setUserBeingEdited(userToEdit);
            userManagementFormController.setCurrentUser(user);
            userManagementFormController.setSystemRoot(systemRoot);

            Stage stage = (Stage) manageUserBtn.getScene().getWindow();

            stage.setTitle("Accounting system");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public void loadSystemInformation(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("System information");
        alert.setHeaderText(null);
        alert.setContentText(systemRoot.toString());
        alert.showAndWait();
    }

    public void loadRootCategoryAddDialog(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add root category");
        dialog.setHeaderText(null);
        dialog.setContentText("Root category name: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && result.get() != "")
        {
            Category categoryToAdd = new Category(result.get(), new ArrayList<User>(), new ArrayList<Category>(), null, 0, new ArrayList<Receivable>(), new ArrayList<Payment>());
            categoryHibernate.create(categoryToAdd);
            systemRoot.setRootCategories(categoryHibernate.getRootCategories());
            refreshRootCategoriesListView();
        }
    }

    public void refreshRootCategoriesListView()
    {
        if (rootCategoriesList.getItems().size() > 0) rootCategoriesList.getItems().clear();
        for (Category category : categoryHibernate.getRootCategories())
        {
            rootCategoriesList.getItems().add(category.getCategoryID() + ": " + category.getCategoryName());
        }
    }

    public void loadRootCategoryDeleteDialog(ActionEvent actionEvent) throws Exception {

        if (systemRoot.getRootCategories().size()>0) {
            ChoiceDialog<Category> dialog = new ChoiceDialog<Category>(systemRoot.getRootCategories().get(0), systemRoot.getRootCategories());
            dialog.setTitle("Delete root category");
            dialog.setHeaderText(null);
            dialog.setContentText("Choose root category: ");

            Optional<Category> result = dialog.showAndWait();
            if (result.isPresent()) {
                categoryHibernate.remove(result.get().getCategoryID());
                for (int i = 0; i <= rootCategoriesList.getItems().size(); i++) {
                    if (rootCategoriesList.getItems().get(i).toString().startsWith(String.valueOf(result.get().getCategoryID()))) {
                        rootCategoriesList.getItems().remove(i);
                        break;
                    }
                }
                systemRoot.setRootCategories(categoryHibernate.getRootCategories());
                refreshRootCategoriesListView();
            }
        }
    }

    public void accessSelectedRootCategory(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CategoryManagementForm.fxml"));

        Parent root = loader.load();
        CategoryManagementFormController categoryManagementFormController = loader.getController();

        int selectedCategoryId = CategoryUtils.getSelectedCategoryId(rootCategoriesList.getSelectionModel().getSelectedItem());

        Category categoryToAccess = categoryHibernate.getCategoryById(selectedCategoryId);

        if (categoryToAccess != null) {
            categoryManagementFormController.setCurrentCategory(categoryToAccess, user);
            categoryManagementFormController.setSystemRoot(systemRoot);

            categoryManagementFormController.setUser(user);


            Stage stage = (Stage) accessSelectedRootCategoryBtn.getScene().getWindow();

            stage.setTitle("Accounting system");
            stage.setScene(new Scene(root));
            stage.show();
        }
        else {
            Stage stage = (Stage) accessSelectedRootCategoryBtn.getScene().getWindow();
            AlertBox alertBox = new AlertBox();
            alertBox.displayError(stage, "Category not selected", "Please select a category you would like to access");
        }
    }

    public void removeUser(ActionEvent actionEvent) {


        if (allUsersList.getItems().size() > 0){

            User selectedUser = getSelectedUser();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                for (int i=0;i<systemRoot.getUsers().size();i++)
                {
                    if (systemRoot.getUsers().get(i).getUserID() == selectedUser.getUserID())
                    {
                        userHibernate.remove(systemRoot.getUsers().get(i).getUserID());
                        systemRoot.getUsers().remove(i);
                        allUsersList.getItems().remove(i);
                        break;
                    }
                }
            }
        }
    }
}
