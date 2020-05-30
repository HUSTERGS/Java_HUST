/**
 * Sample Skeleton for 'login.fxml' Controller Class
 */

package net.samge.view.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController  {
    @FXML
    private AnchorPane main;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    void DoctorLogin(ActionEvent event) {
        try {
            Parent newRoot = FXMLLoader.load(getClass().getResource("../fxml/DoctorLogin.fxml"));
            Stage stage = (Stage) main.getScene().getWindow();
            stage.setScene(new Scene(newRoot));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Exit(ActionEvent event) {

    }

    @FXML
    void PatientLogin(ActionEvent event) {
        try {
            Parent newRoot = FXMLLoader.load(getClass().getResource("../fxml/PatientLogin.fxml"));
            Stage stage = (Stage) main.getScene().getWindow();
            stage.setScene(new Scene(newRoot));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
}
