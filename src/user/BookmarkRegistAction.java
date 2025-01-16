package user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Suggest;
import dao.SuggestListDao;
import tool.Action;

public class BookmarkRegistAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res)throws Exception{
		// DAOの初期化
        SuggestListDao suggestinstitusionDao = new SuggestListDao();

        // 全件取得メソッドを使用
        List<Suggest> suggestinstitusions = suggestinstitusionDao.getAll();

        // 結果をリクエストに設定
        req.setAttribute("suggestinstitusions", suggestinstitusions);

		//JSPへフォワード
		req.getRequestDispatcher("bookmark_regist.jsp").forward(req, res);
	}

}
