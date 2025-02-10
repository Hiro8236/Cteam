package user.user_update;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDao;
import tool.Action;

public class UserDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        List<String> errors = new ArrayList<>();
        HttpSession session = req.getSession();

        // セッションからユーザーIDを取得
        Integer userID = (Integer) session.getAttribute("userID");
        if (userID == null) {
            errors.add("セッションが無効です。再度ログインしてください。");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/user/login.jsp").forward(req, res);
            return;
        }

        UserDao userDao = new UserDao();
        User user = userDao.get(userID);
        if (user == null) {
            errors.add("ユーザー情報が見つかりませんでした。");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/user/login.jsp").forward(req, res);
            return;
        }

        // ユーザー入力のパスワードを取得（※DAO側でハッシュ化してチェックするため、ここでは入力の有無だけを確認）
        String password = req.getParameter("Password");
        if (password == null || password.isEmpty()) {
            errors.add("現在のパスワードを入力してください。");
            req.setAttribute("errors", errors);
            req.setAttribute("Password", password); // 再表示用
            req.getRequestDispatcher("user_delete.jsp").forward(req, res);
            return;
        }

        try {
            boolean isDeleted = userDao.DeleteUser(userID, password);
            if (isDeleted) {
                session.invalidate();
                req.setAttribute("isDeleted", true);
            } else {
            	errors.add("現在のパスワードが正しくありません。");
            	req.setAttribute("errors", errors);
            }
            req.getRequestDispatcher("user_delete_done.jsp").forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
            errors.add("予期せぬエラーが発生しました。");
            req.setAttribute("errors", errors);

            req.getRequestDispatcher("user_delete.jsp").forward(req, res);
        }

    }
}
