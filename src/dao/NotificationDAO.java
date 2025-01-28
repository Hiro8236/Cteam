package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Notification;

public class NotificationDAO extends Dao{
	//すべてのお知らせを取得するメソッド
	public List<Notification> getAll() throws Exception {
        List<Notification> NotificationLists = new ArrayList<>();
        String sql = "SELECT NoticeID,Title ,Sentence ";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Notification notification = new Notification();
                notification.setNoticeid(resultSet.getInt("NoticeID"));
                notification.setTitle(resultSet.getString("Title"));
                notification.setSenrence(resultSet.getString("Sentence"));
                NotificationLists.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("お知らせリストの取得中にエラーが発生しました: " + e.getMessage());
        }

        return NotificationLists;
    }
	// お知らせをデータベースに挿入するメソッド
    public void insert(Integer NoticeId) throws Exception {
        // SQLのINSERT文
        String sql = "INSERT INTO Notice (user_id) VALUES ( ?)";

        // データベース接続
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // パラメータの設定
            statement.setInt(1, NoticeId);

            // 実行
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // エラーが発生した場合
            throw new Exception("お知らせの挿入中にエラーが発生しました: " + e.getMessage());
        }
    }



}