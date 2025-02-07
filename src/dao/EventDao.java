package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.Event;

public class EventDao extends Dao {

	// ヘルパーメソッド: ResultSet から Event オブジェクトを作成
	private Event mapResultSetToEvent(ResultSet rs) throws SQLException {
	    Event event = new Event();
	    event.setEventID(rs.getInt("EventID"));
	    event.setTitle(rs.getString("title"));
	    event.setDescription(rs.getString("description"));
	    event.setStartTime(rs.getTimestamp("start_time"));
	    event.setEndTime(rs.getTimestamp("end_time"));
	    event.setCreatedBy(rs.getInt("created_by"));
	    event.setPublic(rs.getInt("is_public") == 1);
	    event.setStaffOnly(rs.getInt("is_staff_only") == 1);
	    event.setNotify(rs.getInt("notify") == 1);
	    return event;
	}

    public void createEvent(Event event) throws Exception {
        String sql = "INSERT INTO events (title, description, start_time, end_time, created_by, is_public, is_staff_only, notify) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        	// タイトル、説明、日時、作成者IDをセット
            stmt.setString(1, event.getTitle());
            stmt.setString(2, event.getDescription());
            stmt.setTimestamp(3, new Timestamp(event.getStartTime().getTime()));
            stmt.setTimestamp(4, new Timestamp(event.getEndTime().getTime()));
            stmt.setInt(5, event.getCreatedBy());

            // is_public と is_staff_only を boolean から int に変換してセット
            stmt.setInt(6, event.isPublic() ? 1 : 0);
            stmt.setInt(7, event.isStaffOnly() ? 1 : 0);
            stmt.setInt(8, event.isNotify() ? 1 : 0); // 通知設定を追加

            stmt.executeUpdate();
            System.out.println("[成功] イベントがデータベースに登録されました。");
        } catch (SQLException e) {
            System.err.println("[エラー] データベース登録中にエラー: " + e.getMessage());
            throw e;
        }
    }


    // すべてのイベントの取得*使用非推奨
    public List<Event> getEvents() throws Exception {
    	String sql = "SELECT EventID, title, description, start_time, end_time, created_by, is_public, is_staff_only FROM events";
        List<Event> events = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                events.add(mapResultSetToEvent(rs));
            }
        }
        return events;
    }

    // スタッフ用イベント取得
    public List<Event> getStaffEvents() throws Exception {
        String sql = "SELECT * FROM events WHERE is_staff_only = 1";
        List<Event> events = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                events.add(mapResultSetToEvent(rs));
            }
        }
        return events;
    }

    // 公開イベントの取得
    public List<Event> getPublicEvents() throws Exception {
        String sql = "SELECT * FROM events WHERE is_public = 1";
        List<Event> events = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                events.add(mapResultSetToEvent(rs));
            }
        }
        return events;
    }

    // 特定のユーザーが登録したイベントを取得
    public List<Event> getEventsByUserID(int userID) throws Exception {
    	String sql = "SELECT * FROM events WHERE Created_by = ? AND Is_staff_only = 0";
        List<Event> events = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    events.add(mapResultSetToEvent(rs));
                }
            }
        }
        return events;
    }

    //スタッフ用の削除、更新

    // イベントの削除
    public boolean deleteEvent(int eventID) throws Exception {
        String sql = "DELETE FROM events WHERE EventID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventID);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("削除された行数: " + rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("[エラー] イベント削除中にエラー: " + e.getMessage());
            throw e;
        }
    }

    // イベントの更新
    public void updateEvent(Event event) throws Exception {
        String sql = "UPDATE events SET title = ?, description = ?, start_time = ?, end_time = ?, is_public = ?, is_staff_only = ? WHERE EventID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 各プレースホルダーに値を設定
            stmt.setString(1, event.getTitle());
            stmt.setString(2, event.getDescription());
            stmt.setTimestamp(3, new java.sql.Timestamp(event.getStartTime().getTime()));
            stmt.setTimestamp(4, new java.sql.Timestamp(event.getEndTime().getTime()));
            stmt.setInt(5, event.isPublic() ? 1 : 0); // boolean を int に変換
            stmt.setInt(6, event.isStaffOnly() ? 1 : 0); // boolean を int に変換
            stmt.setInt(7, event.getEventID()); // WHERE句で使用するID

            // デバッグログ: 入力値を確認
            System.out.println("[DEBUG] 更新処理対象のイベント情報:");
            System.out.println("  - イベントID: " + event.getEventID());
            System.out.println("  - タイトル: " + event.getTitle());
            System.out.println("  - 説明: " + event.getDescription());
            System.out.println("  - 開始日時: " + event.getStartTime());
            System.out.println("  - 終了日時: " + event.getEndTime());
            System.out.println("  - 公開設定: " + event.isPublic());
            System.out.println("  - スタッフ限定設定: " + event.isStaffOnly());
            System.out.println("[DEBUG] 実行するSQL: " + stmt.toString());

            // クエリ実行
            int rowsUpdated = stmt.executeUpdate();

            // 更新結果ログ
            if (rowsUpdated > 0) {
                System.out.println("[成功] イベントが正常に更新されました: ID=" + event.getEventID());
            } else {
                System.out.println("[警告] 更新対象のイベントが見つかりませんでした: ID=" + event.getEventID());
            }

        } catch (SQLException e) {
            // エラー処理
            System.err.println("[エラー] イベント更新中にエラーが発生しました。");
            System.err.println("  - エラー詳細: " + e.getMessage());
            System.err.println("  - SQLステート: " + e.getSQLState());
            System.err.println("  - エラーコード: " + e.getErrorCode());
            throw new Exception("[エラー] イベント更新中にエラーが発生しました。詳細: " + e.getMessage(), e);
        }
    }

    //ユーザー用の削除、更新

	// ユーザー用の更新
    public void updateEventForUser(Event event) throws Exception {
        String sql = "UPDATE events SET title = ?, description = ?, start_time = ?, end_time = ?, notify = ? WHERE eventID = ? AND created_by = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 各プレースホルダーに値を設定
            stmt.setString(1, event.getTitle());
            stmt.setString(2, event.getDescription());
            stmt.setTimestamp(3, new java.sql.Timestamp(event.getStartTime().getTime()));
            stmt.setTimestamp(4, new java.sql.Timestamp(event.getEndTime().getTime()));
            stmt.setInt(5, event.isNotify() ? 1 : 0); // 通知設定を更新
            stmt.setInt(6, event.getEventID()); // イベントID
            stmt.setInt(7, event.getCreatedBy()); // 作成者（本人）であることを確認

            // クエリ実行
            int rowsUpdated = stmt.executeUpdate();

            // 更新結果ログ
            if (rowsUpdated > 0) {
                System.out.println("[成功] ユーザーイベントが正常に更新されました: ID=" + event.getEventID());
            } else {
                throw new Exception("[エラー] 更新対象のイベントが見つかりませんでした。ID=" + event.getEventID());
            }

        } catch (SQLException e) {
            // エラー処理
            System.err.println("[エラー] ユーザーイベント更新中にエラーが発生しました。");
            System.err.println("  - エラー詳細: " + e.getMessage());
            System.err.println("  - SQLステート: " + e.getSQLState());
            System.err.println("  - エラーコード: " + e.getErrorCode());
            throw new Exception("[エラー] ユーザーイベント更新中にエラーが発生しました。詳細: " + e.getMessage(), e);
        }
    }


    //ユーザー用の削除
    public void deleteEventForUser(int eventID, int userID) throws Exception {
        String sql = "DELETE FROM events WHERE EventID= ? AND created_by = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 各プレースホルダーに値を設定
            stmt.setInt(1, eventID);
            stmt.setInt(2, userID);

            // クエリ実行
            int rowsDeleted = stmt.executeUpdate();

            // 削除結果ログ
            if (rowsDeleted > 0) {
                System.out.println("[成功] ユーザーイベントが削除されました: ID=" + eventID);
            } else {
                throw new Exception("[エラー] 削除対象のイベントが見つかりませんでした: ID=" + eventID);
            }

        } catch (SQLException e) {
            // エラー処理
            System.err.println("[エラー] ユーザーイベント削除中にエラーが発生しました。");
            System.err.println("  - エラー詳細: " + e.getMessage());
            System.err.println("  - SQLステート: " + e.getSQLState());
            System.err.println("  - エラーコード: " + e.getErrorCode());
            throw new Exception("[エラー] ユーザーイベント削除中にエラーが発生しました。詳細: " + e.getMessage(), e);
        }
    }

    //お知らせ送信用、notify=1を取得
    public List<Event> getUpcomingNotifiedEvents() throws Exception {
        String sql = "SELECT e.*, u.EmailAddress FROM events e " +  // 修正: email → EmailAddress
                     "JOIN `user` u ON e.created_by = u.UserID " +  // 修正: user をバッククォートで囲む
                     "WHERE e.notify = 1 " +
                     "AND TIMESTAMPDIFF(HOUR, NOW(), e.start_time) BETWEEN 0 AND 24";

        List<Event> events = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Event event = mapResultSetToEvent(rs);
                event.setEmail(rs.getString("EmailAddress")); // 修正: email → EmailAddress
                events.add(event);
            }
        }
        return events;
    }

}