package user.calendar;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("アプリケーション起動: メール通知スケジューラーを開始");
        Scheduler.startEmailScheduler();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("アプリケーション終了");
    }
}
