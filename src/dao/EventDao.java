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
        event.setEventID(rs.getInt("event_id"));
        event.setTitle(rs.getString("title"));
        event.setDescription(rs.getString("description"));
        event.setStartTime(rs.getTimestamp("start_time"));
        event.setEndTime(rs.getTimestamp("end_time"));
        event.setCreatedBy(rs.getInt("created_by"));

     // is_public と is_staff_only を boolean に変換して設定
        event.setPublic(rs.getInt("is_public") == 1); // 1ならtrue, それ以外はfalse
        event.setStaffOnly(rs.getInt("is_staff_only") == 1); // 1ならtrue, それ以外はfalse

        return event;
    }


    public void createEvent(Event event) throws Exception {
        String sql = "INSERT INTO events (title, description, start_time, end_time, created_by, is_public, is_staff_only) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
            stmt.executeUpdate();
            System.out.println("[成功] イベントがデータベースに登録されました。");
        } catch (SQLException e) {
            System.err.println("[エラー] データベース登録中にエラー: " + e.getMessage());
            throw e;
        }
    }


    // すべてのイベントの取得*使用非推奨
    public List<Event> getEvents() throws Exception {
    	String sql = "SELECT event_id, title, description, start_time, end_time, created_by, is_public, is_staff_only FROM events";
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
        String sql = "SELECT event_id, title, description, start_time, end_time, created_by, is_public, is_staff_only FROM events WHERE is_public = 1";
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
        String sql = "SELECT * FROM events WHERE created_by = ?";
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

    // イベントの削除
    public boolean deleteEvent(int eventID) throws Exception {
        String sql = "DELETE FROM events WHERE event_id = ?";
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
        String sql = "UPDATE events SET title = ?, description = ?, start_time = ?, end_time = ?, is_public = ?, is_staff_only = ? WHERE event_id = ?";
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


}
