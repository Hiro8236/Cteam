package user.regist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import tool.Action;

public class UserRegistSuccessAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            // フォームからデータを取得
            String emailAddress = req.getParameter("EmailAddress");
            String password = req.getParameter("Password");

            // 入力値の検証
            if (emailAddress == null || emailAddress.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
                req.setAttribute("error", "メールアドレスまたはパスワードが空です。");
                req.getRequestDispatcher("login/UserRegist.jsp").forward(req, res);
                return;
            }


            UserDao userDao = new UserDao();
            // パスワードのハッシュ化 (推奨)
            //String hashedPassword = hashPassword(password);
            String hashedPassword = userDao.hashPassword(password);

            // ユーザー情報を保存

            userDao.saveUser(emailAddress, hashedPassword);

            // 成功メッセージを設定
            req.setAttribute("message", "登録が完了しました。ログインしてください。");

            // 登録完了ページに遷移
            req.getRequestDispatcher("login/UserRegistSuccess.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "ユーザー登録中にエラーが発生しました。");
            req.getRequestDispatcher("login/UserRegist.jsp").forward(req, res);
        }
    }
/*
    // パスワードのハッシュ化メソッド
    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("パスワードのハッシュ化に失敗しました。", e);
        }
    }
*/
}