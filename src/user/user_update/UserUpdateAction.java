package user.user_update;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDao;
import tool.Action;

public class UserUpdateAction extends Action {

    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        UserDao userDao = new UserDao();
        User user = null;
        HttpSession session = req.getSession();

        Integer userID = (Integer) session.getAttribute("userID");

        System.out.println("User ID: " + userID);

        user = userDao.get(userID);
        req.setAttribute("user", user);

        // リクエストパラメータ "action" から値を取得
        String actionParam = req.getParameter("action");
        if (actionParam != null) {
            int action = Integer.parseInt(actionParam);

            // 条件に応じてリダイレクト先を変更
            switch (action) {
                case 1:
                    req.getRequestDispatcher("email_update.jsp").forward(req, res);
                    break;
                case 2:
                    req.getRequestDispatcher("password_update.jsp").forward(req, res);
                    break;
                default:
                    // 無効な値の場合、デフォルト画面（例: ユーザー情報更新画面）
                    req.getRequestDispatcher("user_info.jsp").forward(req, res);
                    break;
            }
        } else {
            // "action" パラメータが指定されていない場合、デフォルト画面
            req.getRequestDispatcher("user_info.jsp").forward(req, res);
        }
    }
}
