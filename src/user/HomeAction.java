package user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Institusion;
import dao.InstitusionDao;
import tool.Action;


public class HomeAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session=req.getSession();
		Integer user =(Integer)session.getAttribute("userID");




        if (user != null) {
        // セッションに userID が存在する場合は、リクエスト属性に設定
        req.setAttribute("User", user);
        } else {
        // userID が null の場合でも問題なく進むようにする
        System.out.println("Session userID is null. Proceeding without user.");
        req.setAttribute("User", null); // 必要に応じて null を設定
        }

		 // DAOの初期化
        InstitusionDao institusionDao = new InstitusionDao();

        // 全件取得メソッドを使用
        List<Institusion> institusions = institusionDao.getAll();

        // 結果をリクエストに設定
        req.setAttribute("institusions", institusions);

		req.getRequestDispatcher("home.jsp").forward(req, res);
	}
}
