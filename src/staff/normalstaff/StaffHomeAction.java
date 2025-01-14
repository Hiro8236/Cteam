package staff.normalstaff;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Staff;
import tool.Action;


public class StaffHomeAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session=req.getSession();
		Staff staff =(Staff)session.getAttribute("StaffID");
		req.setAttribute("Staffs",staff);

		req.getRequestDispatcher("staffhome.jsp").forward(req, res);
	}
}
