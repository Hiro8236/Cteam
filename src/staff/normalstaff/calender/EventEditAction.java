package staff.normalstaff.calender;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Event;
import dao.EventDao;

@WebServlet("/staff/normalstaff/calendar/edit")
public class EventEditAction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // フォームデータの取得
        int eventId = Integer.parseInt(request.getParameter("event_id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String start = request.getParameter("start");
        String end = request.getParameter("end");

        // イベントオブジェクトの更新
        Event event = new Event();
        event.setEventId(eventId);
        event.setTitle(title);
        event.setDescription(description);
        event.setStartTime(Timestamp.valueOf(start));
        event.setEndTime(Timestamp.valueOf(end));

        try {
            // DAOを使用してイベントを更新
            EventDao eventDao = new EventDao();
            eventDao.updateEvent(event);
            response.sendRedirect("/staff/normalstaff/calendar/");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "イベント更新中にエラーが発生しました。");
        }
    }
}
