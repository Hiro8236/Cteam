package user.calendar;

import java.util.Timer;

public class Scheduler {
    public static void startEmailScheduler() {
        Timer timer = new Timer();
        timer.schedule(new EmailNotificationTask(), 0, 24 * 60 * 60 * 1000); // 24時間ごとに実行
    }
}
