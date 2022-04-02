package com.example.projectz;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TextToSpeech extends Application{

    public static void main(String...args){ launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception{

        AnchorPane root = new AnchorPane();

        Button addText = new Button("Open File");
        Tooltip txtFile = new Tooltip("File should be a .txt file");
        addText.setTooltip(txtFile);
        addText.setFont(Font.font(15.0));
        addText.setTextFill(Color.DARKGREEN);
        addText.setAlignment(Pos.TOP_LEFT);
        AnchorPane.setTopAnchor(addText, 10.0);
        AnchorPane.setLeftAnchor(addText, 100.0);

        Button read_aloud = new Button("Read Aloud");
        Tooltip t_read = new Tooltip("Read text aloud");
        read_aloud.setTooltip(t_read);
        read_aloud.setFont(Font.font(15.0));
        read_aloud.setTextFill(Color.DODGERBLUE);
        AnchorPane.setTopAnchor(read_aloud, 10.0);
        AnchorPane.setLeftAnchor(read_aloud, 200.0);

        Button props = new Button("Properties");
        Tooltip t_Prop = new Tooltip("Edit sound properties");
        props.setTooltip(t_Prop);
        props.setFont(Font.font(15.0));
        props.setTextFill(Color.DARKSALMON);
        props.setOnAction(e->setProperties());
        AnchorPane.setTopAnchor(props, 10.0);
        AnchorPane.setRightAnchor(props, 200.0);

        Button close = new Button("Close");
        Tooltip t_Close = new Tooltip("Close window");
        close.setTooltip(t_Close);
        close.setFont(Font.font(15.0));
        close.setTextFill(Color.RED);
        close.setAlignment(Pos.TOP_RIGHT);
        AnchorPane.setTopAnchor(close, 10.0);
        AnchorPane.setRightAnchor(close, 100.0);
        close.setOnAction(e->primaryStage.close());

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setPrefWidth(751);
        textArea.setPrefHeight(484);
        textArea.setMinWidth(571);
        textArea.setMinHeight(479);

        textArea.setFont(Font.font(16.0));


        addText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open A .txt File To Read Aloud");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("txt Files Only", "*.txt")
                );
                File file = fileChooser.showOpenDialog(primaryStage);
                Scanner scanner;
                String fileContent = "";
                try{
                    scanner = new Scanner(file);
                    while (scanner.hasNextLine()){
                        fileContent += scanner.nextLine()+"\n";
                    }
                    scanner.close();
                    textArea.setText(fileContent);
                }catch (IOException e){
                    System.err.println("Error reading file "+e);
                    MessageBox.showMessage("Error Occurred", "Error while parsing file, try again later");
                }
            }
        });

        read_aloud.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String text_Speak = textArea.getText();
                if (text_Speak.length() > 1){
                    SpeechController.SpeakAloud(text_Speak);
                }else {
                    MessageBox.showMessage("Information", "Cannot read empty field! Add file with Open file Btn!");
                    System.out.println("No text Found!");
                }
            }
        });

        AnchorPane.setTopAnchor(textArea,50.0);
        AnchorPane.setLeftAnchor(textArea, 100.0);
        AnchorPane.setRightAnchor(textArea, 100.0);
        AnchorPane.setBottomAnchor(textArea, 20.0);

        root.getChildren().addAll(addText,read_aloud, props,close, textArea);

        Scene scene = new Scene(root,771,549, true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Text to Speech Reader");
        primaryStage.show();
    }

    public void setProperties(){
        Stage window = new Stage();

        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        SpeechController speech = new SpeechController();

        AnchorPane root = new AnchorPane();

        GridPane grid = new GridPane();
        grid.setVgap(20);

        Label Rate = new Label("Rate");
        Rate.setFont(Font.font(18.0));
        ComboBox<Float> rate = new ComboBox<>();
        rate.setPrefHeight(43);
        rate.setPrefWidth(132);
        rate.setValue(speech.getRate());
        ObservableList<Float> data = FXCollections.observableArrayList();
        data.addAll(50F,100F,120F,130F,150F,200F,220F,250F,300F);
        rate.itemsProperty().setValue(data);

        Label Pitch = new Label("Pitch");
        Pitch.setFont(Font.font(18.0));
        ComboBox<Float> pitch = new ComboBox<>();
        pitch.setPrefHeight(43);
        pitch.setPrefWidth(132);
        pitch.setValue(speech.getPitch());
        ObservableList<Float> pitches = FXCollections.observableArrayList();
        pitches.addAll(50F,100F,120F,130F,150F,200F,220F,250F,300F);
        pitch.itemsProperty().setValue(pitches);

        Label volume = new Label("Volume");
        volume.setFont(Font.font(18.0));
        ComboBox<Float> v_vol = new ComboBox<>();
        v_vol.setPrefHeight(43);
        v_vol.setPrefWidth(132);
        v_vol.setValue(speech.getVolume());
        ObservableList<Float> vols = FXCollections.observableArrayList();
        vols.addAll(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F);
        v_vol.itemsProperty().setValue(vols);

        Button close = new Button("Close");
        close.setOnAction(e->window.close());
        close.setPrefHeight(53);
        close.setPrefWidth(109);

        AnchorPane.setTopAnchor(close, 282.0);
        AnchorPane.setBottomAnchor(close,15.0);
        AnchorPane.setRightAnchor(close,136.0);
        AnchorPane.setLeftAnchor(close, 150.0);

        speech.setPitch(pitch.getValue());
        speech.setRate(rate.getValue());
        speech.setVolume(v_vol.getValue());

        grid.add(Rate, 0,0);
        grid.add(rate, 1, 0);

        grid.add(Pitch, 0, 1);
        grid.add(pitch, 1, 1);

        grid.add(volume, 0, 2);
        grid.add(v_vol, 1, 2);

        AnchorPane.setTopAnchor(grid, 70.0);
        AnchorPane.setBottomAnchor(grid, 115.0);
        AnchorPane.setRightAnchor(grid, 89.0);
        AnchorPane.setLeftAnchor(grid,100.0);

        root.getChildren().addAll(grid, close);

        Scene scene = new Scene(root, 395, 348, true);
        window.setScene(scene);
        window.setTitle("Sound Properties");
        window.showAndWait();
    }
}