package user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Bookmark;
import dao.BookmarkListDao;
import tool.Action;

public class BookmarkDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        BookmarkListDao bDao = new BookmarkListDao(); // BookmarkDaoの初期化

        // リクエストパラメータからBookmarkIDを取得
        String bookmarkIDParam = req.getParameter("BookmarkID");
        System.out.println(bookmarkIDParam);

        // ログ出力の代わりに適切なエラーメッセージを表示
        if (bookmarkIDParam == null || bookmarkIDParam.isEmpty()) {
            req.setAttribute("message", "No BookmarkID provided.");
            req.getRequestDispatcher("bookmark_delete_done.jsp").forward(req, res);
            return;
        }

        try {
            // BookmarkIDが存在する場合、整数に変換
            int bookmarkID = Integer.parseInt(bookmarkIDParam);

            // BookmarkIDを使って、対応するBookmarkを取得
            Bookmark bookmark = bDao.findByID(bookmarkID);

            if (bookmark != null) {
                // bookmarkが取得できた場合は削除処理を実行
                boolean isDeleted = bDao.delete(bookmark);

                // 成功メッセージまたは失敗メッセージを設定
                if (isDeleted) {
                    req.setAttribute("message", "Bookmark deleted successfully.");
                } else {
                    req.setAttribute("message", "Failed to delete bookmark.");
                }

            } else {
                // bookmarkが見つからない場合
                req.setAttribute("message", "Bookmark not found.");
            }

        } catch (NumberFormatException e) {
            // もしIntegerに変換できなかった場合
            req.setAttribute("message", "Invalid BookmarkID format.");
        } catch (Exception e) {
            // その他の例外処理
            req.setAttribute("message", "An error occurred while deleting the bookmark.");
        }

        // 結果ページにフォワード
        req.getRequestDispatcher("bookmark_delete_done.jsp").forward(req, res);
    }
}
