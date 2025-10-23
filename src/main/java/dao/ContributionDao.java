package dao;


import db.DbConnection;
import model.Contribution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContributionDao {
    public boolean saveContribution(Contribution contribution) {
        String sql = "INSERT INTO contribution (employerETF,employerEPF,employeeEPF) VALUES (?,?,?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            stmt.setDouble(1, contribution.getEmployerETF());
            stmt.setDouble(2, contribution.getEmployerEPF());
            stmt.setDouble(3, contribution.getEmployeeEPF());

            int rowsInserted = stmt.executeUpdate();
            conn.commit();

            return rowsInserted>0;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}
