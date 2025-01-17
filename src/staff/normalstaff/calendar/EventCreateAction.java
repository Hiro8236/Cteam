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

            // ログ出力（デバッグ用）
            System.out.println("受信したデータ:");
            System.out.println("タイトル: " + title);
            System.out.println("説明: " + description);
            System.out.println("開始日時: " + start);
            System.out.println("終了日時: " + end);

            // 仮の作成者ID
            int createdBy = 1;

            // 日付のパース確認
            try {
                System.out.println("パース開始日時: " + Timestamp.valueOf(start));
                System.out.println("パース終了日時: " + Timestamp.valueOf(end));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new ServletException("日付フォーマットが無効です: " + start + ", " + end, e);
            }

            // Event オブジェクト作成
            Event event = new Event();
            event.setTitle(title);
            event.setDescription(description);
            event.setStartTime(Timestamp.valueOf(start));
            event.setEndTime(Timestamp.valueOf(end));
            event.setCreatedBy(createdBy);

            // データベースに登録
            EventDao eventDao = new EventDao();
            System.out.println("データベース登録クエリ開始");
            eventDao.createEvent(event);
            System.out.println("データベース登録完了");

            // 成功レスポンスを返す
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "イベント登録中にエラーが発生しました。");
        }
    }
}
