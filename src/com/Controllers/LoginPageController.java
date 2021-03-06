package com.Controllers;


import com.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import com.Models.FinanceSystem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginPageController {

    @FXML public TextField usernameTextFieldLogin;

    public void validateUser() throws IOException {
        System.out.println(usernameTextFieldLogin.getText());

        Parent root = FXMLLoader.load(getClass().getResource("../Views/RegisterScreen.fxml"));
        Main.primaryStage.setScene(new Scene(root, 1000, 600));

    }
    //public void validateUser(ActionEvent actionEvent) {
    //
    //}
}
