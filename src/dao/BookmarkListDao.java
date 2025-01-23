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
    public void moveAllToBookmark(int userID) throws Exception {
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
                int institutionID = rs.getInt("id");
                // Bookmarkテーブルにデータを挿入
                insertStatement.setInt(1, userID);  // ユーザーID
                insertStatement.setInt(2, institutionID);  // InstitutionID
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

    // ブックマーク一覧を取得するメソッド
    public List<Bookmark> getAll() throws Exception {
        List<Bookmark> bookmarklists = new ArrayList<>();
        String sql = "SELECT BookmarkID, UserID, InstitutionID, Institution.name, Institution.detail FROM Bookmark JOIN Institution ON Bookmark.InstitutionID = Institution.ID";

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

    // IDでブックマークを検索するメソッド
    public Bookmark findByID(int bookmarkID) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Bookmark bookmark = null;

        try {
            // 指定されたBookmarkIDで検索
            statement = connection.prepareStatement("SELECT * FROM Bookmark WHERE BookmarkID = ?");
            statement.setInt(1, bookmarkID);
            resultSet = statement.executeQuery();

            // 結果があればBookmarkオブジェクトを作成
            if (resultSet.next()) {
                bookmark = new Bookmark();
                bookmark.setBookmarkID(resultSet.getInt("BookmarkID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        return bookmark;  // 該当するブックマークが見つからなければnull
    }


    // ブックマークを削除するメソッド
    public boolean delete(Bookmark bookmark) throws Exception {
        String sql = "DELETE FROM Bookmark WHERE BookmarkID = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bookmark.getBookmarkID());
            int count = statement.executeUpdate();

            return count > 0;
        } catch (SQLException e) {
            throw new Exception("削除に失敗しました", e);
        }
    }
}
