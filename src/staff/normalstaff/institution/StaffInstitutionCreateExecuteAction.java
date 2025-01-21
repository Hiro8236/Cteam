package staff.normalstaff.institution;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Institution;
import bean.Staff;
import dao.InstitutionDao;
import tool.Action;

public class StaffInstitutionCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        System.out.println("支援登録処理を開始します");

        // ローカル変数の宣言
        HttpSession session = req.getSession();
        InstitutionDao institutionDao = new InstitutionDao();
        Map<String, String> errors = new HashMap<>();

        // ログインユーザーの情報を取得
        Staff staff = (Staff) session.getAttribute("user");
        if (staff == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // リクエストパラメータの取得
        String institutionName = req.getParameter("institution_name");
        String institutionDetail = req.getParameter("institution_detail");

        // 入力値の検証
        if (institutionName == null || institutionName.trim().isEmpty()) {
            errors.put("institution_name", "支援名を入力してください");
        }
        if (institutionDetail == null || institutionDetail.trim().isEmpty()) {
            errors.put("institution_detail", "支援詳細を入力してください");
        }

        // エラーがある場合はJSPへフォワード
        if (!errors.isEmpty()) {
            System.out.println("入力にエラーがあります");
            req.setAttribute("errors", errors);
            req.setAttribute("institution_name", institutionName);
            req.setAttribute("institution_detail", institutionDetail);
            req.getRequestDispatcher("staff_institution_create.jsp").forward(req, res);
            return;
        }

        // 支援インスタンスを作成して保存
        Institution institution = new Institution();
        institution.setName(institutionName);
        institution.setDetail(institutionDetail);

        // 支援を挿入して生成されたIDを取得
        int institutionId = institutionDao.insert(institution);

        // 職員と支援を関連付ける処理
        institutionDao.insert(staff.getStaffID(), institutionId);

        // 完了ページへフォワード
        req.getRequestDispatcher("staff_institution_create_done.jsp").forward(req, res);
    }

}
