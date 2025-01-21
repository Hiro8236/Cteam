package user.calendar;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Event;
import dao.EventDao;
import tool.Action;

public class UserCalendarAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            EventDao eventDao = new EventDao();
            List<Event> events = new ArrayList<>();

            // ログイン状態を確認
            Integer userId = (Integer) req.getSession().getAttribute("userId");

            if (userId == null) {
                // ログインしていない場合は公開イベントのみ取得
                events = eventDao.getPublicEvents();
            } else {
                // ログインしている場合は公開イベントとユーザー自身のイベントを取得
                events.addAll(eventDao.getPublicEvents());
                events.addAll(eventDao.getEventsByUserId(userId));
            }

            // イベントデータをリクエストスコープに設定
            req.setAttribute("events", events);

            // user_calendar.jsp にフォワード
            req.getRequestDispatcher("user_calendar.jsp").forward(req, res);

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "イベントデータの取得中にエラーが発生しました。");
            req.getRequestDispatcher("error.jsp").forward(req, res);
        }
    }
}
