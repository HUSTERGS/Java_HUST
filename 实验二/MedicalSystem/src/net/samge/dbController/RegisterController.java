package net.samge.dbController;

import net.samge.db.DBConnection;
import net.samge.model.Doctor;
import net.samge.model.PatientRegister;

import java.sql.*;
import java.util.ArrayList;

public class RegisterController {
    /**
     * 由于使用的regId是字符串类型，所以无法直接使用auto inc来让数据库处理相关逻辑，而是需要自己生成新的连续序号，可以通过锁来使得每一次
     * 得到的都是连续的
     * @return
     */
    public static String newReg() {
        // 返回一个新的regId,为最新值+1
        String SQL = "select reg_id from register order by reg_id desc limit 1";
        try {
            Connection conn = DBConnection.getDBConnection().getConnection();
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery(SQL);
            if (rst.next()) {
                int reg_id = Integer.parseInt(rst.getString(1));
//                System.out.println("新的编号为:" + String.format("%06d", reg_id+1));
                return String.format("%06d", reg_id+1);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return "000001";
    }


    public static void addReg(String regId, String catId, String docId, String pid, long currentRegCount, long unreg, double regFee) {
        String SQL = "insert into register (reg_id, catid, docid, pid, current_reg_count, unreg, reg_fee) VALUES (?, ?, ?, ? ,? ,? ,?)";
        try {
            Connection conn = DBConnection.getDBConnection().getConnection();
            PreparedStatement stm = conn.prepareStatement(SQL);
            stm.setObject(1, regId);
            stm.setObject(2, catId);
            stm.setObject(3, docId);
            stm.setObject(4, pid);
            stm.setObject(5, currentRegCount);
            stm.setObject(6, unreg);
            stm.setObject(7, regFee);
            stm.executeUpdate();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static int catCount(String catId) {
        String SQL = "select count(*) from register where catid = " + catId;
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
     * 返回某一个医生下的所有挂号记录
     * @param doctor 医生
     * @return 所有挂号记录
     */
    public static ArrayList<PatientRegister> getDocPatientRegister(Doctor doctor) {
        String SQL = "select register.reg_id, patient.name, register.reg_datetime, register_category.name from\n" +
                "register, doctor, patient, register_category\n" +
                "where register.docid = doctor.docid and\n" +
                "      register.pid = patient.pid and\n" +
                "      register_category.catid = register.catid and\n" +
                "      doctor.docid = " + doctor.getDocid();
        ArrayList<PatientRegister> result = new ArrayList<>();
        try {
            Connection conn = DBConnection.getDBConnection().getConnection();
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery(SQL);

            while (rst.next()) {
                result.add(new PatientRegister(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getTimestamp(3),
                        rst.getString(4)
                ));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

