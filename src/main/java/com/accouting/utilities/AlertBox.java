package com.accouting.utilities;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    @FXML
    public void displayError(Stage stage, String title, String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.getDialogPane().setContentText(message);
        alert.getDialogPane().setHeaderText(title);
        alert.showAndWait();
    }
}
