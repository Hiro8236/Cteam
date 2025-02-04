package staff.normalstaff.notification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.NotificationDao;
import tool.Action;

public class StaffNotificationDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();

        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);

                NotificationDao notificationDao = new NotificationDao();
                boolean isDeleted = notificationDao.deleteNotificationById(id);

                if (isDeleted) {
                    // 削除成功：セッションにメッセージを保存
                    session.setAttribute("message", "制度を削除しました");
                } else {
                    // 削除失敗：セッションにエラーメッセージを保存
                    session.setAttribute("message", "削除に失敗しました");
                }

            } catch (NumberFormatException e) {
                // 無効なIDの場合
                session.setAttribute("message", "無効なIDです");
            }
        } else {
            // IDが指定されていない場合
            session.setAttribute("message", "IDが指定されていません");
        }

        // 一覧ページにリダイレクト
        res.sendRedirect("StaffNotification.action");
    }
}
