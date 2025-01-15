package staff.adminstaff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Staff;
import dao.StaffDao;
import tool.Action;

public class StaffListAction extends Action {

    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Staff loggedInStaff = (Staff) session.getAttribute("user"); // セッションから現在のユーザを取得

        // パラメータ用の変数
        int staffId = 0; // 初期値
        String staffName = "";
        String staffRole = "";

        // エラー用のマップ
        Map<String, String> errors = new HashMap<>();

        // DAOの初期化
        StaffDao sDao = new StaffDao();

        // パラメータを取得し、フィルタ条件を構築
        String staffIdStr = req.getParameter("f1");
        if (staffIdStr != null && !staffIdStr.isEmpty()) {
            try {
                staffId = Integer.parseInt(staffIdStr); // staffId を数値に変換
            } catch (NumberFormatException e) {
                errors.put("f1", "職員IDには数値を入力してください");
            }
        }
        staffName = req.getParameter("f2");
        staffRole = req.getParameter("f3");

        // フィルタリングを実行
        List<Staff> staffs = sDao.filterStaff(staffId, staffName, staffRole);

        // 結果をリクエスト属性に設定
        req.setAttribute("f1", staffIdStr); // 入力された職員ID
        req.setAttribute("f2", staffName); // 入力された職員名
        req.setAttribute("f3", staffRole); // 入力された役割
        req.setAttribute("staffs", staffs); // フィルタ結果
        req.setAttribute("errors", errors); // エラー

        // JSPに転送
        req.getRequestDispatcher("staff_list.jsp").forward(req, res);
    }
}
