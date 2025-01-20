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
        String selectSql = "SELECT id, name, detail FROM Suggest";
        String insertSql = "INSERT INTO Bookmark (name, detail) VALUES (?, ?)";
        String deleteSql = "DELETE FROM Suggest WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql);
             PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
             ResultSet rs = selectStatement.executeQuery()) {

            // トランザクション開始
            connection.setAutoCommit(false);

            while (rs.next()) {
                int suggestId = rs.getInt("id");
                String name = rs.getString("name");
                String detail = rs.getString("detail");

                // Bookmarkテーブルにデータを挿入
                insertStatement.setString(1, name);
                insertStatement.setString(2, detail);
                insertStatement.executeUpdate();

                // Suggestテーブルからデータを削除
                deleteStatement.setInt(1, suggestId);
                deleteStatement.executeUpdate();
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
        String sql = "SELECT id, name, detail FROM Bookmark";

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
}
