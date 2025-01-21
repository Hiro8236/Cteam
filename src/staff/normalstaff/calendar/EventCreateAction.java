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

         // チェックボックスの値を取得
            boolean isPublic = req.getParameter("isPublic") != null; // チェックされていれば true
            boolean isStaffOnly = req.getParameter("isStaffOnly") != null; // チェックされていれば true

         // デバッグログ
            System.out.println("公開: " + isPublic);
            System.out.println("スタッフ限定: " + isStaffOnly);

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

            // 日付の検証: 終了時が開始時より前でないことを確認
            Timestamp startTime = Timestamp.valueOf(start.replace("T", " ") + ":00");
            Timestamp endTime = Timestamp.valueOf(end.replace("T", " ") + ":00");

            if (endTime.before(startTime)) {
                throw new IllegalArgumentException("終了時は開始時より後である必要があります。");
            }

            // 仮の作成者ID（ログイン情報がある場合に変更）
            int createdBy = 1;

            // Eventオブジェクトを作成
            Event event = new Event();
            event.setTitle(title);
            event.setDescription(description);
            event.setStartTime(Timestamp.valueOf(restart));
            event.setEndTime(Timestamp.valueOf(reend));
            event.setCreatedBy(createdBy);
            String visibility = req.getParameter("visibility");
            event.setPublic("public".equals(visibility));
            event.setStaffOnly("staffOnly".equals(visibility));


            //デバッグ用
            System.out.println("タイトル: " + title);
            System.out.println("説明: " + description);
            System.out.println("開始日時: " + restart);
            System.out.println("終了日時: " + reend);
            System.out.println("公開: " + isPublic);
            System.out.println("スタッフ限定: " + isStaffOnly);

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
