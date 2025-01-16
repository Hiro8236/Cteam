package user.user_update;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDao;
import tool.Action;

public class UserUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // エラーメッセージリスト
        List<String> errors = new ArrayList<>();

        // セッションやリクエストからユーザー情報を取得
        UserDao userDao = new UserDao();
        User user = null;
        HttpSession session = req.getSession();

        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null) {
            errors.add("セッションが無効です。再度ログインしてください。");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/user/login.jsp").forward(req, res);
            return;
        }

        user = userDao.get(userID);

        if (user == null) {
            errors.add("ユーザー情報が見つかりませんでした。");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/user/login.jsp").forward(req, res);
            return;
        }

        String currentPassword = req.getParameter("Password"); // 現在のパスワード（ユーザー入力）
        String newPassword = req.getParameter("Newpassword");  // 新しいパスワード（ユーザー入力）

        // 入力チェック
        if (currentPassword == null || currentPassword.isEmpty()) {
            errors.add("現在のパスワードを入力してください。");
        } else {
            // 現在のパスワードをハッシュ化して比較
            String hashedCurrentPassword = userDao.hashPassword(currentPassword);
            if (!hashedCurrentPassword.equals(user.getPassword())) {
                errors.add("現在のパスワードが正しくありません。");
            }
        }

        if (newPassword == null || newPassword.isEmpty()) {
            errors.add("新しいパスワードを入力してください。");
        } else if (newPassword.length() > 20) {
            errors.add("新しいパスワードは20文字以内で入力してください。");
        }

        // エラーがあれば JSP に返す
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("Password", currentPassword); // 再表示用
            req.getRequestDispatcher("user_update.jsp").forward(req, res);
            return;
        }

        // 新しいパスワードをハッシュ化
        String hashedNewPassword = userDao.hashPassword(newPassword);

        // DAO を利用してパスワードを更新
        boolean isUpdated = userDao.save(user.getUserID(), hashedNewPassword);

        if (isUpdated) {
            // 更新成功時、セッション情報も更新
            user.setPassword(hashedNewPassword);  // セッション内のユーザーオブジェクトを更新
            session.setAttribute("user", user);  // ユーザーオブジェクトをセッションに再設定

            // 更新成功後のリダイレクト
            res.sendRedirect("user_info.jsp");
        } else {
            // 更新失敗時
            errors.add("パスワードの更新に失敗しました。");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("user_update.jsp").forward(req, res);
        }
    }
}
