/**
 * Sample Skeleton for 'PatientLogin.fxml' Controller Class
 */

package net.samge.view.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.samge.dbController.DepartmentController;
import net.samge.dbController.RegisterCategoryController;
import net.samge.model.Department;

public class PatientLoginController {
    @FXML
    private TextField regCat;
    @FXML
    private TextField regFee;
    @FXML
    private TextField actualPay;
    @FXML
    private TextField changeMoney;
    @FXML
    private TextField regNum;
    @FXML
    private ComboBox depName;
    @FXML
    private TextField docName;
    @FXML
    private Button confirmButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button exitButton;

    @FXML
    private ComboBox catName;

    @FXML
    private SplitPane main;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    void clear(ActionEvent event) {

    }

    @FXML
    void confirm(ActionEvent event) {

    }

    @FXML
    void exit(ActionEvent event) {
        try {
            Parent newRoot = FXMLLoader.load(getClass().getResource("../fxml/login.fxml"));
            Stage stage = (Stage) main.getScene().getWindow();
            stage.setScene(new Scene(newRoot));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        catName.getItems().addAll(RegisterCategoryController.getAllRegisterCategory());
        depName.getItems().addAll(DepartmentController.getAllDepartment());
        clearButton.setFocusTraversable(false);
    }
}
