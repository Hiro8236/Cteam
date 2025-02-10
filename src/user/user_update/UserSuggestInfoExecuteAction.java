package user.user_update;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import tool.Action;

public class UserSuggestInfoExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // フォームから送信されたデータを取得
        int annualIncome = Integer.parseInt(req.getParameter("annualIncome"));
        int childrenCount = Integer.parseInt(req.getParameter("childrenCount"));
        String employmentStatus = req.getParameter("employmentStatus");
        String singleParentReason = req.getParameter("singleParentReason");
        String childSchoolStatus = req.getParameter("childSchoolStatus");

        // セッションからユーザーIDを取得
        HttpSession session = req.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null) {
            // ユーザーがログインしていない場合、ログインページへリダイレクト
            res.sendRedirect("login.jsp");
            return;
        }

        // UserDaoを使ってデータベースに保存
        UserDao userDao = new UserDao();
        boolean isSaved = userDao.saveUserSuggestInfo(userID, annualIncome, childrenCount,
                                                      employmentStatus, singleParentReason,
                                                      childSchoolStatus);

        // 保存結果をJSPに渡す
        if (isSaved) {
            req.setAttribute("message", "推奨情報が正常に保存されました。");
        } else {
            req.setAttribute("message", "推奨情報の保存に失敗しました。");
        }

        // 結果を表示するJSPにフォワード
        req.getRequestDispatcher("user_suggestinfo_done.jsp").forward(req, res);
    }
}
