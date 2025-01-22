package staff.normalstaff.calendar;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EventDao;
import tool.Action;

@WebServlet("/staff/normalstaff/calendar/delete")
public class EventDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        try {
            System.out.println("=== イベント削除処理開始 ===");

            // フォームデータからイベントIDを取得
            String eventIdStr = req.getParameter("eventID");
            if (eventIdStr == null || eventIdStr.isEmpty()) {
                throw new IllegalArgumentException("イベントIDが指定されていません。");
            }

            int eventID = Integer.parseInt(eventIdStr);

            // イベントを削除
            EventDao eventDao = new EventDao();
            eventDao.deleteEvent(eventID);

            // 成功ログ
            System.out.println("イベントが正常に削除されました: ID=" + eventID);

            // 成功レスポンス
            req.getRequestDispatcher("StaffCalendar.action").forward(req, res);
        } catch (IllegalArgumentException e) {
            System.err.println("[エラー] 入力データが不正です: " + e.getMessage());
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            System.err.println("[エラー] サーバーエラー: " + e.getMessage());
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "イベント削除中にエラーが発生しました。");
        }
    }
}