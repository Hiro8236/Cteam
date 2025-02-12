package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Suggest;

public class SuggestListDao extends Dao {

    // ユーザーのIDを基に適用可能な支援制度を取得
    public List<Suggest> getByUserId(int userId) throws Exception {
        List<Suggest> suggestList = new ArrayList<>();

        // SQL文を作成
        String sql = "SELECT i.* " +
                     "FROM institution i " +
                     "JOIN user u ON (" +
                     "    (u.AnnualIncome IS NULL OR u.AnnualIncome >= i.IncomeRequirement) " +
                     "    AND (u.ChildrenCount IS NULL OR u.ChildrenCount >= i.EligibleChildrenCount) " +
                     "    AND (u.EmploymentStatus IS NULL OR u.EmploymentStatus = i.RequiredEmploymentStatus) " +
                     "    AND (u.SingleParentReason IS NULL OR u.SingleParentReason = i.EligibilityReason) " +
                     "    AND (u.ChildSchoolStatus IS NULL OR u.ChildSchoolStatus = i.RequiredSchoolStatus) " +
                     ") " +
                     "WHERE u.userID = ?";

        // すべての条件がNULLの場合、結果を返さないようにするための修正
        String checkSql = "SELECT COUNT(*) FROM user u WHERE u.userID = ? AND " +
                          "(u.AnnualIncome IS NULL AND u.ChildrenCount IS NULL AND u.EmploymentStatus IS NULL " +
                          "AND u.SingleParentReason IS NULL AND u.ChildSchoolStatus IS NULL)";

        try (Connection conn = getConnection()) {
            // すべての条件がNULLの場合、支援制度を表示しない
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, userId);
                try (ResultSet checkRs = checkStmt.executeQuery()) {
                    if (checkRs.next() && checkRs.getInt(1) == 0) {
                        // SQL文を実行
                        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                            stmt.setInt(1, userId);
                            try (ResultSet rs = stmt.executeQuery()) {
                                while (rs.next()) {
                                    // Suggestオブジェクトを作成
                                    Suggest suggest = new Suggest();
                                    suggest.setInstitutionID(rs.getInt("InstitutionID"));
                                    suggest.setName(rs.getString("Name"));
                                    suggest.setDetail(rs.getString("Detail"));
                                    suggest.setVideo(rs.getString("Video"));
                                    suggest.setPdfPath(rs.getString("PDFPath"));
                                    suggest.setIncomeRequirement(rs.getInt("IncomeRequirement"));
                                    suggest.setEligibleChildrenCount(rs.getInt("EligibleChildrenCount"));
                                    suggest.setRequiredEmploymentStatus(rs.getString("RequiredEmploymentStatus"));
                                    suggest.setEligibilityReason(rs.getString("EligibilityReason"));
                                    suggest.setRequiredSchoolStatus(rs.getString("RequiredSchoolStatus"));

                                    // suggestListに追加
                                    suggestList.add(suggest);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("支援制度の取得中にエラーが発生しました: " + e.getMessage(), e);
        }

        return suggestList;
    }
}
