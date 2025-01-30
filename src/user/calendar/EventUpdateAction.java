package user.calendar;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Event;
import dao.EventDao;
import tool.Action;

@WebServlet("/user/calendar/update")
public class EventUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            System.out.println("=== イベント更新処理開始 ===");

            // フォームデータを取得
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            String start = req.getParameter("start");
            String end = req.getParameter("end");
            int eventID = Integer.parseInt(req.getParameter("eventID"));
            boolean notify = req.getParameter("notification") != null;  // 通知設定（チェックボックスの値）

            Integer userID = (Integer) req.getSession().getAttribute("userID"); // ログイン中のユーザーID

            if (userID == null) {
                throw new IllegalArgumentException("ログインユーザーが特定できません。");
            }

            // 必須フィールドのチェック
            if (title == null || title.isEmpty() || start == null || start.isEmpty() || end == null || end.isEmpty()) {
                throw new IllegalArgumentException("必須フィールドが不足しています。");
            }

            // 日付のフォーマット修正
            String formattedStart = start.replace("T", " ") + ":00";
            String formattedEnd = end.replace("T", " ") + ":00";

            // Event オブジェクトを作成
            Event event = new Event();
            event.setEventID(eventID);
            event.setTitle(title);
            event.setDescription(description);
            event.setStartTime(java.sql.Timestamp.valueOf(formattedStart));
            event.setEndTime(java.sql.Timestamp.valueOf(formattedEnd));
            event.setCreatedBy(userID);
            event.setNotify(notify); // 通知設定を保存

            System.out.println("[DEBUG] 更新イベントデータ: " + event);

            // データベースを更新
            EventDao eventDao = new EventDao();
            eventDao.updateEventForUser(event);

            System.out.println("[成功] イベントが更新されました。");

            // 成功レスポンス
            req.getRequestDispatcher("UserCalendar.action").forward(req, res);

        } catch (IllegalArgumentException e) {
            System.err.println("[エラー] 入力データエラー: " + e.getMessage());
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            System.err.println("[エラー] サーバーエラー: " + e.getMessage());
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "イベント更新中にエラーが発生しました。");
        }
    }
}
