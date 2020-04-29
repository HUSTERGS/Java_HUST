package net.samge.dbController;

import net.samge.db.DBConnection;
import net.samge.model.Department;
import net.samge.model.RegisterCategory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DepartmentController {
    public static ArrayList<Department> getAllDepartment() {
        String SQL = "select * from department";
        ArrayList<Department> deps = new ArrayList<>();
        try {
            Connection conn = DBConnection.getDBConnection().getConnection();
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery(SQL);
            while (rst.next()) {
                deps.add(new Department(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3)
                ));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            return deps;
        }
    }
}
