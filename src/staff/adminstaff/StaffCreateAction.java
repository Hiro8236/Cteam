package staff.adminstaff;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Staff;
import dao.StaffDao;
import tool.Action;

public class StaffCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションからログイン中の職員情報を取得
        HttpSession session = req.getSession();
        Staff staff =(Staff)session.getAttribute("user");
        req.setAttribute("Staff", staff);

        // DAOを使用して既存の職員情報を取得
        StaffDao staffDao = new StaffDao();
        List<Staff> staffList = staffDao.filterStaff(0, null, null); // 全職員を取得

        // 職員一覧をリクエストスコープに設定
        req.setAttribute("staff_list", staffList);

        // 職員登録用のJSPページに転送
        req.getRequestDispatcher("staff_create.jsp").forward(req, res);
    }
}
