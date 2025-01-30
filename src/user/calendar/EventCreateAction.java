package user.calendar;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Event;
import dao.EventDao;
import tool.Action;

@WebServlet("/user/calendar/create")
public class EventCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            System.out.println("=== イベント登録処理開始 ===");

            // フォームデータを取得
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            String start = req.getParameter("start");
            String end = req.getParameter("end");
            boolean notify = req.getParameter("notification") != null;  // 通知設定

            Integer userID = (Integer) req.getSession().getAttribute("userID");
            System.out.println("[DEBUG] userID: " + userID);

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

            // 日付範囲のチェック
            if (java.sql.Timestamp.valueOf(formattedStart).after(java.sql.Timestamp.valueOf(formattedEnd))) {
                throw new IllegalArgumentException("開始日は終了日より前に設定してください。");
            }

            // Event オブジェクトを作成
            Event event = new Event();
            event.setTitle(title);
            event.setDescription(description);
            event.setStartTime(java.sql.Timestamp.valueOf(formattedStart));
            event.setEndTime(java.sql.Timestamp.valueOf(formattedEnd));
            event.setCreatedBy(userID);
            event.setNotify(notify); // 通知設定を保存

            System.out.println("[DEBUG] イベントデータ: " + event);

            // データベースに登録
            EventDao eventDao = new EventDao();
            eventDao.createEvent(event);

            System.out.println("[成功] イベントが登録されました。");

            // 成功レスポンス
            res.sendRedirect("UserCalendar.action");

        } catch (IllegalArgumentException e) {
            System.err.println("[エラー] 入力データエラー: " + e.getMessage());
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "入力内容に誤りがあります: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[エラー] サーバーエラー: " + e.getMessage());
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "イベント登録中にエラーが発生しました。");
        }
    }
}
