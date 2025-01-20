package staff.normalstaff.calendar;

import java.sql.Timestamp;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Event;
import dao.EventDao;
import tool.Action;

@WebServlet("/staff/normalstaff/calendar/create")
public class EventCreateAction extends Action{

	@Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        try {
            System.out.println("=== イベント登録処理開始 ===");

            // フォームデータを取得
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            String start = req.getParameter("start");
            String end = req.getParameter("end");

            String restart = start.replace("T", " ") + ":00";
            String reend= end.replace("T", " ") + ":00";

            System.out.println("タイトル: " + title);
            System.out.println("説明: " + description);
            System.out.println("開始日時: " + restart);
            System.out.println("終了日時: " + reend);

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
            event.setStartTime(Timestamp.valueOf(restart));
            System.out.println("2");
            event.setEndTime(Timestamp.valueOf(reend));
            System.out.println("3");
            event.setCreatedBy(createdBy);
            System.out.println("4");

            // データベースに登録
            EventDao eventDao = new EventDao();
            eventDao.createEvent(event);

            // 成功ログ
            System.out.println("イベントが正常に登録されました。");

            // 成功レスポンス
            req.getRequestDispatcher("StaffCalendar.action").forward(req, res);
        } catch (IllegalArgumentException e) {
            System.err.println("[エラー] 入力データが不正です: " + e.getMessage());
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            System.err.println("[エラー] サーバーエラー: " + e.getMessage());
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "イベント登録中にエラーが発生しました。");
        }
    }
}
