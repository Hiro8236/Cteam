package staff.adminstaff;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Staff;
import dao.StaffDao;
import tool.Action;

public class StaffUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // ローカル変数の宣言
        StaffDao sDao = new StaffDao(); // 職員Dao
        Map<String, String> errors = new HashMap<>(); // エラーメッセージ

        // リクエストパラメータの取得
        String staffIdStr = req.getParameter("staffId"); // 職員ID

        // staffId がnullまたは空の場合、エラーメッセージを設定
        if (staffIdStr == null || staffIdStr.isEmpty()) {
            errors.put("staffId", "職員IDが必要です");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("staff_update.jsp").forward(req, res);
            return; // エラーが発生した場合は処理を中断
        }

        int staffId = Integer.parseInt(staffIdStr);

        // DBからデータ取得
        Staff staffFromDb = sDao.findById(staffId);

        // ビジネスロジック
        if (staffFromDb != null) {
            // 職員が存在していた場合、情報をJSPに渡す
            req.setAttribute("staff", staffFromDb);
        } else {
            // 職員が存在しない場合、エラーメッセージを設定
            errors.put("staffId", "指定された職員が存在していません");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("staff_update.jsp").forward(req, res);
            return; // エラーが発生した場合は処理を中断
        }

        // 更新画面に遷移
        req.getRequestDispatcher("staff_update.jsp").forward(req, res);
    }
}

