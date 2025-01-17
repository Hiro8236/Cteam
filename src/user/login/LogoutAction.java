package user.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class LogoutAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションを取得
        HttpSession session = req.getSession(false); // セッションが存在しない場合はnullを返す

        if (session != null) {
            // セッションを無効化
            session.invalidate();
        }

        // ログインページにリダイレクト
        res.sendRedirect("../Home.action");
    }
}