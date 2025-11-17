package com.example.cv_builder_96;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class PreviewScreenController {

    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label phoneLabel;
    @FXML private Label addressLabel;

    @FXML private Label degreeLabel;
    @FXML private Label institutionLabel;
    @FXML private Label yearLabel;

    @FXML private Label jobTitleLabel;
    @FXML private Label companyLabel;
    @FXML private Label durationLabel;

    @FXML private Label projectTitleLabel;
    @FXML private Label projectDescriptionLabel;

    @FXML private ImageView previewImageView;

    public void setData(
            String name,
            String email,
            String phone,
            String address,
            String degree,
            String institution,
            String year,
            String jobTitle,
            String company,
            String duration,
            String projectTitle,
            String projectDescription,
            File imageFile
    ) {
        nameLabel.setText(name);
        emailLabel.setText(email);
        phoneLabel.setText(phone);
        addressLabel.setText(address);

        degreeLabel.setText(degree);
        institutionLabel.setText(institution);
        yearLabel.setText(year);

        jobTitleLabel.setText(jobTitle);
        companyLabel.setText(company);
        durationLabel.setText(duration);

        projectTitleLabel.setText(projectTitle);
        projectDescriptionLabel.setText(projectDescription);

        if (imageFile != null) {
            previewImageView.setImage(new Image(imageFile.toURI().toString()));
        }
    }
}