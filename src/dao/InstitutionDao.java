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
    // 支援をデータベースに挿入するメソッド
    public void insert(Integer userID, Integer InstitutionID) throws Exception {
        // SQLのINSERT文
        String sql = "INSERT INTO Bookmark (userID, InstitutionID) VALUES (?, ?)";

        // データベース接続
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // パラメータの設定
            statement.setInt(1, userID);
            statement.setInt(2, InstitutionID);

            // 実行
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // エラーが発生した場合
            throw e;
        }
    }

    public boolean deleteInstitutionById(int id) throws Exception {
        boolean isDeleted = false;
        String sql = "DELETE FROM institution WHERE ID = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            isDeleted = rowsAffected > 0; // 削除成功判定
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }
}