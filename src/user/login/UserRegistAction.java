package user.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class UserRegistAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) {
        try {
            // 新規登録ページへのフォワード
        	req.getRequestDispatcher("/user/login/UserRegist.jsp").forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
            // エラーの場合はエラーページにフォワード
            try {
                req.getRequestDispatcher("/error.jsp").forward(req, res);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
