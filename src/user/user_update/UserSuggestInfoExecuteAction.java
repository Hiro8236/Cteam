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
        String annualIncomeParam = req.getParameter("annualIncome");
        String childrenCountParam = req.getParameter("childrenCount");
        String employmentStatus = req.getParameter("employmentStatus");
        String singleParentReason = req.getParameter("singleParentReason");
        String childSchoolStatus = req.getParameter("childSchoolStatus");

        // 空の場合、デフォルト値を設定
        Integer annualIncome = null;
        Integer childrenCount = null;

        // annualIncome と childrenCount を空文字やnullの場合にデフォルト値を設定
        if (annualIncomeParam != null && !annualIncomeParam.isEmpty()) {
            try {
                annualIncome = Integer.parseInt(annualIncomeParam);
            } catch (NumberFormatException e) {
                // 数字に変換できない場合の処理（例: 0に設定）
                annualIncome = 0;
            }
        }

        if (childrenCountParam != null && !childrenCountParam.isEmpty()) {
            try {
                childrenCount = Integer.parseInt(childrenCountParam);
            } catch (NumberFormatException e) {
                // 数字に変換できない場合の処理（例: 0に設定）
                childrenCount = 0;
            }
        }

        // 他のフィールドは空でも許可
        employmentStatus = (employmentStatus != null && !employmentStatus.isEmpty()) ? employmentStatus : null;
        singleParentReason = (singleParentReason != null && !singleParentReason.isEmpty()) ? singleParentReason : null;
        childSchoolStatus = (childSchoolStatus != null && !childSchoolStatus.isEmpty()) ? childSchoolStatus : null;

        // セッションからユーザーIDを取得
        HttpSession session = req.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null) {
            // ユーザーがログインしていない場合、ログインページへリダイレクト
            res.sendRedirect("login.jsp");
            return;
        }

        // ユーザーIDが取得できているかを確認するためにログ出力
        System.out.println("UserID: " + userID);

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
