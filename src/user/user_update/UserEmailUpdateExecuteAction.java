package user.user_update;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDao;
import tool.Action;

public class UserEmailUpdateExecuteAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // フォームデータを取得
        String EmailAddress = req.getParameter("EmailAddress");
        String NewEmailAddress = req.getParameter("NewEmailAddress");
        String Password = req.getParameter("Password");

        UserDao userDao = new UserDao();
        String hashedPassword = userDao.hashPassword(Password);

        // セッションからユーザーIDを取得
        HttpSession session = req.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        // DAOを利用してユーザー情報を更新
        boolean isUpdated = userDao.updateEmail(userID, EmailAddress, hashedPassword, NewEmailAddress);

        // 更新結果に応じてメッセージを設定
        if (isUpdated) {
            req.setAttribute("message", "メールアドレスが正常に更新されました。");
            // 更新完了ページへ転送
            req.getRequestDispatcher("UserInfo.action").forward(req, res);
        } else {
            // パスワードが一致しない場合、元のフォームページへリダイレクト
            req.setAttribute("errorMessage", "現在のメールアドレスまたはパスワードが一致しません。");

    		User user = null;
            user = userDao.get(userID);
    		req.setAttribute("user", user);
            req.setAttribute("NewEmailAddress", NewEmailAddress);  // フォームに新しいメールアドレスを保持
            // 元の更新フォームページへリダイレクト
            req.getRequestDispatcher("email_update_execute.jsp").forward(req, res);
        }
    }
}
