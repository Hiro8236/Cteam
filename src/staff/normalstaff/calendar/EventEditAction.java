package staff.normalstaff.calendar;

import java.io.IOException;
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
        try {
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String start = request.getParameter("start");
            String end = request.getParameter("end");

            Event event = new Event();
            event.setEventId(eventId);
            event.setTitle(title);
            event.setDescription(description);
            event.setStartTime(Timestamp.valueOf(start));
            event.setEndTime(Timestamp.valueOf(end));

            EventDao eventDao = new EventDao();
            eventDao.updateEvent(event);

            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "イベント更新中にエラーが発生しました。");
        }
    }
}
