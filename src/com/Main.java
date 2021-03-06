package com;

import com.Controllers.LoginPageController;
import com.Models.FinanceSystem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage = new Stage();

    @Override
    public void start(Stage stage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("Views/LoginScreen.fxml"));

      //  LoginPageController loginPageController = root.getController();

        primaryStage.setTitle("PST-Enterprise");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        FinanceSystem financeSystem = FinanceSystem.getInstance(); //taip pasidarai singleton class

    }
}
