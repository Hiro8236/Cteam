package user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.SuggestInstitusion;
import bean.User;
import dao.SuggestInstitusionDao;
import tool.Action;

public class SuggestInstitusionAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session=req.getSession();
		User user =(User)session.getAttribute("userID");
		req.setAttribute("User",user);

		 // DAOの初期化
        SuggestInstitusionDao suggestinstitusionDao = new SuggestInstitusionDao();

        // 全件取得メソッドを使用
        List<SuggestInstitusion> suggestinstitusions = suggestinstitusionDao.getAll();

        // 結果をリクエストに設定
        req.setAttribute("suggestinstitusions", suggestinstitusions);


		req.getRequestDispatcher("suggest_institusion.jsp").forward(req, res);
	}
}