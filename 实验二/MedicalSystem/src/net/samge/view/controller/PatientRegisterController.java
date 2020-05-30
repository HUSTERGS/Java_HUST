package net.samge.view.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.samge.FxUtilTest;
import net.samge.dbController.*;
import net.samge.model.*;

public class PatientRegisterController {
    /**
     * 当前用户剩余余额
     */
    @FXML
    private Text remainBalance;
    /**
     * 当前用户信息
     */
    private Patient currentUser;

    /**
     * 当前用户的名字
     */
    @FXML
    private Text currentUserName;
    /**
     * 当前患者的编号
     */
    @FXML
    private Text currentUserNo;
    /**
     * 用于展示提示文字
     */
    @FXML
    private Text prompt;
    /**
     * 号种类别
     */
    @FXML
    private ComboBox<String> regCat;

    /**
     * 应缴金额
     */
    @FXML
    private TextField regFee;

    /**
     * 实际交款金额
     */
    @FXML
    private TextField actualPay;

    /**
     * 找零金额
     */
    @FXML
    private TextField changeMoney;

    /**
     * 挂号号码
     */
    @FXML
    private TextField regNum;

    /**
     * 科室名称
     */
    @FXML
    private ComboBox<Department> depName;

    /**
     * 医生名称
     */
    @FXML
    private ComboBox<Doctor> docName;

    /**
     * 确定按钮
     */
    @FXML
    private Button confirmButton;

    /**
     * 清除按钮
     */
    @FXML
    private Button clearButton;

    /**
     * 退出按钮
     */
    @FXML
    private Button exitButton;

    /**
     * 号种名称
     */
    @FXML
    private ComboBox<RegisterCategory> catName;


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
    void depNameSelect(ActionEvent event) {
        Doctor selectedDoctor;
        Department selectedDept = depName.getItems().get(depName.getSelectionModel().getSelectedIndex());

        if (docName.getSelectionModel().getSelectedIndex() == -1) {
            selectedDoctor = null;
        } else {
            selectedDoctor = docName.getItems().get(docName.getSelectionModel().getSelectedIndex());
        }
        // 当科室被选中的时候，应该对医生一栏进行修改，只显示对应科室的医生
        docName.getItems().clear();
        docName.getItems().addAll(DoctorController.getAllDoctorInDept(selectedDept));
        if (selectedDoctor != null && docName.getItems().contains(selectedDoctor)) {
            // 如果选中了某一个医生,并且对应的科室中有这个医生，则手动重新选择以保持选项不变
//            docName.getSelectionModel().select(selectedDoctor);
            docName.setValue(selectedDoctor);
        }

        catName.getItems().clear();
        catName.getItems().addAll(RegisterCategoryController.getDeptCategory(selectedDept));
    }


    @FXML
    public void docNameSelect(ActionEvent event) {
        Doctor selectedDoctor = docName.getItems().get(docName.getSelectionModel().getSelectedIndex());
        catName.getItems().clear();
        catName.getItems().addAll(RegisterCategoryController.getDoctorCategory(selectedDoctor));
        regCat.getItems().clear();
        if (selectedDoctor.getSpeciallist() == 0) {
            regCat.getItems().add("普通号");
        } else {
            regCat.getItems().add("专家号");
        }
        regCat.getSelectionModel().select(regCat.getItems().get(0));
    }


    @FXML
    void exit(ActionEvent event) {
        try {
            Parent newRoot = FXMLLoader.load(getClass().getResource("../fxml/PatientLogin.fxml"));
            Stage stage = (Stage) main.getScene().getWindow();
            stage.setScene(new Scene(newRoot));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        catName.getItems().addAll(RegisterCategoryController.getAllRegisterCategory());
        depName.getItems().addAll(DepartmentController.getAllDepartment());
        docName.getItems().addAll(DoctorController.getAllDoctor());
        regCat.getItems().add("专家号");
        regCat.getItems().add("普通号");
        clearButton.setFocusTraversable(false);
        actualPay.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                // 限制只能够输入数字
                if (!newValue.matches("\\d*")) {
                    // 如果不是数字就将文字换为空字符串
                    actualPay.setText(newValue.replaceAll("[^\\d]", ""));
                }
                try {
                    // 尝试解析文字，如果失败不管
                    // 首先,可以编辑就意味着存款不够
                    double value = Double.parseDouble(actualPay.getText());
                    double change = value - Double.parseDouble(regFee.getText()) + currentUser.getBalance();
                    if (change < 0) {
                        changeMoney.setText("交款金额不足");
                    } else {
                        changeMoney.setText(Double.toString(change));
                    }
                } catch (NumberFormatException ignored) {
                }

            }
        });


        // 确认按钮的点击事件函数
        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // 如果检查不通过
                if (checkReady()) {
                    // 如果已经挂号的人数大于等于该号种允许的最大数目,则直接退出
                    if (RegisterController.catCount(catName.getItems().get(catName.getSelectionModel().getSelectedIndex()).getCatid()) >=
                       RegisterCategoryController.getMaxRegNum(catName.getItems().get(catName.getSelectionModel().getSelectedIndex()).getCatid()))
                    {
                        prompt.setText("该号种人数已满,挂号失败");
                        return;
                    }

                    prompt.setText("");
                    RegisterController.addReg(
                            RegisterController.newReg(),
                            catName.getItems().get(catName.getSelectionModel().getSelectedIndex()).getCatid(),
                            docName.getItems().get(docName.getSelectionModel().getSelectedIndex()).getDocid(),
                            currentUser.getPid(),
                            RegisterController.catCount(catName.getItems().get(catName.getSelectionModel().getSelectedIndex()).getCatid()) + 1,
                            0,
                            Double.parseDouble(regFee.getText())
                    );

                    PatientController.payRegFee(currentUser, Double.parseDouble(regFee.getText()));
                    setCurrentUser(currentUser);
                    prompt.setText("挂号成功,剩余余额" + currentUser.getBalance() + "元");
                }
            }
        });

        // 一开始将交款金额设置为不可用
        actualPay.setEditable(false);

        FxUtilTest.autoCompleteComboBoxPlus(depName, (typedText, itemToCompare) -> itemToCompare.toString().contains(typedText));
        FxUtilTest.autoCompleteComboBoxPlus(docName, (typedText, itemToCompare) -> itemToCompare.toString().contains(typedText));
        FxUtilTest.autoCompleteComboBoxPlus(catName, (typedText, itemToCompare) -> itemToCompare.toString().contains(typedText));
        FxUtilTest.autoCompleteComboBoxPlus(regCat, (typedText, itemToCompare) -> itemToCompare.toString().contains(typedText));

    }

//    @FXML
//    public void inputMoney(InputMethodEvent inputMethodEvent) {
//        // 获得
//
//    }

    /**
     * 号种类别选择事件
     *
     * @param event
     */
    @FXML
    public void catNameSelect(ActionEvent event) {
        RegisterCategory reg = catName.getItems().get(regCat.getSelectionModel().getSelectedIndex());
        regFee.setText(Double.toString(reg.getRegFee()));
        System.out.println("被选中时的为: " + RegisterController.newReg());
        regNum.setText(RegisterController.newReg());
        prompt.setText("");

        if (reg.getRegFee() > currentUser.getBalance()) {
            // 如果说实际费用要大于用户的存款,那么就将交款金额设置为可用
            actualPay.setEditable(true);
            changeMoney.setText("");
        } else {
            changeMoney.setText("将使用账户余额支付");
            actualPay.setEditable(false);
        }
    }

    /**
     * 检查是否准备好提交，检查几个选项框是否已经选中，并且交款金额足够
     * @return 真假值
     */
    private boolean checkReady() {
        if (depName.getSelectionModel().getSelectedIndex() == -1) {
            prompt.setText("未选择科室");
            return false;
        } else if (docName.getSelectionModel().getSelectedIndex() == -1) {
            prompt.setText("未选择医生");
            return false;
        } else if (regCat.getSelectionModel().getSelectedIndex() == -1) {
            prompt.setText("未选择号种类别");
            return false;
        } else if (catName.getSelectionModel().getSelectedIndex() == -1) {
            prompt.setText("未选择号种名称");
            return false;
        } else if (changeMoney.getText().equals("")) {
            prompt.setText("未输入缴费金额");
            return false;
        } else if (changeMoney.getText().equals("交款金额不足")) {
            prompt.setText("交款金额不足");
            return false;
        }
        return true;
    }

    /**
     * 登录时用于保存当前登录患者的信息
     * @param currentUser 当前患者
     */
    public void setCurrentUser(Patient currentUser) {
        this.currentUser = currentUser;
        currentUserName.setText(currentUser.getName());
        currentUserNo.setText("#" + currentUser.getPid());
        remainBalance.setText(Double.toString(currentUser.getBalance()));
    }
}

