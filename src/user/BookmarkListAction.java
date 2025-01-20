package user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Bookmark;
import dao.BookmarkListDao;
import tool.Action;

public class BookmarkListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session=req.getSession();
        Integer userID = (Integer) session.getAttribute("userID");
		req.setAttribute("UserID",userID);

		 // DAOの初期化
        BookmarkListDao bookmarklistDao = new BookmarkListDao();

        // 全件取得メソッドを使用
        List<Bookmark> bookmarklists = bookmarklistDao.getAll();

        // 結果をリクエストに設定
        req.setAttribute("bookmarklists", bookmarklists);


		req.getRequestDispatcher("bookmark_list.jsp").forward(req, res);
	}
}