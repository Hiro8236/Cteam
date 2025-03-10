package user.suggest;

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
	    HttpSession session = req.getSession();
	    Integer userID = (Integer) session.getAttribute("userID");

	    if (userID == null) {
	        // Send an alert message to the user
	        res.getWriter().write("<script type='text/javascript'>alert('ログインしてください'); window.location.href='/Cteam1/user/login/Login.action';</script>");
	        return;
	    }

	    // ユーザーIDをリクエスト属性に設定
	    req.setAttribute("UserID", userID);

	    // DAOの初期化
	    SuggestListDao suggestlistDao = new SuggestListDao();

	    // ユーザーIDを基に適用可能な制度を取得
	    List<Suggest> suggestlists = suggestlistDao.getByUserId(userID);

	    // 結果をリクエストに設定
	    req.setAttribute("suggestlists", suggestlists);

	    // JSPへフォワード
	    req.getRequestDispatcher("suggest_list.jsp").forward(req, res);
	}

}
