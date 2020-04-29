package net.samge.dbController;

import net.samge.db.DBConnection;
import net.samge.model.RegisterCategory;

import java.sql.*;
import java.util.ArrayList;

public class RegisterCategoryController {
    public static ArrayList<RegisterCategory> getAllRegisterCategory() {
        String SQL = "select * from register_category";
        ArrayList<RegisterCategory> cats = new ArrayList<>();
        try {
            Connection conn = DBConnection.getDBConnection().getConnection();
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery(SQL);
            while (rst.next()) {
                cats.add(new RegisterCategory(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getLong(5),
                        rst.getLong(6),
                        rst.getDouble(7))
                );
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            return cats;
        }
    }

}
