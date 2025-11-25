package com.example.cv_builder_96;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private long userId = -1;


    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @FXML
    public void initialize() {
        Database.createTable();
    }

    public void setEditData(
            long id,
            String name, String email, String phone, String address,
            String degree, String institution, String year,
            String jobTitle, String company, String duration,
            String projectTitle, String projectDescription,
            File imageFile
    ) {
        this.userId = id;
        this.selectedImageFile = imageFile;

        nameField.setText(name);
        emailField.setText(email);
        phoneField.setText(phone);
        addressField.setText(address);

        degreeField.setText(degree);
        institutionField.setText(institution);
        yearField.setText(year);

        jobTitleField.setText(jobTitle);
        companyField.setText(company);
        durationField.setText(duration);

        projectTitleField.setText(projectTitle);
        projectDescriptionField.setText(projectDescription);

        if (imageFile != null) {
            profileImageView.setImage(new Image(imageFile.toURI().toString()));
        }
    }


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
    private void handlePreview(javafx.event.ActionEvent event) {

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || email.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter at least **Name** and **Email** before previewing/saving.").show();
            return;
        }

        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String degree = degreeField.getText().trim();
        String institution = institutionField.getText().trim();
        String year = yearField.getText().trim();
        String jobTitle = jobTitleField.getText().trim();
        String company = companyField.getText().trim();
        String duration = durationField.getText().trim();
        String projectTitle = projectTitleField.getText().trim();
        String projectDescription = projectDescriptionField.getText().trim();

        boolean isUpdate = userId > 0;

        CompletableFuture.supplyAsync(() -> {
                    if (isUpdate) {
                        boolean success = Database.updateUser(userId, name, email, phone, address, degree, institution, year,
                                jobTitle, company, duration, projectTitle, projectDescription);
                        return success ? userId : -1L;
                    } else {
                        return Database.insertUser(name, email, phone, address, degree, institution, year,
                                jobTitle, company, duration, projectTitle, projectDescription);
                    }
                }, executor)
                .thenAccept(resultId -> {
                    if (resultId > 0) {
                        Platform.runLater(() -> {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("PreviewScreen.fxml"));
                                Parent root = loader.load();

                                PreviewScreenController controller = loader.getController();

                                controller.setData(
                                        resultId,
                                        name, email, phone, address,
                                        degree, institution, year,
                                        jobTitle, company, duration,
                                        projectTitle, projectDescription,
                                        selectedImageFile
                                );

                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage.setTitle("CV Preview");
                                stage.setScene(new Scene(root));
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                                new Alert(Alert.AlertType.ERROR, "Failed to load preview screen.").show();
                            }
                        });
                    } else {
                        Platform.runLater(() -> {

                            new Alert(Alert.AlertType.ERROR, isUpdate ?
                                    "Could not update record. Check if Email is already used by another record." :
                                    "Could not save to database. Check if Email is already used.").show();
                        });
                    }
                }).exceptionally(ex -> {
                    ex.printStackTrace();
                    Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Unexpected error: " + ex.getMessage()).show());
                    return null;
                });
    }

    public void shutdownExecutor() { executor.shutdownNow(); }
}