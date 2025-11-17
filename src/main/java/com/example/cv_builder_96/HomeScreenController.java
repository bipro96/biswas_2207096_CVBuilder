package com.example.cv_builder_96;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeScreenController {

    @FXML
    private void handleCreateCv(ActionEvent event) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FormScreen.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Create Your CV");
        stage.setScene(new Scene(root));
        stage.show();
    }
}