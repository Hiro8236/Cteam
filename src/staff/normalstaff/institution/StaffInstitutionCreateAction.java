package staff.normalstaff.institution;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Institution;
import bean.Staff;
import dao.InstitutionDao;
import tool.Action;

public class StaffInstitutionCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションからログイン中の職員情報を取得
        HttpSession session = req.getSession();
        Staff staff =(Staff)session.getAttribute("user");
        req.setAttribute("Staff", staff);

        // DAOを使用して既存の職員情報を取得
        InstitutionDao InsDao = new InstitutionDao();
        List<Institution> institution = InsDao.getAll();

        // 職員一覧をリクエストスコープに設定
        req.setAttribute("institution", institution);

        // 職員登録用のJSPページに転送
        req.getRequestDispatcher("staff_institution_create.jsp").forward(req, res);
    }
}
