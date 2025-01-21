package staff.normalstaff.calendar;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Event;
import dao.EventDao;
import tool.Action;

@WebServlet("/staff/normalstaff/calendar/update")
public class EventUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            System.out.println("=== イベント更新処理開始 ===");

            // フォームデータを取得
            String eventIdStr = req.getParameter("eventId");
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            String start = req.getParameter("start");
            String end = req.getParameter("end");
            String isPublicStr = req.getParameter("isPublic");
            String isStaffOnlyStr = req.getParameter("isStaffOnly");

            // 必須フィールドのチェック
            if (eventIdStr == null || eventIdStr.isEmpty()) {
                throw new IllegalArgumentException("イベントIDが指定されていません。");
            }

            int eventId = Integer.parseInt(eventIdStr);
            boolean isPublic = isPublicStr != null && isPublicStr.equals("on");
            boolean isStaffOnly = isStaffOnlyStr != null && isStaffOnlyStr.equals("on");

            String restart = start.replace("T", " ") + ":00";
            String reend = end.replace("T", " ") + ":00";

            // Eventオブジェクトを作成
            Event event = new Event();
            event.setEventId(eventId); // イベントIDを設定
            event.setTitle(title);
            event.setDescription(description);
            event.setStartTime(java.sql.Timestamp.valueOf(restart));
            event.setEndTime(java.sql.Timestamp.valueOf(reend));
            String visibility = req.getParameter("visibility");
            event.setPublic("public".equals(visibility));
            event.setStaffOnly("staffOnly".equals(visibility));

            // デバッグ用ログ
            System.out.println("更新対象イベントの情報:");
            System.out.println("  - イベントID: " + eventId);
            System.out.println("  - タイトル: " + title);
            System.out.println("  - 説明: " + description);
            System.out.println("  - 開始日時: " + restart);
            System.out.println("  - 終了日時: " + reend);
            System.out.println("  - 公開設定 (isPublic): " + isPublic);
            System.out.println("  - スタッフ限定設定 (isStaffOnly): " + isStaffOnly);

            // データベースを更新
            EventDao eventDao = new EventDao();
            eventDao.updateEvent(event);

            // 成功レスポンス
            System.out.println("イベントが正常に更新されました: ID=" + eventId);
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
