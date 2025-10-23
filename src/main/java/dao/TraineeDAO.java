package dao;

import com.mysql.cj.protocol.Resultset;
import db.DbConnection;
import model.Trainee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TraineeDAO {
    public boolean saveTrainee(Trainee trainee) {
        String sql = "INSERT INTO trainee (name,email,department,stipEnd) VALUES (?,?,?,?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            stmt.setString(1, trainee.getName());
            stmt.setString(2, trainee.getEmail());
            stmt.setString(3, trainee.getDepartment());
            stmt.setDouble(4, trainee.getStipEnd());

            int rowsInserted = stmt.executeUpdate();
            conn.commit();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Trainee> getALlTrainees() {
        List<Trainee> trainees = new ArrayList<>();
        String sql = "SELECT * FROM trainee";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Trainee trainee = new Trainee();
                trainee.setId(rs.getInt("id"));
                trainee.setName(rs.getString("name"));
                trainee.setEmail(rs.getString("email"));
                trainee.setDepartment(rs.getString("department"));
                trainee.setStipEnd(rs.getDouble("stipEnd"));
                trainees.add(trainee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trainees;
    }

    public Trainee getTraineeById(int id) {
        String sql = "SELECT  * FROM trainee WHERE id = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Trainee t = new Trainee();
                    t.setId(rs.getInt("id"));
                    t.setName(rs.getString("name"));
                    t.setEmail(rs.getString("email"));
                    t.setDepartment(rs.getString("department"));
                    t.setStipEnd(rs.getDouble("stipEnd"));
                    return t;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateTrainee(int id, Trainee trainee) {
        String sql = "UPDATE trainee SET name = ?, email = ?, department = ?, stipEnd = ? WHERE id = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false); //  make sure it's same as addTrainee
//            ps.setInt(1, trainee.getId());
            ps.setString(1, trainee.getName());
            ps.setString(2, trainee.getEmail());
            ps.setString(3, trainee.getDepartment());
            ps.setDouble(4, trainee.getStipEnd());
            ps.setInt(5,id);


            int rowsUpdated = ps.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);

            conn.commit(); //  commit the changes
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTrainee(int id) {
        String sql = "DELETE FROM trainee WHERE id = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}




