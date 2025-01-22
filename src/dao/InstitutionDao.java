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
        String sql = "SELECT ID, name, detail FROM institution WHERE ID = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    institution = new Institution();
                    institution.setId(resultSet.getInt("ID"));
                    institution.setName(resultSet.getString("name"));
                    institution.setDetail(resultSet.getString("detail"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
            throw e;
        }

        return institution;
    }

    // 支援をデータベースに挿入し、自動生成されたIDを返すメソッド
    public int insert(Institution institution) throws Exception {
        String sql = "INSERT INTO institution (name, detail) VALUES (?, ?)";
        int generatedId = -1;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // パラメータの設定
            statement.setString(1, institution.getName());
            statement.setString(2, institution.getDetail());

            // 実行
            statement.executeUpdate();

            // 自動生成されたIDを取得
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return generatedId;
    }

    // 追加したメソッド：staffIDとinstitutionIDを使ってBookmarkに挿入
    public void insert(int staffID, int institutionID) throws Exception {
        String sql = "INSERT INTO Bookmark (userID, InstitutionID) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // パラメータの設定
            statement.setInt(1, staffID);
            statement.setInt(2, institutionID);

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

            isDeleted = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }



    public boolean updateInstitution(Integer id, String name, String detail) throws Exception {
        String sql = "UPDATE institution SET name = ?, detail = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // パラメータを設定
            statement.setString(1, name);
            statement.setString(2, detail);
            statement.setInt(3, id);

            // クエリを実行し、影響を受けた行数を取得
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // 1以上なら更新成功
        }
    }
}
