package com.example.cv_builder_96;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class FormScreenController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextArea addressField;

    @FXML private TextField degreeField;
    @FXML private TextField institutionField;
    @FXML private TextField yearField;

    @FXML private TextField jobTitleField;
    @FXML private TextField companyField;
    @FXML private TextField durationField;

    @FXML private TextField projectTitleField;
    @FXML private TextArea projectDescriptionField;

    @FXML private ImageView profileImageView;
    private File selectedImageFile;


    @FXML
    private void handleChoosePhoto() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose Profile Image");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        File file = chooser.showOpenDialog(null);
        if (file != null) {
            selectedImageFile = file;
            profileImageView.setImage(new Image(file.toURI().toString()));
        }
    }


    @FXML
    private void handlePreview() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("PreviewScreen.fxml"));
        Parent root = loader.load();

        PreviewScreenController controller = loader.getController();

        controller.setData(
                nameField.getText(),
                emailField.getText(),
                phoneField.getText(),
                addressField.getText(),
                degreeField.getText(),
                institutionField.getText(),
                yearField.getText(),
                jobTitleField.getText(),
                companyField.getText(),
                durationField.getText(),
                projectTitleField.getText(),
                projectDescriptionField.getText(),
                selectedImageFile
        );

        Stage stage = new Stage();
        stage.setTitle("CV Preview");
        stage.setScene(new Scene(root));
        stage.show();
    }
}