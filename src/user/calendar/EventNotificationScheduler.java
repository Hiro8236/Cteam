package user.calendar;

import java.util.Timer;
import java.util.TimerTask;

public class EventNotificationScheduler {

    public static void startScheduler() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                EventNotificationService service = new EventNotificationService();
                service.sendEventNotifications();
            }
        }, 60 * 1000); // 24時間ごとに実行
    }
}
