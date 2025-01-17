package staff.adminstaff;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Staff;
import dao.StaffDao;
import tool.Action;

public class StaffDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        StaffDao staffDao = new StaffDao();  // StaffDaoを使用
        HttpSession session = req.getSession();  // セッションの取得

        // ログインユーザーを取得
        Staff staff = (Staff) session.getAttribute("user");
        Map<String, String> errors = new HashMap<>();  // エラーメッセージを格納

        // 削除対象の職員IDを取得
        String staffIdStr = req.getParameter("staffId");

        if (staffIdStr != null && !staffIdStr.isEmpty()) {
            int staffId = Integer.parseInt(staffIdStr);

            // 職員IDを基に職員情報を取得
            Staff targetStaff = staffDao.findById(staffId);

            if (targetStaff != null) {
                // 職員が見つかれば、その情報をリクエストにセット
                req.setAttribute("staff", targetStaff);
            } else {
                errors.put("staffId", "指定された職員が存在しません。");
                req.setAttribute("errors", errors);
            }
        } else {
            errors.put("staffId", "職員IDが無効です。");
            req.setAttribute("errors", errors);
        }

        // 削除対象の職員情報を表示するJSPページにフォワード
        req.getRequestDispatcher("staff_delete.jsp").forward(req, res);
    }
}
