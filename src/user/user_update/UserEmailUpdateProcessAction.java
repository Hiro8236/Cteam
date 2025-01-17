package user.user_update;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDao;
import tool.Action;

public class UserEmailUpdateProcessAction extends Action{

	public void execute(HttpServletRequest req , HttpServletResponse res) throws Exception{

		UserDao userDao = new UserDao();
		User user = null;
		HttpSession session = req.getSession();

		Integer userID = (Integer) session.getAttribute("userID");


		String NewEmailAddress = req.getParameter("NewEmailAddress");

		user = userDao.get(userID);
		req.setAttribute("user", user);
		req.setAttribute("NewEmailAddress", NewEmailAddress);



		req.getRequestDispatcher("email_update_execute.jsp").forward(req,res);

	}
}