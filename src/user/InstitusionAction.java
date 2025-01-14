package user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Institusion;
import dao.InstitusionDao;
import tool.Action;

public class InstitusionAction extends Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // DAOの初期化
        InstitusionDao institusionDao = new InstitusionDao();

        // 全件取得メソッドを使用
        List<Institusion> institusions = institusionDao.getAll();

        // 結果をリクエストに設定
        request.setAttribute("institusions", institusions);

        // JSPへフォワード
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
}
