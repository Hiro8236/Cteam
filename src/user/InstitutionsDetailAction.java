package user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Institution;
import dao.InstitutionDao;
import tool.Action;

public class InstitutionsDetailAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // ローカル変数の宣言
        InstitutionDao insDao = new InstitutionDao(); // 支援Dao
        Map<String, String> errors = new HashMap<>(); // エラーメッセージ

        // 支援リストを取得
        List<Institution> institutions = insDao.getAll(); // 全ての支援情報を取得
        req.setAttribute("institutions", institutions); // JSPに渡す

        // リクエストパラメータの取得
        String insIDstr = req.getParameter("id"); // 支援ID (変更)
        System.out.println("Received institutionID: " + insIDstr);

        // institutionID がnullまたは空の場合、エラーメッセージを設定
        if (insIDstr == null || insIDstr.isEmpty()) {
            errors.put("institutionID", "支援IDが必要です。");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("institutionsdetail.jsp").forward(req, res);
            return; // エラーが発生した場合は処理を中断
        }

        int insId = Integer.parseInt(insIDstr);

        // DBからデータ取得
        Institution InsFromDb = insDao.findById(insId);
        System.out.println("Retrieved Institution: " + InsFromDb);

        // ビジネスロジック
        if (InsFromDb != null) {
            // 支援が存在していた場合、情報をJSPに渡す
            req.setAttribute("institution", InsFromDb);
        } else {
            // 支援が存在しない場合、エラーメッセージを設定
            errors.put("insID", "支援が存在していません");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("institutionsdetail.jsp").forward(req, res);
            return; // エラーが発生した場合は処理を中断
        }

        // 詳細画面に遷移
        req.getRequestDispatcher("institutionsdetail.jsp").forward(req, res);
    }
}
