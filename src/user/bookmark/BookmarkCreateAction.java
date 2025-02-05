package user.bookmark;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Bookmark;
import dao.BookmarkListDao;
import tool.Action;

public class BookmarkCreateAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // DAOの初期化
        BookmarkListDao bookmarkListDao = new BookmarkListDao();

        // リクエストパラメータの取得と検証
        String idParam = req.getParameter("institutionID");
        if (idParam == null || idParam.isEmpty()) {
            throw new IllegalArgumentException("institutionIDパラメータが指定されていません。");
        }

        // 数値に変換
        Integer institutionID;
        try {
            institutionID = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("institutionIDパラメータが数値として解釈できません: " + idParam, e);
        }

        // セッションからuserIDを取得
        HttpSession session = req.getSession(false); // セッションが存在しない場合はnullを返す
        if (session == null || session.getAttribute("userID") == null) {
            throw new IllegalStateException("ユーザーがログインしていません。");
        }
        Integer userID = (Integer) session.getAttribute("userID");

        // 重複チェック
        if (bookmarkListDao.isBookmarkExist(userID, institutionID)) {
            // 重複があればエラーメッセージを表示
            req.setAttribute("errorMessage", "このブックマークは既に登録されています。");
            req.getRequestDispatcher("bookmark_create.jsp").forward(req, res);
            return;
        }

        // Bookmarkオブジェクトを作成
        Bookmark bookmark = new Bookmark();
        bookmark.setUserID(userID);  // userIDを設定
        bookmark.setInstitutionID(institutionID);  // institutionIDを設定

        // DAOを使ってデータベースに登録
        boolean isInserted = bookmarkListDao.insert(bookmark);

        // 挿入結果に応じて適切な画面に遷移
        if (isInserted) {
            // 登録が成功した場合、登録完了画面などへフォワード
            req.getRequestDispatcher("bookmark_create.jsp").forward(req, res);
        } else {
            // 失敗した場合、エラーメッセージを表示するページに遷移
            req.getRequestDispatcher("error.jsp").forward(req, res);
        }
    }
}
