package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.Event;

public class EventDao extends Dao{
    // イベントの作成
    public void createEvent(Event event) throws Exception {
        String sql = "INSERT INTO events (title, description, start_time, end_time, created_by) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getTitle());
            stmt.setString(2, event.getDescription());
            stmt.setTimestamp(3, new Timestamp(event.getStartTime().getTime()));
            stmt.setTimestamp(4, new Timestamp(event.getEndTime().getTime()));
            stmt.setInt(5, event.getCreatedBy());
            stmt.executeUpdate();
            System.out.println("[成功] イベントがデータベースに登録されました。");
        } catch (SQLException e) {
            System.err.println("[エラー] データベース登録中にエラー: " + e.getMessage());
            throw e;
        }
    }


    // イベントの取得
    public List<Event> getEvents() throws Exception {
    	String sql = "SELECT event_id, title, description, start_time, end_time, created_by FROM events";
        List<Event> events = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getInt("event_id"));
                event.setTitle(rs.getString("title"));
                event.setDescription(rs.getString("description"));
                event.setStartTime(rs.getTimestamp("start_time"));
                event.setEndTime(rs.getTimestamp("end_time"));
                event.setCreatedBy(rs.getInt("created_by"));
                events.add(event);
            }
        }
        return events;
    }


    /**
     * 単一のイベントを削除する
     *
     * @param eventId 削除するイベントID
     * @return 成功時true、失敗時false
     * @throws Exception
     */
    public boolean deleteEvent(int eventId) throws Exception {
        String sql = "DELETE FROM events WHERE event_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // SQL パラメータを設定
            pstmt.setInt(1, eventId);

            // デバッグログ
            System.out.println("イベント削除のSQL: " + pstmt.toString());

            // クエリ実行し、削除された行があるかどうかを返す
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("削除された行数: " + rowsAffected);

            return rowsAffected > 0; // 削除が成功した場合はtrue
        } catch (SQLException e) {
            // SQL エラーの詳細ログ
            System.err.println("SQLエラー: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("エラーコード: " + e.getErrorCode());
            throw new Exception("イベント削除中にエラーが発生しました: " + e.getMessage(), e);
        }
    }


 // イベントの更新
    public void updateEvent(Event event) throws Exception {
        String sql = "UPDATE events SET title = ?, description = ?, start_time = ?, end_time = ? WHERE event_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 各プレースホルダーに値を設定
            stmt.setString(1, event.getTitle());
            stmt.setString(2, event.getDescription());
            stmt.setTimestamp(3, new java.sql.Timestamp(event.getStartTime().getTime()));
            stmt.setTimestamp(4, new java.sql.Timestamp(event.getEndTime().getTime()));
            stmt.setInt(5, event.getEventId()); // WHERE句で使用するID

            // 更新クエリを実行
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("[成功] イベントが正常に更新されました: ID=" + event.getEventId());
            } else {
                System.out.println("[警告] 更新対象のイベントが見つかりませんでした: ID=" + event.getEventId());
            }
        } catch (SQLException e) {
            System.err.println("[エラー] データベース更新中にエラー: " + e.getMessage());
            throw e;
        }
    }

}
