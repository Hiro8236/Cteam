package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Notification;

public class NotificationDao extends Dao{

	/**
     * データベースからすべてのおしらせを取得するメソッド。
     * @return おしらせのリスト。
     * @throws Exception データ取得中にエラーが発生した場合。
     */
    public List<Notification> getNotification() throws Exception {
        List<Notification> notifications = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // SQLクエリの準備
            statement = connection.prepareStatement("SELECT * FROM Notice ORDER BY upload_date DESC");
            ResultSet resultSet = statement.executeQuery();

            // 結果セットをお知らせオブジェクトに変換してリストに追加
            while (resultSet.next()) {
                Notification notification= new Notification();
                notification.setTitle(resultSet.getString("TITLE"));
                notification.setId(resultSet.getInt("ID"));
                notification.setSentence(resultSet.getString("Sentence"));
                notifications.add(notification);
            }

        } catch (SQLException e) {
            throw new Exception("お知らせデータの取得中にエラーが発生しました。", e);
        } finally {
            // リソースのクリーンアップ
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

        return notifications;

}
    /**
     * 指定したお知らせIDに基づいてお知らせ情報を取得するメソッド。
     * @param NoticeId 動画ID。
     * @return お知らせオブジェクト、またはnull（見つからない場合）。
     * @throws Exception データ取得中にエラーが発生した場合。
     */
    public Notification getnotificationById(int Id) throws Exception {
        Notification Notification = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // SQLクエリの準備
            statement = connection.prepareStatement("SELECT * FROM Notice WHERE NoticeID = ?");
            statement.setInt(1, Id);
            ResultSet resultSet = statement.executeQuery();

            // 結果セットからお知らせオブジェクトを作成
            if (resultSet.next()) {
                Notification = new Notification();
                Notification.setId(resultSet.getInt("Id"));
                Notification.setTitle(resultSet.getString("TITLE"));
                Notification.setSentence(resultSet.getString("Sentence"));
            }

        } catch (SQLException e) {
            throw new Exception("お知らせデータの取得中にエラーが発生しました。", e);
        } finally {
            // リソースのクリーンアップ
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

        return Notification;
    }

    /**
     * 新しいお知らせをデータベースに保存するメソッド。
     * @param title お知らせのタイトル。
     * @param　Sentence 内容。
     * @param datetime。
     * @return 保存成功ならtrue、それ以外はfalse。
     * @throws Exception データ保存中にエラーが発生した場合。
     */
    public boolean saveNotification(String title, String sentence) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // SQLクエリの準備
            statement = connection.prepareStatement(
                "INSERT INTO Notice (title, sentence) VALUES (?, ?, )"
            );
            statement.setString(1, title);
            statement.setString(2, sentence);

            // クエリの実行
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new Exception("お知らせデータの保存中にエラーが発生しました。", e);
        } finally {
            // リソースのクリーンアップ
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
    }
    /**
     * 指定した動画IDの動画をデータベースから削除するメソッド。
     * @param videoId 動画ID。
     * @return 削除成功ならtrue、それ以外はfalse。
     * @throws Exception データ削除中にエラーが発生した場合。
     */
    public boolean deleteNotification(int NoticeId) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // SQLクエリの準備
            statement = connection.prepareStatement("DELETE FROM videos WHERE NoticceId= ?");
            statement.setInt(1, NoticeId);

            // クエリの実行
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new Exception("お知らせデータの削除中にエラーが発生しました。", e);
        } finally {
            // リソースのクリーンアップ
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
    }
}


