package user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Suggest;
import dao.SuggestListDao;
import tool.Action;

public class SuggestListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session=req.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

		req.setAttribute("UserID",userID);

		 // DAOの初期化
        SuggestListDao suggestlistDao = new SuggestListDao();

        // 全件取得メソッドを使用
        List<Suggest> suggestlists = suggestlistDao.getAll();

        // 結果をリクエストに設定
        req.setAttribute("suggestlists", suggestlists);

		req.getRequestDispatcher("suggest_list.jsp").forward(req, res);
	}
}