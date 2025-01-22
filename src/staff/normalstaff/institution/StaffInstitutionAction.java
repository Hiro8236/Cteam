package staff.normalstaff.institution;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Institution;
import dao.InstitutionDao;
import tool.Action;

public class StaffInstitutionAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();

        // セッションからメッセージを取得し、リクエストスコープに設定
        String message = (String) session.getAttribute("message");
        if (message != null) {
            req.setAttribute("message", message);
            session.removeAttribute("message"); // セッションから削除
        }

        // DAOを使用してデータを取得
        InstitutionDao institutionDao = new InstitutionDao();
        List<Institution> institutions = institutionDao.getAll();

        req.setAttribute("institutions", institutions);

        // JSPにフォワード
        req.getRequestDispatcher("staff_institution_list.jsp").forward(req, res);
    }
}