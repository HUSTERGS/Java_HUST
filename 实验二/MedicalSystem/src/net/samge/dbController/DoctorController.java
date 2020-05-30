package net.samge.dbController;


import net.samge.db.DBConnection;
import net.samge.model.Department;
import net.samge.model.Doctor;
import net.samge.model.DoctorIncome;

import javax.print.Doc;
import java.sql.*;
import java.util.ArrayList;

public class DoctorController {
    public static ArrayList<Doctor> getAllDoctor() {
        String SQL = "select * from doctor";
        ArrayList<Doctor> doctors = new ArrayList<>();
        try {
            Connection conn = DBConnection.getDBConnection().getConnection();
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery(SQL);
            while (rst.next()) {
                doctors.add(new Doctor(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getLong(6),
                        rst.getTimestamp(7)
                ));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            return doctors;
        }
    }

    public static ArrayList<Doctor> getAllDoctorInDept(Department dept) {
        String SQL = "select * from doctor where depid = " + dept.getDepid();
        ArrayList<Doctor> doctors = new ArrayList<>();
        try {
            Connection conn = DBConnection.getDBConnection().getConnection();
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery(SQL);
            while (rst.next()) {
                doctors.add(new Doctor(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getLong(6),
                        rst.getTimestamp(7)
                ));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            return doctors;
        }
    }

    public static ArrayList<DoctorIncome> getDoctorIncomeList(String startTime) {
        System.out.println("时间为" + startTime);
        ArrayList<DoctorIncome> result = new ArrayList<>();
        String SQL = "select department.name, doctor.docid, doctor.name, register_category.name, count(*) , sum(register.reg_fee) from\n" +
                "doctor, register, register_category, department\n" +
                "where register.docid = doctor.docid and\n" +
                "      register_category.catid = register.catid and\n" +
                "      register_category.depid = department.depid and\n" +
                "      register.reg_datetime > '" + startTime + "'\n" +
                "group by doctor.docid";
        try {
            Connection conn = DBConnection.getDBConnection().getConnection();
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery(SQL);
            while (rst.next()) {
                result.add(new DoctorIncome(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getInt(5),
                        rst.getInt(6)
                ));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static Doctor Login(String docId, String password) {
        String SQL = "select * from doctor where docid=? and password=?";
        try {
            Connection conn = DBConnection.getDBConnection().getConnection();
            PreparedStatement stm = conn.prepareStatement(SQL);
            stm.setObject(1, docId);
            stm.setObject(2,password);
            ResultSet rst = stm.executeQuery();
            if (rst.next()) {
                return new Doctor(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getLong(6),
                        rst.getTimestamp(7)
                );
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateLoginTime(Doctor doctor) {
        String SQL = "update doctor set last_login_datetime=current_timestamp() where docid = " + doctor.getDocid();
        try {
            Connection conn = DBConnection.getDBConnection().getConnection();
            Statement stm = conn.createStatement();
            stm.executeUpdate(SQL);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
