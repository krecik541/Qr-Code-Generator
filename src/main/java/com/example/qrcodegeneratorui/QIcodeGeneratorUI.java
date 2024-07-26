package com.example.qrcodegeneratorui;

import com.example.qrcodegeneratorui.generator.QrCodeGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class QIcodeGeneratorUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Stage stage = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        Scene scene = new Scene(root);
        Image iconImage = new Image("images/icon.png");

        stage.setTitle("QR Code Generator");
        stage.getIcons().add(iconImage);
        stage.setResizable(false);
        stage.setX(100);
        stage.setY(200);

        //QrCodeGenerator qrCodeGenerator = new QrCodeGenerator("HELLO WORLD", "MEDIUM");

        stage.setScene(scene);
        stage.setY(200);
        stage.setX(600);
        stage.show();
    }
}