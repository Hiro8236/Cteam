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

@WebServlet("/staff/normalstaff/calendar/create")
public class EventCreateAction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // フォームデータを取得
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String start = request.getParameter("start");
            String end = request.getParameter("end");

            // 仮の作成者ID（ログイン情報がある場合は変更）
            int createdBy = 1;

            // Event オブジェクトを作成
            Event event = new Event();
            event.setTitle(title);
            event.setDescription(description);
            event.setStartTime(Timestamp.valueOf(start));
            event.setEndTime(Timestamp.valueOf(end));
            event.setCreatedBy(createdBy);

            // データベースに登録
            EventDao eventDao = new EventDao();
            eventDao.createEvent(event);

            // 成功レスポンスを返す
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "イベント登録中にエラーが発生しました。");
        }
    }
}