package staff.normalstaff.calendar;

import java.sql.Timestamp;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Event;
import dao.EventDao;
import tool.Action;

@WebServlet("/staff/normalstaff/calendar/update")
public class EventUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        try {
            System.out.println("=== イベント更新処理開始 ===");

            // フォームデータからイベント情報を取得
            String eventIdStr = req.getParameter("eventId");
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            String start = req.getParameter("start");
            String end = req.getParameter("end");

            if (eventIdStr == null || eventIdStr.isEmpty()) {
                throw new IllegalArgumentException("イベントIDが指定されていません。");
            }

            int eventId = Integer.parseInt(eventIdStr);
            String formattedStart = start.replace("T", " ") + ":00";
            String formattedEnd = end.replace("T", " ") + ":00";

            System.out.println("更新対象イベントID: " + eventId);
            System.out.println("タイトル: " + title);
            System.out.println("説明: " + description);
            System.out.println("開始日時: " + formattedStart);
            System.out.println("終了日時: " + formattedEnd);

            // イベントオブジェクトを作成
            Event event = new Event();
            event.setEventId(eventId);
            event.setTitle(title);
            event.setDescription(description);
            event.setStartTime(Timestamp.valueOf(formattedStart));
            event.setEndTime(Timestamp.valueOf(formattedEnd));

            // イベントを更新
            EventDao eventDao = new EventDao();
            eventDao.updateEvent(event);

            // 成功ログ
            System.out.println("イベントが正常に更新されました: ID=" + eventId);

            // 成功レスポンス
            req.getRequestDispatcher("StaffCalendar.action").forward(req, res);
        } catch (IllegalArgumentException e) {
            System.err.println("[エラー] 入力データが不正です: " + e.getMessage());
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            System.err.println("[エラー] サーバーエラー: " + e.getMessage());
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "イベント更新中にエラーが発生しました。");
        }
    }
}
