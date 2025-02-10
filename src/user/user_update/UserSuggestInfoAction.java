package user.user_update;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDao;
import tool.Action;

public class UserSuggestInfoAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションからユーザーIDを取得
        HttpSession session = req.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null) {
            // ユーザーがログインしていない場合、ログインページへリダイレクト
            res.sendRedirect("login.jsp");
            return;
        }

        // UserDaoを利用してユーザーの提案情報を取得
        UserDao userDao = new UserDao();
        User user = userDao.getUserSuggestInfo(userID);

        if (user == null) {
            req.setAttribute("errorMessage", "ユーザー情報が見つかりませんでした。");
        } else {
            req.setAttribute("user", user);
        }

        // JSPにフォワード
        req.getRequestDispatcher("user_suggestinfo.jsp").forward(req, res);
    }
}
