package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Bookmark;

public class BookmarkListDao extends Dao {

    // おすすめ一覧からブックマーク一覧に移動するメソッド
    public void moveAllToBookmark() throws Exception {
        String selectSql = "SELECT id, name, detail FROM Institution";
        String insertSql = "INSERT INTO Bookmark (UserID, InstitutionID) VALUES (?, ?)";
        String deleteSql = "DELETE FROM Institution WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql);
             PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
             ResultSet rs = selectStatement.executeQuery()) {

            // トランザクション開始
            connection.setAutoCommit(false);

            while (rs.next()) {
                String name = rs.getString("name");
                String detail = rs.getString("detail");

                // Bookmarkテーブルにデータを挿入
                insertStatement.setString(1, name);
                insertStatement.setString(2, detail);
                insertStatement.executeUpdate();
            }

            // トランザクションコミット
            connection.commit();

        } catch (SQLException e) {
            // エラー発生時はロールバック
            try (Connection connection = getConnection()) {
                connection.rollback();
            }
            throw new Exception("データ移行に失敗しました", e);
        }
    }

    public List<Bookmark> getAll() throws Exception {
        List<Bookmark> bookmarklists = new ArrayList<>();
        String sql = "SELECT BookmarkID, UserID, InstitutionID, Institution.name, Institution.detail FROM Bookmark JOIN Institution ON Bookmark.InstitutionID = Institution.id";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Bookmark bookmark = new Bookmark();
                bookmark.setBookmarkID(resultSet.getInt("BookmarkID"));
                bookmark.setUserID(resultSet.getInt("UserID"));
                bookmark.setInstitutionID(resultSet.getInt("InstitutionID"));
                bookmark.setName(resultSet.getString("name"));
                bookmark.setDetail(resultSet.getString("detail"));
                bookmarklists.add(bookmark);
            }
        }

        return bookmarklists;
    }


}
