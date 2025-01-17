package staff.normalstaff.calendar;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Event;
import dao.EventDao;
import tool.Action;

public class StaffCalendarAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        try {
            // DAOを使用してイベントデータを取得
            EventDao eventDao = new EventDao();
            List<Event> events = eventDao.getEvents();

            // イベントデータをリクエストスコープに設定
            req.setAttribute("events", events);

            // staff_calendar.jsp にフォワード
            req.getRequestDispatcher("staff_calendar.jsp").forward(req, res);

        } catch (SQLException e) {
            // 例外が発生した場合、エラー情報をリクエストスコープに設定しエラーページへ
            e.printStackTrace();
            req.setAttribute("error", "イベントデータの取得中にエラーが発生しました。");
            req.getRequestDispatcher("error.jsp").forward(req, res);
        }
    }
}
