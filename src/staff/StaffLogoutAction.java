package staff;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class StaffLogoutAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション

		session.invalidate();//セッション全削除

		//JSPへフォワード 7
		req.getRequestDispatcher("stafflogout.jsp").forward(req, res);
	}
}
