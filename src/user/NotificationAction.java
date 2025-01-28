package user;

import java.util.List;

import bean.Notification;
import dao.NotificationDAO;

public class NotificationAction {

    private NotificationDAO notificationDAO;

    public NotificationAction() {
        notificationDAO = new NotificationDAO();
    }

    /**
     * 全ての通知を取得します。
     * @return 通知のリスト
     * @throws Exception
     */
    public List<Notification> getAllNotifications() throws Exception {
        return notificationDAO.getAll();
    }

    /**
     * 通知を新規作成します。
     * @param title 通知のタイトル
     * @param sentence 通知の内容
     * @return 成功時はtrue、失敗時はfalse
     * @throws Exception
     */
    public boolean createNotification(String title, String sentence) throws Exception {
        return notificationDAO.saveNotification(title, sentence);
    }

    /**
     * 指定されたIDの通知を削除します。
     * @param id 通知ID
     * @return 成功時はtrue、失敗時はfalse
     * @throws Exception
     */
    public boolean deleteNotification(int id) throws Exception {
        return notificationDAO.deleteNotification(id);
    }
}