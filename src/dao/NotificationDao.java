package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Notification;

public class NotificationDao extends Dao {

    /**
     * データベースからすべてのおしらせを取得するメソッド。
     * @return おしらせのリスト。
     * @throws Exception データ取得中にエラーが発生した場合。
     */
    public List<Notification> getAll() throws Exception {
        List<Notification> notificationlists = new ArrayList<>();
        String sql = "SELECT NoticeID, Sentence, Title FROM notice";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Notification notificationlist = new Notification();
                notificationlist.setId(resultSet.getInt("NoticeID"));
                notificationlist.setTitle(resultSet.getString("Title"));
                notificationlist.setSentence(resultSet.getString("Sentence"));
                notificationlists.add(notificationlist);
            }
        }

        return notificationlists;
    }

    /**
     * 指定したお知らせIDに基づいてお知らせ情報を取得するメソッド。
     * @param NoticeId
     * @return お知らせオブジェクト、またはnull（見つからない場合）。
     * @throws Exception データ取得中にエラーが発生した場合。
     */
    public Notification getNotificationById(int Id) throws Exception {
        Notification notification = null;
        String sql = "SELECT * FROM notice WHERE NoticeID = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    notification = new Notification();
                    notification.setId(resultSet.getInt("NoticeID"));
                    notification.setTitle(resultSet.getString("Title"));
                    notification.setSentence(resultSet.getString("Sentence"));
                }
            }
        } catch (SQLException e) {
            throw new Exception("お知らせデータの取得中にエラーが発生しました。", e);
        }

        return notification;
    }

    /**
     * 新しいお知らせをデータベースに保存するメソッド。
     * @param title お知らせのタイトル。
     * @param sentence 内容。
     * @return 保存成功ならtrue、それ以外はfalse。
     * @throws Exception データ保存中にエラーが発生した場合。
     */
    public boolean saveNotification(String title, String sentence) throws Exception {
        String sql = "INSERT INTO Notice (title, sentence) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, sentence);

            // クエリの実行
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new Exception("お知らせデータの保存中にエラーが発生しました。", e);
        }
    }

    /**
     * 指定したお知らせIDのお知らせをデータベースから削除するメソッド。
     * @param NoticeId お知らせID。
     * @return 削除成功ならtrue、それ以外はfalse。
     * @throws Exception データ削除中にエラーが発生した場合。
     */
    public boolean deleteNotification(int NoticeId) throws Exception {
        String sql = "DELETE FROM notice WHERE NoticeID = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, NoticeId);

            // クエリの実行
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new Exception("お知らせデータの削除中にエラーが発生しました。", e);
        }
    }
}
