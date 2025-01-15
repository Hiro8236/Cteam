//package dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//import db.DBConnection;
//
//public class BookmarkDao {
//
//    // ブックマークを追加するメソッド
//    public boolean addBookmark(String emailAddress, String suggestinstitusionId) {
//        String query = "INSERT INTO bookmark (EmailAddress, SuggestInstitusionID) VALUES (?, ?)";
//
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement ps = conn.prepareStatement(query)) {
//            ps.setString(1, emailAddress);
//            ps.setString(2, suggestinstitusionId);
//
//            int rowsAffected = ps.executeUpdate();
//
//            return rowsAffected > 0;  // 成功した場合はtrueを返す
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;  // 失敗した場合はfalseを返す
//        }
//    }
//}
