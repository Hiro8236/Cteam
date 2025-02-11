package staff.normalstaff.institution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Institution;
import dao.InstitutionDao;
import tool.Action;

public class StaffInstitutionEditAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // ローカル変数の宣言
        InstitutionDao insDao = new InstitutionDao(); // 支援Dao
        Map<String, String> errors = new HashMap<>(); // エラーメッセージ

        System.out.println("1");
        // 支援リストを取得
        List<Institution> institutions = insDao.getAll(); // 全ての支援情報を取得
        for (Institution institution : institutions) {
            System.out.println("ID: " + institution.getID());
            System.out.println("Name: " + institution.getName());
            System.out.println("Detail: " + institution.getDetail());
            System.out.println("Video: " + institution.getVideo());
            System.out.println("PDF Path: " + institution.getPdfPath());
            System.out.println("Income Requirement: " + institution.getIncomeRequirement());
            System.out.println("Eligible Children Count: " + institution.getEligibleChildrenCount());
            System.out.println("Required Employment Status: " + institution.getRequiredEmploymentStatus());
            System.out.println("Eligibility Reason: " + institution.getEligibilityReason());
            System.out.println("Required School Status: " + institution.getRequiredSchoolStatus());
            System.out.println("---------------------------------------");
        }

        req.setAttribute("institutions", institutions); // JSPに渡す

        System.out.println("2");

        // リクエストパラメータの取得
        String insIDstr = req.getParameter("id"); // 支援ID (変更)
        System.out.println("Received institutionID: " + insIDstr);
        System.out.println("3");

        // institutionID がnullまたは空の場合、エラーメッセージを設定
        if (insIDstr == null || insIDstr.isEmpty()) {
            errors.put("institutionID", "支援IDが必要です。");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("staff_institution_edit.jsp").forward(req, res);
            return; // エラーが発生した場合は処理を中断
        }
        System.out.println("4");

        int insId = Integer.parseInt(insIDstr);

        // DBからデータ取得
        Institution InsFromDb = insDao.findById(insId);
        System.out.println("Retrieved Institution: " + InsFromDb);
        System.out.println("5");

        // ビジネスロジック
        if (InsFromDb != null) {
            // 支援が存在していた場合、情報をJSPに渡す
            req.setAttribute("institution", InsFromDb);
        } else {
            // 支援が存在しない場合、エラーメッセージを設定
            errors.put("insID", "支援が存在していません");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("staff_institution_edit.jsp").forward(req, res);
            return; // エラーが発生した場合は処理を中断
        }
        System.out.println("6");

        // 詳細画面に遷移
        req.getRequestDispatcher("staff_institution_edit.jsp").forward(req, res);
    }
}
