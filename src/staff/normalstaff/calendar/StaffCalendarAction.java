package staff.normalstaff.calendar;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Event;
import dao.EventDao;
import tool.Action;

public class StaffCalendarAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        EventDao eventDao = new EventDao();
        try {
            // イベントデータを取得
            List<Event> events = eventDao.getEvents();

            // イベントをリクエストスコープに設定
            request.setAttribute("events", events);
        } catch (Exception e) {
            // エラー処理
            request.setAttribute("error", "イベントデータの取得に失敗しました。");
            e.printStackTrace();
        }

        // JSPへ転送
        request.getRequestDispatcher("/staff/normalstaff/calendar/staff_calendar.jsp").forward(request, response);
    }
}
