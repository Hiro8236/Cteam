package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Institution;

public class InstitutionDao extends Dao {

    // 全ての支援情報を取得
    public List<Institution> getAll() throws Exception {
        List<Institution> institutions = new ArrayList<>();
        String sql = "SELECT InstitutionID, name, detail, video, PdfPath FROM institution";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Institution institution = new Institution();
                institution.setId(resultSet.getInt("InstitutionID"));
                institution.setName(resultSet.getString("name"));
                institution.setDetail(resultSet.getString("detail"));
                institution.setVideo(resultSet.getString("video"));
                institution.setPdfPath(resultSet.getString("PdfPath")); // PdfPathを取得して設定
                institutions.add(institution);
            }
        }

        return institutions;
    }

    // IDを指定して支援情報を取得
    public Institution findById(int id) throws Exception {
        Institution institution = null;
        String sql = "SELECT InstitutionID, name, detail, video, PdfPath FROM institution WHERE InstitutionID = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    institution = new Institution();
                    institution.setId(resultSet.getInt("InstitutionID"));
                    institution.setName(resultSet.getString("name"));
                    institution.setDetail(resultSet.getString("detail"));
                    institution.setVideo(resultSet.getString("video"));
                    institution.setPdfPath(resultSet.getString("PdfPath")); // PdfPathを取得して設定
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
            throw e;
        }

        return institution;
    }

    // 支援をデータベースに挿入し、自動生成されたIDを返す
    public int insert(Institution institution) throws Exception {
        String sql = "INSERT INTO institution (name, detail, video, PdfPath) VALUES (?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // パラメータを設定
            statement.setString(1, institution.getName());
            statement.setString(2, institution.getDetail());
            statement.setString(3, institution.getVideo());
            statement.setString(4, institution.getPdfPath()); // PdfPathを設定

            System.out.println(institution.getName());
            System.out.println(institution.getDetail());
            System.out.println(institution.getVideo());
            System.out.println(institution.getPdfPath());

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

    // 指定したIDの支援情報を削除
    public boolean deleteInstitutionById(int id) throws Exception {
        boolean isDeleted = false;
        String sql = "DELETE FROM institution WHERE InstitutionID = ?";

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

    // 指定したIDの支援情報を更新
    public boolean updateInstitution(Integer id, String name, String detail, String pdfPath) throws Exception {
        String sql = "UPDATE institution SET name = ?, detail = ?, PdfPath = ? WHERE InstitutionID = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // パラメータを設定
            statement.setString(1, name);
            statement.setString(2, detail);
            statement.setString(3, pdfPath); // PdfPathを更新
            statement.setInt(4, id);

            // クエリを実行し、影響を受けた行数を取得
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // 更新成功の場合はtrue
        }
    }
}
