package user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BookmarkListDao;
import dao.SuggestListDao;
import tool.Action;

public class BookmarkCreateAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // DAOの初期化
        SuggestListDao suggestListDao = new SuggestListDao();
        BookmarkListDao blistDao = new BookmarkListDao();
/*

        // リクエストパラメータを取得
        String id = req.getParameter("id");
        String name = req.getParameter("name");  // 支援名
        String detail = req.getParameter("detail");  // 支援詳細

        // Suggestオブジェクトを作成して、リクエストパラメータを設定
        Bookmark bookmark = new Bookmark();
        Bookmark.setName(name);  // 支援名をセット
        Bookmark.setDetail(detail);  // 支援詳細をセット
   */
        String idParam = req.getParameter("id");
        Integer InstitutionID = Integer.parseInt(idParam);

        HttpSession session = req.getSession();

        Integer userID = (Integer) session.getAttribute("userID");

        System.out.print(InstitutionID);
        System.out.print(userID);



        // SuggestListDaoでデータベースに登録
       suggestListDao.insert(userID,InstitutionID);  // 新しい支援をデータベースに挿入

        // JSPへフォワード（登録完了画面など）
        req.getRequestDispatcher("bookmark_create.jsp").forward(req, res);
    }
}
