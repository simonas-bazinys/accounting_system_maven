package com.Controllers;

import com.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



import java.io.IOException;

public class ContentController {

    @FXML
    public Button but;

    @FXML
    public Accordion contentAccordion;

    @FXML
    public void initialize() {
        but.setText("asdasd");
        Accordion accordion = new Accordion();
        VBox vbox1 = new VBox();

        VBox someContent = new VBox();
        TitledPane pane2 = new TitledPane("Ships", vbox1);
        someContent.getChildren().add(pane2);
       // someContent.
        TitledPane pane1 = new TitledPane("Boats" , someContent);
        System.out.println(pane1.getContent());

        accordion.getPanes().add(pane1);

        contentAccordion.getPanes().add(pane1);


        Label label = new Label();



        // Create ContextMenu
        ContextMenu contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("Menu Item 1");
        item1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                label.setText("Select Menu Item 1");
            }
        });
        MenuItem item2 = new MenuItem("Menu Item 2");
        item2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                label.setText("Select Menu Item 2");
                VBox vbox2 = new VBox();
                TitledPane pane2 = new TitledPane("Small Boats", vbox2);
                vbox1.getChildren().add(pane2);
                vbox1.getChildren().add(new Label("expense: 25 25 25 25"));
            }
        });

        // Add MenuItem to ContextMenu
        contextMenu.getItems().addAll(item1, item2);

        // When user right-click on Circle
        pane2.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {

                contextMenu.show(contentAccordion, event.getScreenX(), event.getScreenY());
            }
        });


    }







            public void doshit() throws IOException{
                but.setText("asdasd");
                TitledPane pane1 = new TitledPane("Boats" , new Label("Show all boats available"));
                contentAccordion.getPanes().add(pane1);
                //but.setText("bled");



            }

 /*   @FXML
    protected static void initialize() {
        Accordion accordion = new Accordion();

        TitledPane pane1 = new TitledPane("Boats" , new Label("Show all boats available"));
        TitledPane pane2 = new TitledPane("Cars"  , new Label("Show all cars available"));
        TitledPane pane3 = new TitledPane("Planes", new Label("Show all planes available"));

        contentAccordion.getPanes().add(pane1);
        accordion.getPanes().add(pane2);
        accordion.getPanes().add(pane3);
        //mainPane.getChildren().removeAll();

    }*/
            public static void startPage() throws IOException{









               // refreshPage();

            }

    public static void refreshPage() throws IOException{



        //Main.root = FXMLLoader.load(getClass().getResource("../Views/ContentPage.fxml"));

        //Main.primaryStage.setScene(new Scene(Main.root, 1000, 600));

    }




}
