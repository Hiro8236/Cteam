package user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Notification;
import dao.NotificationDao;

public class NotificationAction {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            // DAOを使用して動画一覧を取得
        	NotificationDao notificationDao = new NotificationDao();
        	List<Notification> notificationList = notificationDao.getAllNotification();
            // リクエストに動画一覧をセット
            request.setAttribute("NotificationList", notificationList);

            // 表示するJSPのパスを返す
            return "/user/notification.jsp";

        } catch (Exception e) {
            e.printStackTrace();
            // エラーページにリダイレクト
            return "/error.jsp";
        }
    }
}
