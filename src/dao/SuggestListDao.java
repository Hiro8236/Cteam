package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Suggest;

public class SuggestListDao extends Dao {

    // ユーザーの情報に基づいて適用可能な支援制度を取得
    public List<Suggest> getByUserId(int userId) throws Exception {
        List<Suggest> suggestList = new ArrayList<>();

        String sql = "SELECT i.* " +
                "FROM institution i";


        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
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

                    suggestList.add(suggest);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("支援制度の取得中にエラーが発生しました: " + e.getMessage(), e);
        }

        return suggestList;
    }
}
