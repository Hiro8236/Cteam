package staff.normalstaff.calendar;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EventDao;

@WebServlet("/staff/normalstaff/calendar/delete")
public class EventDeleteAction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int eventId = Integer.parseInt(request.getParameter("eventId"));

            EventDao eventDao = new EventDao();
            eventDao.deleteEvent(eventId);

            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "イベント削除中にエラーが発生しました。");
        }
    }
}
