package staff.normalstaff.institution;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.InstitutionDao;
import tool.Action;

public class StaffInstitutionEditExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // リクエストからパラメータを取得
        String idParam = req.getParameter("id");
        String name = req.getParameter("name");
        String detail = req.getParameter("detail");

        // バリデーション
        if (idParam == null || name == null || detail == null || idParam.isEmpty() || name.isEmpty() || detail.isEmpty()) {
            req.setAttribute("message", "入力された情報が不完全です");
            req.getRequestDispatcher("staff_institution_edit.jsp").forward(req, res);
            return;
        }

        try {
            // IDを数値に変換
            int id = Integer.parseInt(idParam);

            // InstitutionDaoを使用して更新処理を実行
            InstitutionDao institutionDao = new InstitutionDao();
            boolean isUpdated = institutionDao.updateInstitution(id, name, detail);

            if (isUpdated) {
                // 更新成功
                req.setAttribute("message", "支援情報が正常に更新されました");
                req.getRequestDispatcher("StaffInstitution.action").forward(req, res);
            } else {
                // 更新失敗
                req.setAttribute("message", "支援情報の更新に失敗しました");
                req.getRequestDispatcher("staff_institution_edit.jsp").forward(req, res);
            }
        } catch (NumberFormatException e) {
            // IDが無効な場合
            req.setAttribute("message", "無効な支援IDです");
            req.getRequestDispatcher("staff_institution_edit.jsp").forward(req, res);
        }
    }
}

