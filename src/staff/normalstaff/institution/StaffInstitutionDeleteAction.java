package staff.normalstaff.institution;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.InstitutionDao;
import tool.Action;

public class StaffInstitutionDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッション情報を取得
        HttpSession session = req.getSession();
        // 必要に応じてユーザー情報を取得する場合はここで行う（現在のコードでは未使用）
        // Staff staff =(Staff)session.getAttribute("user");

        // 削除するインスタンスIDをリクエストから取得
        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);

                // DAOの初期化
                InstitutionDao institutionDao = new InstitutionDao();

                // 削除メソッドを呼び出し
                boolean isDeleted = institutionDao.deleteInstitutionById(id);

                if (isDeleted) {
                    // 削除成功後、一覧ページにリダイレクト
                    res.sendRedirect("StaffInstitution.action");  // 一覧にリダイレクト
                } else {
                    // 削除失敗時の処理（エラーメッセージなどを設定する）
                    req.setAttribute("errorMessage", "削除に失敗しました");
                    req.getRequestDispatcher("error.jsp").forward(req, res);
                }

            } catch (NumberFormatException e) {
                req.setAttribute("errorMessage", "無効なIDです");
                req.getRequestDispatcher("error.jsp").forward(req, res);
            }
        } else {
            req.setAttribute("errorMessage", "IDが指定されていません");
            req.getRequestDispatcher("error.jsp").forward(req, res);
        }
    }
}
