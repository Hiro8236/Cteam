package staff.normalstaff.notification;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Notification;
import dao.NotificationDao;
import tool.Action;

public class StaffNotificationAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();

        // セッションからメッセージを取得し、リクエストスコープに設定
        String message = (String) session.getAttribute("message");
        if (message != null) {
            req.setAttribute("message", message);
            session.removeAttribute("message"); // セッションから削除
        }

        // DAOを使用してデータを取得
        NotificationDao notificationDao = new NotificationDao();
        List<Notification> notifications = notificationDao.getAll();

        req.setAttribute("notifications", notifications);

        // JSPにフォワード
        req.getRequestDispatcher("staff_notification_list.jsp").forward(req, res);
    }
}