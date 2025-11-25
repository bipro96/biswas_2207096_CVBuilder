package com.example.cv_builder_96;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class PreviewScreenController {

    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label phoneLabel;
    @FXML private Label addressLabel;

    @FXML private Label degreeLabel;
    @FXML private Label institutionLabel;
    @FXML private Label yearLabel;

    @FXML private Label jobLabel;
    @FXML private Label companyLabel;
    @FXML private Label durationLabel;

    @FXML private Label projectTitleLabel;
    @FXML private Label projectDescLabel;

    @FXML private ImageView previewImageView;

    private long userId;
    private File profileImageFile;


    public void setData(
            long id,
            String name, String email, String phone, String address,
            String degree, String institution, String year,
            String jobTitle, String company, String duration,
            String projectTitle, String projectDescription,
            File imageFile
    ) {
        this.userId = id;
        this.profileImageFile = imageFile;

        nameLabel.setText(name != null ? name : "");
        emailLabel.setText(email != null ? email : "");
        phoneLabel.setText(phone != null ? phone : "");
        addressLabel.setText(address != null ? address : "");

        degreeLabel.setText(degree != null ? degree : "");
        institutionLabel.setText(institution != null ? institution : "");
        yearLabel.setText(year != null ? year : "");

        jobLabel.setText(jobTitle != null ? jobTitle : "");
        companyLabel.setText(company != null ? company : "");
        durationLabel.setText(duration != null ? duration : "");

        projectTitleLabel.setText(projectTitle != null ? projectTitle : "");
        projectDescLabel.setText(projectDescription != null ? projectDescription : "");

        if (imageFile != null) {
            previewImageView.setImage(new Image(imageFile.toURI().toString()));
        }
    }

    @FXML
    private void handleDelete() {
        boolean ok = Database.deleteUser(userId);
        if (ok) {
            new Alert(Alert.AlertType.INFORMATION, "Record Deleted! Returning to Home screen.").show();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) previewImageView.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Welcome to the CV Builder");

            } catch (IOException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Deleted but failed to load Home screen.").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Delete failed! Record may not exist in DB.").show();
        }
    }

    @FXML
    private void handleEdit(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FormScreen.fxml"));
            Parent root = loader.load();

            FormScreenController controller = loader.getController();


            controller.setEditData(
                    userId,
                    nameLabel.getText(), emailLabel.getText(), phoneLabel.getText(), addressLabel.getText(),
                    degreeLabel.getText(), institutionLabel.getText(), yearLabel.getText(),
                    jobLabel.getText(), companyLabel.getText(), durationLabel.getText(),
                    projectTitleLabel.getText(), projectDescLabel.getText(),
                    profileImageFile
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Edit CV");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load the Form screen for editing.").show();
        }
    }
}