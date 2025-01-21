package user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.SuggestListDao;
import tool.Action;

public class BookmarkCreateAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // DAOの初期化
        SuggestListDao suggestListDao = new SuggestListDao();

        // リクエストパラメータの取得と検証
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            throw new IllegalArgumentException("idパラメータが指定されていません。");
        }

        // 数値に変換
        Integer InstitutionID;
        try {
            InstitutionID = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("idパラメータが数値として解釈できません: " + idParam, e);
        }

        // セッションからuserIDを取得
        HttpSession session = req.getSession(false); // セッションが存在しない場合はnullを返す
        if (session == null || session.getAttribute("userID") == null) {
            throw new IllegalStateException("ユーザーがログインしていません。");
        }
        Integer userID = (Integer) session.getAttribute("userID");

        // DAOを使ってデータベースに登録
        suggestListDao.insert(userID, InstitutionID);

        // JSPへフォワード（登録完了画面など）
        req.getRequestDispatcher("bookmark_create.jsp").forward(req, res);
    }
}
