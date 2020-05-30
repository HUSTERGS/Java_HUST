package net.samge.view.controller;


import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import net.samge.dbController.DoctorController;
import net.samge.dbController.RegisterController;
import net.samge.model.Doctor;
import net.samge.model.DoctorIncome;
import net.samge.model.PatientRegister;
import net.samge.model.Register;

/**
 * Sample Skeleton for 'DoctorPanel.fxml' Controller Class
 */
public class DoctorPanelController {

    @FXML
    private DatePicker startTime;

    @FXML
    private AnchorPane main;
    /**
     * 当前医生
     */
    private Doctor currentUser;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    /**
     * 病人面板对应的tab
     */
    @FXML // fx:id="tabPatient"
    private Tab tabPatient; // Value injected by FXMLLoader

    /**
     * 病人列表
     */
    @FXML // fx:id="patientTable"
    private TableView<PatientRegister> patientTable; // Value injected by FXMLLoader

    /**
     * 收入面板对应的标签
     */
    @FXML // fx:id="tabIncome"
    private Tab tabIncome; // Value injected by FXMLLoader

    /**
     * 收入列表
     */
    @FXML // fx:id="incomeTable"
    private TableView<DoctorIncome> incomeTable; // Value injected by FXMLLoader

    /**
     * 退出按钮
     */
    @FXML // fx:id="exitButton"
    private Button exitButton; // Value injected by FXMLLoader

    /**
     * 医生编号
     */
    @FXML // fx:id="docNo"
    private Text docNo; // Value injected by FXMLLoader

    /**
     * 医生名字
     */
    @FXML // fx:id="docName"
    private Text docName; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tabPatient != null : "fx:id=\"tabPatient\" was not injected: check your FXML file 'DoctorPanel.fxml'.";
        assert patientTable != null : "fx:id=\"patientTable\" was not injected: check your FXML file 'DoctorPanel.fxml'.";
        assert tabIncome != null : "fx:id=\"tabIncome\" was not injected: check your FXML file 'DoctorPanel.fxml'.";
        assert incomeTable != null : "fx:id=\"incomeTable\" was not injected: check your FXML file 'DoctorPanel.fxml'.";
        assert exitButton != null : "fx:id=\"exitButton\" was not injected: check your FXML file 'DoctorPanel.fxml'.";
        assert docNo != null : "fx:id=\"docNo\" was not injected: check your FXML file 'DoctorPanel.fxml'.";
        assert docName != null : "fx:id=\"docName\" was not injected: check your FXML file 'DoctorPanel.fxml'.";


        // 退出事件
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AnchorPane root = FXMLLoader.load(getClass().getResource("../fxml/DoctorLogin.fxml"));
                    main.getChildren().setAll(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ((TableColumn) patientTable.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<PatientRegister, String>("regId"));
        ((TableColumn) patientTable.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<PatientRegister, String>("patientName"));
        ((TableColumn) patientTable.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<PatientRegister, Timestamp>("regDateTime"));
        ((TableColumn) patientTable.getColumns().get(3)).setCellValueFactory(new PropertyValueFactory<PatientRegister, String>("regCat"));

        ((TableColumn)incomeTable.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<DoctorIncome, String>("depName"));
        ((TableColumn)incomeTable.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<DoctorIncome, String>("docId"));
        ((TableColumn)incomeTable.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<DoctorIncome, String>("docName"));
        ((TableColumn)incomeTable.getColumns().get(3)).setCellValueFactory(new PropertyValueFactory<DoctorIncome, String>("regCat"));
        ((TableColumn)incomeTable.getColumns().get(4)).setCellValueFactory(new PropertyValueFactory<DoctorIncome, Integer>("regNum"));
        ((TableColumn)incomeTable.getColumns().get(5)).setCellValueFactory(new PropertyValueFactory<DoctorIncome, Integer>("income"));
        incomeTable.getItems().addAll(DoctorController.getDoctorIncomeList(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString()));
        startTime.setValue(LocalDate.now());
        startTime.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(startTime.getValue());
                incomeTable.getItems().clear();
                incomeTable.getItems().addAll(DoctorController.getDoctorIncomeList(date));
            }
        });
    }

    public void setCurrentUser(Doctor doctor) {
        this.currentUser = doctor;
        docName.setText(doctor.getName());
        docNo.setText("#" + doctor.getDocid());
        patientTable.getItems().addAll(RegisterController.getDocPatientRegister(currentUser));
    }
}
