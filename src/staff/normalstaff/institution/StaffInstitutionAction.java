package staff.normalstaff.institution;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Institution;
import bean.Staff;
import dao.InstitutionDao;
import tool.Action;


public class StaffInstitutionAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

	    HttpSession session = req.getSession();
		Staff staff =(Staff)session.getAttribute("user");
		req.setAttribute("Staffs",staff);


	    // DAOの初期化
	    InstitutionDao institutionDao = new InstitutionDao();

	    try {
	        // 全件取得メソッドを使用
	        List<Institution> institutions = institutionDao.getAll();
	        System.out.println("DAOから取得した institution リスト: " + institutions);

	        // 結果をリクエストに設定
	        req.setAttribute("institutions", institutions);
	    } catch (Exception e) {
	    	System.err.println("エラー詳細: " + e);
	        e.printStackTrace();
	        throw e; // 必要に応じて例外を再スロー
	    }

	    req.getRequestDispatcher("staff_institution_list.jsp").forward(req, res);
	}
}