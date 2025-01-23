package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Bookmark;

public class BookmarkListDao extends Dao {


	 public List<Bookmark> getAll() throws Exception {
	        List<Bookmark> bookmarklists = new ArrayList<>();
	        String sql = "SELECT BookmarkID, UserID, InstitutionID, Institution.name, Institution.detail "
	                     + "FROM Bookmark JOIN Institution ON Bookmark.InstitutionID = Institution.ID";

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
    // BookmarkIDでブックマークを検索するメソッド
    public Bookmark findByID(int bookmarkID) throws Exception {
        String sql = "SELECT BookmarkID, UserID, InstitutionID, Institution.name, Institution.detail "
                   + "FROM Bookmark JOIN Institution ON Bookmark.InstitutionID = Institution.ID "
                   + "WHERE BookmarkID = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bookmarkID);
            ResultSet resultSet = statement.executeQuery();

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
}
