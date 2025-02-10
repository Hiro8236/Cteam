package staff.adminstaff;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Staff;
import dao.StaffDao;
import dao.UserDao;
import tool.Action;

public class StaffCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // ローカル変数の宣言
        HttpSession session = req.getSession();
        StaffDao staffDao = new StaffDao();
        Map<String, String> errors = new HashMap<>();

        // ログインユーザーの情報を取得
        Staff loggedInStaff = (Staff) session.getAttribute("user");
        if (loggedInStaff == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // リクエストパラメータの取得
        String staffName = req.getParameter("staff_name"); // 職員名
        String staffRole = req.getParameter("staff_role"); // 役職
        String password = req.getParameter("password");   // パスワード
        String staffIDStr = req.getParameter("staff_id");  // ユーザー指定の職員ID

        // 入力値の検証
        if (staffName == null || staffName.trim().isEmpty()) {
            errors.put("staff_name", "氏名を入力してください");
        }
        if (staffRole == null || staffRole.trim().isEmpty()) {
            errors.put("staff_role", "役職を入力してください");
        }
        if (password == null || password.trim().isEmpty()) {
            errors.put("password", "パスワードを入力してください");
        }
        if (staffIDStr == null || staffIDStr.trim().isEmpty()) {
            errors.put("staff_id", "職員IDを入力してください");
        }

        // エラーがある場合はJSPへフォワード
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("staff_name", staffName);
            req.setAttribute("staff_role", staffRole);
            req.getRequestDispatcher("staff_create.jsp").forward(req, res);
            return;
        }

        // 入力された職員IDがユニークかを確認
        int staffID = Integer.parseInt(staffIDStr);
        Staff existingStaff = staffDao.findById(staffID);

        if (existingStaff != null) {
            errors.put("staff_id", "この職員IDはすでに使用されています");
            req.setAttribute("errors", errors);
            req.setAttribute("staff_name", staffName);
            req.setAttribute("staff_role", staffRole);
            req.getRequestDispatcher("staff_create.jsp").forward(req, res);
            return;
        }

        // パスワードをハッシュ化
        UserDao userDao = new UserDao();
        String hashedPassword = userDao.hashPassword(password);

        // 新しい職員インスタンスを作成
        Staff newStaff = new Staff();
        newStaff.setStaffName(staffName);
        newStaff.setStaffRole(staffRole);
        newStaff.setPassword(hashedPassword);
        newStaff.setStaffID(staffID); // ユーザー指定のIDを設定

        // 職員情報をデータベースに保存
        staffDao.save(newStaff);

        // 完了ページへフォワード
        req.getRequestDispatcher("staff_create_done.jsp").forward(req, res);
    }
}
