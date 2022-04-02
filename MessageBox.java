package com.example.projectz;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MessageBox {

    public static void showMessage(String title, String message){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        AnchorPane root = new AnchorPane();

        Button close = new Button("Close");
        close.setPrefWidth(100);
        close.setPrefHeight(34);
        close.setOnAction((e)->window.close());
        AnchorPane.setTopAnchor(close, 87.0);
        AnchorPane.setLeftAnchor(close, 187.0);
        AnchorPane.setRightAnchor(close, 214.0);
        AnchorPane.setBottomAnchor(close, 14.0);

        Label label = new Label();
        label.setText(message);
        label.setFont(Font.font(15.0));
        AnchorPane.setTopAnchor(label, 33.0);
        AnchorPane.setLeftAnchor(label, 83.0);
        AnchorPane.setRightAnchor(label, 60.0);
        AnchorPane.setBottomAnchor(label, 75.0);

        root.getChildren().addAll(close, label);

        Scene scene = new Scene(root, 501, 131, true);

        window.setScene(scene);
        window.setTitle(title);
        window.setResizable(false);
        window.showAndWait();
    }
}
