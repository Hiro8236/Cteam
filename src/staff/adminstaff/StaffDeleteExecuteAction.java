package staff.adminstaff;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Staff;
import dao.StaffDao;
import tool.Action;

public class StaffDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        StaffDao staffDao = new StaffDao();  // StaffDaoを使用
        HttpSession session = req.getSession();  // セッションの取得

        // ログインユーザーを取得（Teacherまたは適切なユーザータイプ）
        Staff staff = (Staff) session.getAttribute("user");
        Map<String, String> errors = new HashMap<>();  // エラーメッセージを格納

        // リクエストパラメータから職員IDを取得
        String staffIdStr = req.getParameter("staffId");

        if (staffIdStr != null && !staffIdStr.isEmpty()) {
            int staffId = Integer.parseInt(staffIdStr);

            // 職員IDを基にStaffDaoから職員情報を取得
            Staff staffs = staffDao.findById(staffId);

            if (staffs != null) {
                // 職員が存在する場合、削除処理を実行
                boolean result = staffDao.deleteStaffById(staffId);

                if (result) {
                    req.setAttribute("message", "職員情報が削除されました。");
                } else {
                    errors.put("staffId", "職員削除に失敗しました。");
                    req.setAttribute("errors", errors);
                }
            } else {
                errors.put("staffId", "指定された職員が存在しません。");
                req.setAttribute("errors", errors);
            }
        } else {
            errors.put("staffId", "職員IDが無効です。");
            req.setAttribute("errors", errors);
        }

        // 削除完了後、適切なページへ遷移（職員一覧ページなど）
        req.getRequestDispatcher("staff_delete_done.jsp").forward(req, res);
    }
}
