package com.accouting.controllers;



import com.accouting.hibernate.CategoryHibernate;
import com.accouting.hibernate.PaymentHibernate;
import com.accouting.hibernate.ReceivableHibernate;
import com.accouting.hibernate.UserHibernate;
import com.accouting.model.*;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.accouting.utilities.CategoryUtils.getSelectedCategoryId;

public class CategoryManagementFormController implements Initializable {

    @FXML
    public Button accessSelectedCategoryBtn;
    @FXML public ListView categoryList;
    @FXML public Label userLabel;
    @FXML public Label currentUserLabel;
    @FXML public ListView responsibleUsersList;
    @FXML public Label auditDetailsLabel;
    @FXML public ListView incomeList;
    @FXML public ListView expenseList;
    @FXML public Label incomeHistoryLabel;
    @FXML public Label expensesHistoryLabel;
    @FXML public Button sellBtn;
    @FXML public Button buyBtn;
    @FXML public MenuItem addCategoryBtn;
    @FXML public MenuItem deleteCategoryBtn;
    @FXML public Button backToParentBtn;
    @FXML public Label categoryNameLabel;
    @FXML public Button addResponsibleUserBtn;
    @FXML public Button removeResponsibleUser;

    private User user;
    private Category currentCategory;
    private SystemRoot systemRoot;

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("AccountingSystem_GUI_DB");
    private CategoryHibernate categoryHibernate = new CategoryHibernate(factory);
    private PaymentHibernate paymentHibernate = new PaymentHibernate(factory);
    private ReceivableHibernate receivableHibernate = new ReceivableHibernate(factory);
    private UserHibernate userHibernate = new UserHibernate(factory);

    public SystemRoot getSystemRoot() {
        return systemRoot;
    }

    public void setSystemRoot(SystemRoot systemRoot) {
        this.systemRoot = systemRoot;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        userLabel.setText("User: " + user.getName());
    }

    public Category getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(Category currentCategory, User user) {
        this.currentCategory = currentCategory;
        currentUserLabel.setText(user.getName());

        refreshIncomeHistoryList();
        refreshExpenseHistoryList();
        refreshCategoriesListView();
        refreshResponsibleUsersListView();

        categoryNameLabel.setText(currentCategory.getCategoryName());
    }

    private void refreshExpenseHistoryList() {
        if (expenseList.getItems().size() > 0) expenseList.getItems().clear();
        for (Payment payment : paymentHibernate.getPaymentsOfCategory(currentCategory))
        {
            expenseList.getItems().add(payment.getPaymentDate() + " : " + payment.getPaymentSum());
        }
    }

    private void refreshIncomeHistoryList() {
        if (incomeList.getItems().size() > 0) incomeList.getItems().clear();
        for(Receivable receivable : receivableHibernate.getReceivablesOfCategory(currentCategory))
        {
            incomeList.getItems().add(receivable.getReceivableDate() + " : " + receivable.getReceivableSum());
        }
    }


    public void refreshCategoriesListView()
    {
        if (categoryList.getItems().size() > 0) categoryList.getItems().clear();
        for (Category category : categoryHibernate.getCategoriesOfParent(currentCategory))
        {
            categoryList.getItems().add(category.getCategoryID() + ": " + category.getCategoryName());
        }
    }

    public void refreshResponsibleUsersListView()
    {
        if(responsibleUsersList.getItems().size() > 0) responsibleUsersList.getItems().clear();
        for (User user : categoryHibernate.getResponsibleUsersOfCategory(currentCategory))
        {
            responsibleUsersList.getItems().add(user.getUserID() + ": " + user.getUsername());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void accessSelectedCategory(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CategoryManagementForm.fxml"));

        Parent root = loader.load();
        CategoryManagementFormController categoryManagementFormController = loader.getController();

        int selectedCategoryId = getSelectedCategoryId(categoryList.getSelectionModel().getSelectedItem());

        Category categoryToAccess = categoryHibernate.getCategoryById(selectedCategoryId);

        categoryManagementFormController.setCurrentCategory(categoryToAccess, user);

        categoryManagementFormController.setUser(user);

        categoryManagementFormController.setSystemRoot(systemRoot);


        Stage stage = (Stage) accessSelectedCategoryBtn.getScene().getWindow();

        stage.setTitle("Accounting system");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void SellSomething(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Income");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter price: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && result.get() != "")
        {
            Receivable receivableBeingReceived =  new Receivable(Float.parseFloat(result.get()), Date.valueOf(LocalDate.now()), currentCategory);

            categoryHibernate.AddIncome(currentCategory, receivableBeingReceived);

            refreshIncomeHistoryList();
        }
    }

    public void buySomething(ActionEvent actionEvent) throws Exception {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Payment");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter price: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && result.get() != "")
        {
            Payment paymentBeingMade = new Payment(Float.parseFloat(result.get()),Date.valueOf(LocalDate.now()),currentCategory);

            currentCategory.getExpense().add(paymentBeingMade);//TODO make proper validation in case for not numbers

            categoryHibernate.AddExpense(currentCategory, paymentBeingMade);

            refreshExpenseHistoryList();
        }
    }

    public void loadCategoryAddDialog(ActionEvent actionEvent) throws Exception {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add  category");
        dialog.setHeaderText(null);
        dialog.setContentText("Category name: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().equals(""))
        {
            Category categoryBeingAdded =  new Category(result.get(), new ArrayList<User>(), new ArrayList<Category>(), currentCategory, 0, new ArrayList<Receivable>(), new ArrayList<Payment>());
            categoryHibernate.AddSubCategory(categoryBeingAdded, categoryBeingAdded);

            refreshCategoriesListView();
        }
    }

    //TODO delete this method
    public void deleteCategory(ActionEvent actionEvent) {
        List<Category> categories = categoryHibernate.getCategoriesOfParent(currentCategory);

        ChoiceDialog<Category> dialog = new ChoiceDialog<Category>(categories.get(0), categories);
        dialog.setTitle("Add responsible user");
        dialog.setHeaderText(null);
        dialog.setContentText("Choose user: ");

        Optional<Category> result = dialog.showAndWait();
        if (result.isPresent()) {
            categoryHibernate.remove(currentCategory.getCategoryID());
            refreshCategoriesListView();
        }
    }

    public void loadCategoryDeleteDialog(ActionEvent actionEvent) throws Exception {
            List<Category> categories = categoryHibernate.getCategoriesOfParent(currentCategory);

            ChoiceDialog<Category> dialog = new ChoiceDialog<Category>(categories.get(0), categories);
            dialog.setTitle("Delete category");
            dialog.setHeaderText(null);
            dialog.setContentText("Choose category: ");

            Optional<Category> result = dialog.showAndWait();
            if (result.isPresent()) {
                categoryHibernate.remove(result.get().getCategoryID());
                for(int i = 0; i<= categoryList.getItems().size(); i++) {
                    if (categoryList.getItems().get(i).toString().startsWith(String.valueOf(result.get().getCategoryID()))) {
                        categoryList.getItems().remove(i);
                        break;
                    }
                }
                refreshCategoriesListView();
            }

    }

    public void goToParentCategory(ActionEvent actionEvent) throws IOException {


        if (currentCategory.getParentCategory() != null)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CategoryManagementForm.fxml"));

            Parent root = loader.load();

            CategoryManagementFormController categoryManagementFormController = loader.getController();

            categoryManagementFormController.setCurrentCategory(currentCategory.getParentCategory(), user);

            categoryManagementFormController.setUser(user);

            categoryManagementFormController.setSystemRoot(systemRoot);



            Stage stage = (Stage) accessSelectedCategoryBtn.getScene().getWindow();

            stage.setTitle("Accounting system");
            stage.setScene(new Scene(root));
            stage.show();

        }
        else
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/SystemRootPage.fxml"));

            Parent root = loader.load();

            SystemRootPageController systemRootPageController = loader.getController();
            systemRootPageController.setSystemRoot(systemRoot,user);

            Stage stage = (Stage) backToParentBtn.getScene().getWindow();

            stage.setTitle("Accounting system");
            stage.setScene(new Scene(root));
            stage.show();
        }




    }


    public void addResponsibleUser(ActionEvent actionEvent) throws Exception {

            List<User> users = userHibernate.getUsersList();

            ChoiceDialog<User> dialog = new ChoiceDialog<User>(users.get(0),users);
            dialog.setTitle("Add responsible user");
            dialog.setHeaderText(null);
            dialog.setContentText("Choose user: ");

            Optional<User> result = dialog.showAndWait();
            if (result.isPresent()) {
                categoryHibernate.AddResponsibleUser(currentCategory.getCategoryID(), result.get().getUserID());
                refreshResponsibleUsersListView();
            }
    }

    public void removeResponsibleUser(ActionEvent actionEvent) throws Exception {
        List<User> users = categoryHibernate.getResponsibleUsersOfCategory(currentCategory);

        ChoiceDialog<User> dialog = new ChoiceDialog<User>(users.get(0),users);
        dialog.setTitle("Remove responsible user");
        dialog.setHeaderText(null);
        dialog.setContentText("Choose user: ");

        Optional<User> result = dialog.showAndWait();
        if (result.isPresent()) {
            categoryHibernate.removeResponsibleUser(currentCategory.getCategoryID(), result.get().getUserID());
            refreshResponsibleUsersListView();
        }
    }
}
