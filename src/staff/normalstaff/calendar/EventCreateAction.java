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
            System.out.println("=== イベント登録処理開始 ===");

            // フォームデータを取得
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String start = request.getParameter("start");
            String end = request.getParameter("end");

            System.out.println("タイトル: " + title);
            System.out.println("説明: " + description);
            System.out.println("開始日時: " + start);
            System.out.println("終了日時: " + end);

            // 必須フィールドのチェック
            if (title == null || title.isEmpty() || start == null || start.isEmpty() || end == null || end.isEmpty()) {
                throw new IllegalArgumentException("必須フィールドが不足しています。");
            }

            // 仮の作成者ID（ログイン情報がある場合に変更）
            int createdBy = 1;

            // Eventオブジェクトを作成
            Event event = new Event();
            event.setTitle(title);
            event.setDescription(description);
            event.setStartTime(Timestamp.valueOf(start.replace("T", " ")));
            event.setEndTime(Timestamp.valueOf(end.replace("T", " ")));
            event.setCreatedBy(createdBy);

            // データベースに登録
            EventDao eventDao = new EventDao();
            eventDao.createEvent(event);

            // 成功ログ
            System.out.println("イベントが正常に登録されました。");

            // 成功レスポンス
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException e) {
            System.err.println("[エラー] 入力データが不正です: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            System.err.println("[エラー] サーバーエラー: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "イベント登録中にエラーが発生しました。");
        }
    }
}
