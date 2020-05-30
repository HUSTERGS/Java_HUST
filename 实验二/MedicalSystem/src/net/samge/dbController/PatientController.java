package net.samge.dbController;

import net.samge.db.DBConnection;
import net.samge.model.Doctor;
import net.samge.model.Patient;

import java.sql.*;

public class PatientController {
    /**
     * 患者登录操作
     * @param pid 患者编号
     * @param password 密码
     * @return 登录是否成功
     */
    public static Patient Login(String pid, String password) {
        String SQL = "select * from patient where pid=? and password=?";
        try {
            Connection conn = DBConnection.getDBConnection().getConnection();
            PreparedStatement stm = conn.prepareStatement(SQL);
            stm.setObject(1, pid);
            stm.setObject(2,password);
            ResultSet rst = stm.executeQuery();
            if (rst.next()) {
                return new Patient(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getDouble(4),
                        rst.getTimestamp(5)
                );
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新患者的登录时间
     * @param patient 患者
     */
    public static void updateLoginTime(Patient patient) {
        String SQL = "update patient set last_login_datetime=current_timestamp() where pid = " + patient.getPid();
        try {
            Connection conn = DBConnection.getDBConnection().getConnection();
            Statement stm = conn.createStatement();
            stm.executeUpdate(SQL);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 获得患者的余额
     * @param patient 患者
     * @return 余额
     */
    public static int getBalance(Patient patient) {
        String SQL = "select balance from patient where pid = " + patient.getPid();
        try {
            Connection conn = DBConnection.getDBConnection().getConnection();
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery(SQL);
            if (rst.next()) {
                return rst.getInt(1);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 支付挂号费用
     * @param patient 患者
     * @param fee 挂号费用
     */
    public static void payRegFee(Patient patient, Double fee) {
        if (PatientController.getBalance(patient) >= fee) {
            String SQL = "update patient set balance = balance - ? where pid = ?";
            try {
                Connection conn = DBConnection.getDBConnection().getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);
                stm.setObject(1, fee);
                stm.setObject(2, patient.getPid());
                stm.executeUpdate();
                patient.setBalance(patient.getBalance() - fee);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            String SQL = "update patient set balance = 0 where pid = ?";
            try {
                Connection conn = DBConnection.getDBConnection().getConnection();
                PreparedStatement stm = conn.prepareStatement(SQL);
                stm.setObject(1, patient.getPid());
                stm.executeUpdate();
                patient.setBalance(0);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
