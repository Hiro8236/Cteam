package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Bookmark;

public class BookmarkListDao extends Dao {

    // すべてのブックマークを取得するメソッド
    public List<Bookmark> getAll() throws Exception {
        List<Bookmark> bookmarkLists = new ArrayList<>();
        String sql = "SELECT Bookmark.BookmarkID, Bookmark.UserID, Bookmark.InstitutionID, Institution.name, Institution.detail "
                     + "FROM Bookmark "
                     + "JOIN Institution ON Bookmark.InstitutionID = Institution.InstitutionID";

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
                bookmarkLists.add(bookmark);
            }
        } catch (SQLException e) {
            throw new Exception("ブックマークの取得に失敗しました", e);
        }

        return bookmarkLists;
    }

    // BookmarkIDでブックマークを検索するメソッド
    public Bookmark findByID(int bookmarkID) throws Exception {
        String sql = "SELECT Bookmark.BookmarkID, Bookmark.UserID, Bookmark.InstitutionID, Institution.name, Institution.detail "
                   + "FROM Bookmark "
                   + "JOIN Institution ON Bookmark.InstitutionID = Institution.InstitutionID "
                   + "WHERE Bookmark.BookmarkID = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bookmarkID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Bookmark bookmark = new Bookmark();
                    bookmark.setBookmarkID(resultSet.getInt("BookmarkID"));
                    bookmark.setUserID(resultSet.getInt("UserID"));
                    bookmark.setInstitutionID(resultSet.getInt("InstitutionID"));
                    bookmark.setName(resultSet.getString("name"));
                    bookmark.setDetail(resultSet.getString("detail"));
                    return bookmark;
                }
            }
        } catch (SQLException e) {
            throw new Exception("ブックマークの検索に失敗しました", e);
        }

        return null;  // 該当するブックマークが見つからない場合はnullを返す
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

    // ブックマークを挿入するメソッド
    public boolean insert(Bookmark bookmark) throws Exception {
        String sql = "INSERT INTO Bookmark (UserID, InstitutionID) VALUES (?, ?)";  // 必要なカラムを指定

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // パラメータを設定
            statement.setInt(1, bookmark.getUserID());
            statement.setInt(2, bookmark.getInstitutionID());

            // クエリの実行
            int count = statement.executeUpdate();

            // 挿入が成功したかを返す
            return count > 0;
        } catch (SQLException e) {
            throw new Exception("ブックマークの挿入に失敗しました", e);
        }
    }
}
