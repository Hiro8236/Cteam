package staff.adminstaff;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Staff;
import dao.StaffDao;
import tool.Action;

public class StaffUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // ローカル変数の宣言
        StaffDao sDao = new StaffDao();  // StaffDaoのインスタンス化
        Map<String, String> errors = new HashMap<>();  // エラーメッセージ用のMap

        // リクエストパラメータの取得
        String staffIdStr = req.getParameter("staffId");
        String staffName = req.getParameter("staffName");
        String staffRole = req.getParameter("staffRole");

        // スタッフIDが取得できていない場合のエラー処理
        if (staffIdStr == null || staffIdStr.isEmpty()) {
            errors.put("staffId", "職員IDが必要です");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("staff_update.jsp").forward(req, res);
            return;
        }

        // スタッフIDのパース
        int staffId = Integer.parseInt(staffIdStr);

        // スタッフ情報の取得
        Staff staff = sDao.findById(staffId);

        // スタッフが存在していない場合
        if (staff == null) {
            errors.put("staffId", "指定された職員が存在しません");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("staff_update.jsp").forward(req, res);
            return;
        }

        // スタッフ情報の更新
        staff.setStaffName(staffName);
        staff.setStaffRole(staffRole);

        // 更新をデータベースに保存
        sDao.update(staff);

        // 更新後のメッセージ
        req.setAttribute("staff", staff);
        req.getRequestDispatcher("staff_update_done.jsp").forward(req, res);
    }
}
