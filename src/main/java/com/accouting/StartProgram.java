package com.accouting;


import com.accouting.controllers.LoginPageController;
import com.accouting.model.SystemRoot;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Hello world!
 *
 */
public class StartProgram extends Application
{
    SystemRoot systemRoot = new SystemRoot("System");

    @Override
    public void start(Stage primaryStage) throws Exception{

        URL resourceUrl = StartProgram.class.getResource("/View/LoginPage.fxml");

        FXMLLoader loader = new FXMLLoader(resourceUrl);


        Parent root = loader.load();

        LoginPageController loginPageController = loader.getController();
        loginPageController.setSystemRoot(systemRoot);

        primaryStage.setTitle("Accounting system");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main( String[] args )
    {
        launch(args);
    }
}
