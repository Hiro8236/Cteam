package staff.adminstaff;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Staff;
import tool.Action;


public class AdminStaffHomeAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session=req.getSession();
		Staff staff =(Staff)session.getAttribute("StaffID");
		req.setAttribute("Staffs",staff);

		req.getRequestDispatcher("adminstaffhome.jsp").forward(req, res);
	}
}
