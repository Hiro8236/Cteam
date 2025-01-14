package user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;


public class HomeAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Integer userID = (Integer) session.getAttribute("userID");
		req.setAttribute("UserID",userID);


		req.getRequestDispatcher("home.jsp").forward(req, res);
	}
}
