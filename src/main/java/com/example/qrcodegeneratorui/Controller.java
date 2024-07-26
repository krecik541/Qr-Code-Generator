package com.example.qrcodegeneratorui;

import com.example.qrcodegeneratorui.generator.QrCodeGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Label label1;

    @FXML
    private TextArea textInput;

    private String input;

    @FXML
    private Label label2;

    @FXML
    private ChoiceBox<String> errCorrLevelChoiceBox;

    private String errCorrLevel;

    @FXML
    private Button generateButton;

    @FXML
    private Rectangle rectangle;

    @FXML
    private SubScene subScene;

    private final String[] errCorrLevelTable = {"LOW", "MEDIUM", "QUARTILE", "HIGH"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errCorrLevelChoiceBox.getItems().addAll(errCorrLevelTable);
        errCorrLevelChoiceBox.setOnAction(this::getErrCorrLevelChoiceBox);
        errCorrLevelChoiceBox.getSelectionModel().select(0);
        subScene.setVisible(false);

    }

    private void getErrCorrLevelChoiceBox(ActionEvent actionEvent) {
        errCorrLevel = errCorrLevelChoiceBox.getValue();
    }

    public void generate() {
        getTextInput();
        if(input.isEmpty())
            System.out.println("DODAJ NAPIS O TYM ŻE JEST PUSTY STRING");
        else {
            QrCodeGenerator qrCodeGenerator = new QrCodeGenerator(input, errCorrLevel);
            char[][] t = qrCodeGenerator.getDoneQrCode();
            int size = t.length;
            double x = rectangle.getX(), y = rectangle.getY();
            subScene.setVisible(true);

            System.out.println(subScene.getRoot().getClass());
            Group root = new Group();

            for(int i = 0; i < t.length; i++) {
                for(int j = 0; j < t[0].length; j++) {
                    Rectangle rectangle = new Rectangle();
                    rectangle = new Rectangle();
                    rectangle.setX(x + this.rectangle.getWidth() / size * i);
                    rectangle.setY(y + this.rectangle.getHeight() / size * j);
                    rectangle.setWidth(this.rectangle.getWidth() / size);
                    rectangle.setHeight(this.rectangle.getWidth() / size);
                    rectangle.setFill(t[j][i] == 'b' ? Color.BLACK : Color.WHITE);
                    root.getChildren().add(rectangle);
                }
            }
            subScene.setRoot(root);
        }
    }

    public void getTextInput()
    {
        System.out.println(textInput.getText());
        input = textInput.getText();
    }
}