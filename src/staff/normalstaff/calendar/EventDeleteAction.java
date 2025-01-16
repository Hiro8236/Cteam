package staff.normalstaff.calendar;

import java.io.IOException;
import java.sql.SQLException;

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
        // フォームデータの取得
        int eventId = Integer.parseInt(request.getParameter("event_id"));

        try {
            // DAOを使用してイベントを削除
            EventDao eventDao = new EventDao();
            eventDao.deleteEvent(eventId);
            response.sendRedirect("/staff/normalstaff/calendar/");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "イベント削除中にエラーが発生しました。");
        }
    }
}
