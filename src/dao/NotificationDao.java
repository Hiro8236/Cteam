package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Notification;

public class NotificationDao extends Dao {

    // 全ての支援情報を取得
    public List<Notification> getAll() throws Exception {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT NotificationID, title, detail FROM Notification";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Notification notification = new Notification();
                notification.setNotificationID(resultSet.getInt("notificationID"));
                notification.setTitle(resultSet.getString("title"));
                notification.setDetail(resultSet.getString("detail"));
                notifications.add(notification);

            }
        }


        return notifications;
    }


    // 支援をデータベースに挿入し、自動生成されたIDを返す
    public int insert(Notification notification) throws Exception {
        String sql = "INSERT INTO notification (title, detail) VALUES (?, ?)";
        int generatedId = -1;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // パラメータを設定
            statement.setString(1, notification.getTitle());
            statement.setString(2, notification.getDetail());


            System.out.println(notification.getTitle());
            System.out.println(notification.getDetail());

            // 実行
            statement.executeUpdate();

            // 自動生成されたIDを取得
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return generatedId;
    }

    // 指定したIDの支援情報を削除
    public boolean deleteNotificationById(int id) throws Exception {
        boolean isDeleted = false;
        String sql = "DELETE FROM notification WHERE NotificationID = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            isDeleted = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isDeleted;
    }

    // 指定したIDの支援情報を更新
    public boolean updateNotification(Integer id, String name, String detail, String pdfPath) throws Exception {
        String sql = "UPDATE notification SET name = ?, detail = ?, PdfPath = ? WHERE NotificationID = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // パラメータを設定
            statement.setString(1, name);
            statement.setString(2, detail);
            statement.setString(3, pdfPath); // PdfPathを更新
            statement.setInt(4, id);

            // クエリを実行し、影響を受けた行数を取得
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // 更新成功の場合はtrue
        }
    }



    public List<String> getAllEmails() throws SQLException {
        List<String> emails = new ArrayList<>();
        String query = "SELECT EmailAddress FROM user"; // ユーザーのメールアドレスを取得するクエリ

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                emails.add(rs.getString("EmailAddress"));
            }
        } catch (SQLException e) {
            // SQLExceptionを処理
            e.printStackTrace();
        } catch (Exception e) {
            // その他の例外を処理
            e.printStackTrace();
        }
        return emails;
    }

}

