package user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Institusion;
import bean.User;
import dao.InstitusionDao;
import tool.Action;


public class HomeAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session=req.getSession();
		User user =(User)session.getAttribute("userID");
		req.setAttribute("User",user);

		 // DAOの初期化
        InstitusionDao institusionDao = new InstitusionDao();

        // 全件取得メソッドを使用
        List<Institusion> institusions = institusionDao.getAll();

        // 結果をリクエストに設定
        req.setAttribute("institusions", institusions);


		req.getRequestDispatcher("home.jsp").forward(req, res);
	}
}
