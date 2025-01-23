package user.calendar;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Event;
import dao.EventDao;
import tool.Action;

@WebServlet("/user/calendar/delete")
public class EventDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            System.out.println("=== イベント削除処理開始 ===");

            // パラメータを取得
            int eventID = Integer.parseInt(req.getParameter("eventID"));
            Integer userID = (Integer) req.getSession().getAttribute("userID"); // ログイン中のユーザーID

            if (userID == null) {
                throw new IllegalArgumentException("ログインユーザーが特定できません。");
            }

            // DAOを呼び出して削除処理を実行
            EventDao eventDao = new EventDao();
            eventDao.deleteEventForUser(eventID, userID);

            System.out.println("[成功] イベントが削除されました: ID=" + eventID);

            // 成功レスポンス
            req.getRequestDispatcher("UserCalendar.action").forward(req, res);

        } catch (IllegalArgumentException e) {
            System.err.println("[エラー] 入力データエラー: " + e.getMessage());
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            System.err.println("[エラー] サーバーエラー: " + e.getMessage());
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "イベント削除中にエラーが発生しました。");
        }
    }
}
