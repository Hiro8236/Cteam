package user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Bookmark;
import dao.BookmarkListDao;
import tool.Action;

public class BookmarkDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションからuserIDを取得
        HttpSession session = req.getSession(false); // セッションが存在しない場合はnullを返す
        if (session == null || session.getAttribute("userID") == null) {
            req.setAttribute("message", "ユーザーがログインしていません。");
            req.getRequestDispatcher("login.jsp").forward(req, res);  // ログイン画面にリダイレクト
            return;
        }
        Integer userID = (Integer) session.getAttribute("userID");
        req.setAttribute("UserID", userID);

        // リクエストパラメータからBookmarkIDを取得
        String bookmarkIDParam = req.getParameter("BookmarkID");

        if (bookmarkIDParam == null || bookmarkIDParam.isEmpty()) {
            req.setAttribute("message", "No BookmarkID provided.");
        } else {
            try {
                int bookmarkID = Integer.parseInt(bookmarkIDParam);

                // Bookmarkオブジェクトを作成
                Bookmark bookmark = new Bookmark();
                bookmark.setBookmarkID(bookmarkID);

                // DAOを使用して削除
                BookmarkListDao bDao = new BookmarkListDao();
                boolean isDeleted = bDao.delete(bookmark);

                if (isDeleted) {
                    req.setAttribute("message", "Bookmark deleted successfully.");
                } else {
                    req.setAttribute("message", "Failed to delete bookmark.");
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "Invalid BookmarkID format.");
            } catch (Exception e) {
                req.setAttribute("message", "An error occurred while deleting the bookmark.");
            }
        }

        // JSPへフォワード
        req.getRequestDispatcher("bookmark_delete.jsp").forward(req, res);
    }
}
