package user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Notification;
import dao.NotificationDao;
import tool.Action;

public class NotificationAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            NotificationDao notificationDao = new NotificationDao();
            List<Notification> notificationList = notificationDao.getAll();

            System.out.println("DAOから取得した通知リスト: " + notificationList);

            req.setAttribute("notificationList", notificationList);
            req.getRequestDispatcher("notification.jsp").forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "通知情報の取得中にエラーが発生しました。");
            req.getRequestDispatcher("error.jsp").forward(req, res);
        }
    }

}
