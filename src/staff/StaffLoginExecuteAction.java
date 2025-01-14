package staff;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Staff;
import dao.StaffDao;
import tool.Action;

public class StaffLoginExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String url = "";
        String staffIdParam = "";
        String password = "";
        int staffId = 0;

        System.out.println("StaffDaoを生成");

        StaffDao staffDao = new StaffDao();
        Staff staff = null;

        // リクエストパラメータの取得
        staffIdParam = req.getParameter("StaffId");
        password = req.getParameter("password");

        // StaffIdが整数であるかをチェック
        if (staffIdParam != null && !staffIdParam.isEmpty()) {
            try {
                staffId = Integer.parseInt(staffIdParam); // 整数に変換
            } catch (NumberFormatException e) {
                // 整数変換エラーの場合
                List<String> errors = new ArrayList<>();
                errors.add("スタッフIDは数値で入力してください");
                req.setAttribute("errors", errors);

                // フォワード
                url = "StaffLogin.action";
                req.getRequestDispatcher(url).forward(req, res);
                return;
            }
        } else {
            // StaffIdが空またはnullの場合
            List<String> errors = new ArrayList<>();
            errors.add("スタッフIDを入力してください");
            req.setAttribute("errors", errors);

            // フォワード
            url = "StaffLogin.action";
            req.getRequestDispatcher(url).forward(req, res);
            return;
        }

        // DBからデータ取得
        staff = staffDao.login(staffId, password); // IDとパスワードで認証

        if (staff != null) {
            // セッション情報を取得
            HttpSession session = req.getSession(true);
            // 認証済みフラグを立てる
            staff.setAuthenticated(true);
            // セッションにログイン情報を保存
            session.setAttribute("staff", staff);

            // staffroleによるリダイレクト
            String staffRole = staff.getStaffRole();
            if ("1".equals(staffRole)) {
                url = "normalstaff/StaffHome.action";  // 一般スタッフホーム
            } else if ("2".equals(staffRole)) {
                url = "adminstaff/AdminStaffHome.action";  // 管理者ホーム
            } else {
                url = "StaffLogin.action";  // その他の場合
            }
            res.sendRedirect(url);
        } else {
            // 認証失敗の場合
            List<String> errors = new ArrayList<>();
            errors.add("スタッフIDまたはパスワードが違います");
            req.setAttribute("errors", errors);

            // フォワード
            url = "StaffLogin.action";
            req.getRequestDispatcher(url).forward(req, res);
        }
    }
}

