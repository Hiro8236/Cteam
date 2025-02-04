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
        HttpSession session = req.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        // 未ログインの場合はメッセージのみ表示
        if (userID == null) {
            req.setAttribute("loginMessage", "ログインしてください");
            req.getRequestDispatcher("bookmark_list.jsp").forward(req, res);
            return;
        }

        req.setAttribute("UserID", userID);

        // DAOの初期化
        BookmarkListDao bookmarklistDao = new BookmarkListDao();

        // ログインユーザーのブックマークのみ取得
        List<Bookmark> bookmarklists = bookmarklistDao.getByUserID(userID);

        // 結果をリクエストに設定
        req.setAttribute("bookmarklists", bookmarklists);

        req.getRequestDispatcher("bookmark_list.jsp").forward(req, res);
    }
}
