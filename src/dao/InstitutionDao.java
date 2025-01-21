package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Institution;

public class InstitutionDao extends Dao {

    public List<Institution> getAll() throws Exception {
        List<Institution> institutions = new ArrayList<>();
        String sql = "SELECT ID, name, detail FROM institution";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Institution institution = new Institution();
                institution.setId(resultSet.getInt("ID"));
                institution.setName(resultSet.getString("name"));
                institution.setDetail(resultSet.getString("detail"));
                institutions.add(institution);
            }
        }

        return institutions;
    }

    public Institution findById(int id) throws Exception {
        Institution institution = null;
        String sql = "SELECT ID, name, detail FROM institution WHERE ID = ?"; // カラム名を大文字に修正

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    institution = new Institution();
                    institution.setId(resultSet.getInt("ID")); // カラム名を大文字に修正
                    institution.setName(resultSet.getString("name"));
                    institution.setDetail(resultSet.getString("detail"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
            throw e; // 例外を再スロー
        }

        return institution;
    }

}
