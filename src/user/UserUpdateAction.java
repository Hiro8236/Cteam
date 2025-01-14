package user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDao;
import tool.Action;

public class UserUpdateAction extends Action{

	public void execute(HttpServletRequest req , HttpServletResponse res) throws Exception{


		UserDao userDao = new UserDao();
		User user = null;
		HttpSession session = req.getSession();

		Integer userID = (Integer) session.getAttribute("userID");

		System.out.println(userID);



		user = userDao.get(userID);
		req.setAttribute("user", user);

		req.getRequestDispatcher("user_update.jsp").forward(req,res);

	}
}