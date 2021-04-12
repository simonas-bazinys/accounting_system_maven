package com.Controllers;

import com.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class RegisterPageController {

    public void createUser() throws IOException{


        Parent root = FXMLLoader.load(getClass().getResource("../Views/ContentPage.fxml"));

        Main.primaryStage.setScene(new Scene(root, 1000, 600));

    }
    public void toggleIndividualUser(){

    }
    public void toggleLegalUser(){

    }


}
