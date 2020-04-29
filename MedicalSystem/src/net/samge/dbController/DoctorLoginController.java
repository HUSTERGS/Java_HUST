package net.samge.dbController;

import net.samge.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorLoginController {
    public static boolean Login(String docId, String password) {
        String SQL = "select * from doctor where docid=? and password=?";
        try {
            Connection conn = DBConnection.getDBConnection().getConnection();
            PreparedStatement stm = conn.prepareStatement(SQL);
            stm.setObject(1, docId);
            stm.setObject(2,password);
            ResultSet rst = stm.executeQuery();
            if (rst.next()) {
                return true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
