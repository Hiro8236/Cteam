package staff.normalstaff.calendar;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Event;
import dao.EventDao;

@WebServlet("/staff/normalstaff/calendar")
public class StaffCalendarAction extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // リクエストとレスポンスの文字コードを設定
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {
            // イベントデータを取得
            EventDao eventDao = new EventDao();
            List<Event> events = eventDao.getEvents();

            // データをリクエストスコープに設定
            request.setAttribute("events", events);

            // JSP に転送
            request.getRequestDispatcher("/staff/normalstaff/calendar/staff_calendar.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "イベントデータの取得中にエラーが発生しました。");
        }
    }
}
