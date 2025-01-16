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

@WebServlet("/staff/normalstaff/calendar/create")
public class EventCreateAction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // フォームデータの取得
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        int createdBy = Integer.parseInt(request.getParameter("created_by"));

        // イベントオブジェクトの作成
        Event event = new Event();
        event.setTitle(title);
        event.setDescription(description);
        event.setStartTime(Timestamp.valueOf(start));
        event.setEndTime(Timestamp.valueOf(end));
        event.setCreatedBy(createdBy);

        try {
            // DAOを使用してイベントを登録
            EventDao eventDao = new EventDao();
            eventDao.createEvent(event);
            response.sendRedirect("/staff/normalstaff/calendar/");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "イベント登録中にエラーが発生しました。");
        }
    }
}
