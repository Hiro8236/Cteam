package user.user_update;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDao;
import tool.Action;

public class UserInfoAction extends Action{

	public void execute(HttpServletRequest req , HttpServletResponse res) throws Exception{


		UserDao userDao = new UserDao();
		User user = null;
		HttpSession session = req.getSession();

		Integer userID = (Integer) session.getAttribute("userID");

		System.out.println(userID);


	    String message = (String) req.getAttribute("message");
		 if (message != null) {
		        // messageを使って処理（画面に表示など）
		        req.setAttribute("message", message);
		    }

		user = userDao.get(userID);
		req.setAttribute("user", user);

		req.getRequestDispatcher("user_info.jsp").forward(req,res);

	}
}