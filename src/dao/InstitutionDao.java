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
        String sql = "SELECT InstitutionID, name, detail, video, PdfPath, " +
                     "IncomeRequirement, EligibleChildrenCount, RequiredEmploymentStatus, " +
                     "EligibilityReason, RequiredSchoolStatus FROM institution";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Institution institution = new Institution();
                institution.setID(resultSet.getInt("InstitutionID"));
                institution.setName(resultSet.getString("name"));
                institution.setDetail(resultSet.getString("detail"));
                institution.setVideo(resultSet.getString("video"));
                institution.setPdfPath(resultSet.getString("PdfPath"));
                institution.setIncomeRequirement(resultSet.getObject("IncomeRequirement") != null ? resultSet.getInt("IncomeRequirement") : null);
                institution.setEligibleChildrenCount(resultSet.getObject("EligibleChildrenCount") != null ? resultSet.getInt("EligibleChildrenCount") : null);
                institution.setRequiredEmploymentStatus(resultSet.getString("RequiredEmploymentStatus"));
                institution.setEligibilityReason(resultSet.getString("EligibilityReason"));
                institution.setRequiredSchoolStatus(resultSet.getString("RequiredSchoolStatus"));

                institutions.add(institution);
            }
        }

        return institutions;
    }

    // IDを指定して支援情報を取得
    public Institution findById(int id) throws Exception {
        Institution institution = null;
        String sql = "SELECT InstitutionID, name, detail, video, PdfPath, " +
                     "IncomeRequirement, EligibleChildrenCount, RequiredEmploymentStatus, " +
                     "EligibilityReason, RequiredSchoolStatus FROM institution WHERE InstitutionID = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    institution = new Institution();
                    institution.setID(resultSet.getInt("InstitutionID"));
                    institution.setName(resultSet.getString("name"));
                    institution.setDetail(resultSet.getString("detail"));
                    institution.setVideo(resultSet.getString("video"));
                    institution.setPdfPath(resultSet.getString("PdfPath"));
                    institution.setIncomeRequirement(resultSet.getObject("IncomeRequirement") != null ? resultSet.getInt("IncomeRequirement") : null);
                    institution.setEligibleChildrenCount(resultSet.getObject("EligibleChildrenCount") != null ? resultSet.getInt("EligibleChildrenCount") : null);
                    institution.setRequiredEmploymentStatus(resultSet.getString("RequiredEmploymentStatus"));
                    institution.setEligibilityReason(resultSet.getString("EligibilityReason"));
                    institution.setRequiredSchoolStatus(resultSet.getString("RequiredSchoolStatus"));
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
        String sql = "INSERT INTO institution (name, detail, video, PdfPath, IncomeRequirement, EligibleChildrenCount, RequiredEmploymentStatus, EligibilityReason, RequiredSchoolStatus) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, institution.getName());
            statement.setString(2, institution.getDetail());
            statement.setString(3, institution.getVideo());
            statement.setString(4, institution.getPdfPath());
            statement.setObject(5, institution.getIncomeRequirement(), java.sql.Types.INTEGER);
            statement.setObject(6, institution.getEligibleChildrenCount(), java.sql.Types.INTEGER);
            statement.setString(7, institution.getRequiredEmploymentStatus());
            statement.setString(8, institution.getEligibilityReason());
            statement.setString(9, institution.getRequiredSchoolStatus());

            statement.executeUpdate();

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
    public boolean updateInstitution(int id, String name, String detail, String video, String pdfPath,
                                     Integer incomeRequirement, Integer eligibleChildrenCount,
                                     String requiredEmploymentStatus, String eligibilityReason,
                                     String requiredSchoolStatus) throws Exception {
        String sql = "UPDATE institution SET name=?, detail=?, video=?, PdfPath=?, " +
                     "IncomeRequirement=?, EligibleChildrenCount=?, " +
                     "RequiredEmploymentStatus=?, EligibilityReason=?, RequiredSchoolStatus=? " +
                     "WHERE InstitutionID=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, detail);
            stmt.setString(3, video);
            stmt.setString(4, pdfPath);
            stmt.setObject(5, incomeRequirement, java.sql.Types.INTEGER);
            stmt.setObject(6, eligibleChildrenCount, java.sql.Types.INTEGER);
            stmt.setString(7, requiredEmploymentStatus);
            stmt.setString(8, eligibilityReason);
            stmt.setString(9, requiredSchoolStatus);
            stmt.setInt(10, id);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }
}
