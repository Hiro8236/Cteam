package staff.normalstaff.notification;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Notification;
import bean.Staff;
import dao.NotificationDao;
import tool.Action;

public class StaffNotificationCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションからログイン中の職員情報を取得
        HttpSession session = req.getSession();
        Staff staff = (Staff) session.getAttribute("user");
        req.setAttribute("staff", staff);

        // DAOを使用して既存の支援情報を取得
        NotificationDao notificationDao = new NotificationDao();
        List<Notification> notifications = notificationDao.getAll();

        // 支援情報をリクエストスコープに設定
        req.setAttribute("notifications", notifications);

        // 支援登録用のJSPページに転送
        req.getRequestDispatcher("staff_notification_create.jsp").forward(req, res);
    }
}

