package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Bookmark;

public class BookmarkListDao extends Dao{
	public List<Bookmark> getAll() throws Exception {
	    List<Bookmark> bookmarklists = new ArrayList<>();
	    String sql = "SELECT id, name, detail FROM bookmark";

	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql);
	         ResultSet resultSet = statement.executeQuery()) {

	        while (resultSet.next()) {
	            Bookmark bookmarklist = new Bookmark();
	            bookmarklist.setId(resultSet.getInt("id"));
	            bookmarklist.setName(resultSet.getString("name"));
	            bookmarklist.setDetail(resultSet.getString("detail"));
	            bookmarklists.add(bookmarklist);
	        }
	    }

	    return bookmarklists;
	}

	// DAO: 新規登録メソッド
	public void insert(Bookmark bookmark) throws Exception {
	    String sql = "INSERT INTO bookmark (name, detail) VALUES (?, ?)";

	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {

	        // プレースホルダーにデータをセット
	        statement.setString(1, bookmark.getName());
	        statement.setString(2, bookmark.getDetail());

	        // クエリ実行
	        statement.executeUpdate();
	    }
	}


}