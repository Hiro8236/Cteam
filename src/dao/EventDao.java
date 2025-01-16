package dao;

import bean.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDao extends Dao {

    // イベントの作成
    public void createEvent(Event event) throws SQLException {
        String sql = "INSERT INTO events (title, description, start_time, end_time, created_by) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getTitle());
            stmt.setString(2, event.getDescription());
            stmt.setTimestamp(3, new Timestamp(event.getStartTime().getTime()));
            stmt.setTimestamp(4, new Timestamp(event.getEndTime().getTime()));
            stmt.setInt(5, event.getCreatedBy());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new SQLException("イベントの作成中にエラーが発生しました: " + e.getMessage(), e);
        }
    }

    // イベントの取得
    public List<Event> getEvents() throws SQLException {
        String sql = "SELECT * FROM events";
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
        } catch (Exception e) {
            throw new SQLException("イベントの取得中にエラーが発生しました: " + e.getMessage(), e);
        }
        return events;
    }

    // イベントの編集
    public void updateEvent(Event event) throws SQLException {
        String sql = "UPDATE events SET title = ?, description = ?, start_time = ?, end_time = ? WHERE event_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getTitle());
            stmt.setString(2, event.getDescription());
            stmt.setTimestamp(3, new Timestamp(event.getStartTime().getTime()));
            stmt.setTimestamp(4, new Timestamp(event.getEndTime().getTime()));
            stmt.setInt(5, event.getEventId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new SQLException("イベントの更新中にエラーが発生しました: " + e.getMessage(), e);
        }
    }

    // イベントの削除
    public void deleteEvent(int eventId) throws SQLException {
        String sql = "DELETE FROM events WHERE event_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new SQLException("イベントの削除中にエラーが発生しました: " + e.getMessage(), e);
        }
    }
}
