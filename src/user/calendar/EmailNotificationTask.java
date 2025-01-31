package user.calendar;

import java.util.List;
import java.util.TimerTask;

import bean.Event;
import dao.EventDao;

public class EmailNotificationTask extends TimerTask {

    @Override
    public void run() {
        try {
            System.out.println("=== 通知メール送信処理開始 ===");
            EventDao eventDao = new EventDao();
            List<Event> events = eventDao.getUpcomingNotifiedEvents();

            for (Event event : events) {
                String to = event.getEmail();
                String subject = "イベントのリマインダー: " + event.getTitle();
                String body = "以下のイベントが 24 時間以内に開始されます。\n\n"
                            + "イベント名: " + event.getTitle() + "\n"
                            + "開始日時: " + event.getStartTime() + "\n"
                            + "説明: " + event.getDescription() + "\n\n"
                            + "お忘れなく！";

                MailUtil.sendEmail(to, subject, body);
            }
            System.out.println("[成功] 通知メール送信完了");
        } catch (Exception e) {
            System.err.println("[エラー] 通知メール送信中にエラー: " + e.getMessage());
        }
    }
}


