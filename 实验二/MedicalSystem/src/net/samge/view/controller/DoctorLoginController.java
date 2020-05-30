
package net.samge.view.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.samge.dbController.DoctorController;
import net.samge.model.Doctor;
import net.samge.model.Patient;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DoctorLoginController {


    @FXML
    private AnchorPane main;

    @FXML
    private PasswordField password;

    @FXML
    private TextField pid;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="usernamePrompt"
    private Text usernamePrompt; // Value injected by FXMLLoader

    @FXML // fx:id="passwordPrompt"
    private Text passwordPrompt; // Value injected by FXMLLoader

    @FXML // fx:id="LoginButton"
    private Button LoginButton; // Value injected by FXMLLoader

    @FXML // fx:id="CancelButton"
    private Button CancelButton; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert usernamePrompt != null : "fx:id=\"usernamePrompt\" was not injected: check your FXML file 'PatientLogin.fxml'.";
        assert passwordPrompt != null : "fx:id=\"passwordPrompt\" was not injected: check your FXML file 'PatientLogin.fxml'.";
        assert LoginButton != null : "fx:id=\"LoginButton\" was not injected: check your FXML file 'PatientLogin.fxml'.";
        assert CancelButton != null : "fx:id=\"CancelButton\" was not injected: check your FXML file 'PatientLogin.fxml'.";


        LoginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (checkReady()) {
                    usernamePrompt.setText("");
                    passwordPrompt.setText("");
                    Doctor doctor = DoctorController.Login(pid.getText(), password.getText());
                    if (doctor != null) {
                        // 登录成功
                        try {
//                            Parent newRoot = FXMLLoader.load(getClass().getResource("../fxml/PatientRegister.fxml"));
//                            Stage stage = (Stage) main.getScene().getWindow();
//                            stage.setScene(new Scene(newRoot));
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/DoctorPanel.fxml"));
                            Parent root = fxmlLoader.load();
                            DoctorPanelController prc = fxmlLoader.getController();
//                            prc.setCurrentUser(doctor);
//                            Stage stage = new Stage();
//                            stage.setScene(new Scene(root));
//                            stage.show();
                            DoctorController.updateLoginTime(doctor);
                            prc.setCurrentUser(doctor);
                            main.getChildren().setAll(root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        passwordPrompt.setText("编号或密码错误");
                    }
                }
            }
        });

        CancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent newRoot = FXMLLoader.load(getClass().getResource("../fxml/login.fxml"));
                    Stage stage = (Stage) main.getScene().getWindow();
                    stage.setScene(new Scene(newRoot));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        pid.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (newValue.equals("")) {
                    usernamePrompt.setText("医生编号不能为空");
                } else {
                    usernamePrompt.setText("");
                }
            }
        });

        password.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (newValue.equals("")) {
                    passwordPrompt.setText("密码不能为空");
                } else {
                    passwordPrompt.setText("");
                }
            }
        });
    }

    private boolean checkReady() {
        if (pid.getText().equals("")) {
            usernamePrompt.setText("医生编号不能为空");
            return false;
        } else if (password.getText().equals("")) {
            passwordPrompt.setText("密码不能为空");
            return false;
        }
        return true;
    }
}
