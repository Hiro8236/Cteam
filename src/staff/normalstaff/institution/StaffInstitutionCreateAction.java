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
        Staff staff = (Staff) session.getAttribute("user");
        req.setAttribute("staff", staff);

        // DAOを使用して既存の支援情報を取得
        InstitutionDao institutionDao = new InstitutionDao();
        List<Institution> institutions = institutionDao.getAll();

        // 支援情報をリクエストスコープに設定
        req.setAttribute("institutions", institutions);

        // 支援登録用のJSPページに転送
        req.getRequestDispatcher("staff_institution_create.jsp").forward(req, res);
    }
}

