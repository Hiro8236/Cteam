//package user;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import bean.User;
////import dao.BookmarkDao;
//import tool.Action;
//
//public class BookmarkAction extends Action {
//
//    @Override
//    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
//        HttpSession session = req.getSession();
//        User user = (User) session.getAttribute("userID");
//
//        // ブックマーク登録処理
//        String suggestinstitusionId = req.getParameter("suggestinstitusionId");
//
////        // ここでブックマークをデータベースに保存
////        if (suggestinstitusionId != null) {
////            BookmarkDao bookmarkDao = new BookmarkDao();
////            boolean result = bookmarkDao.addBookmark(user.getEmailAddress(), suggestinstitusionId);
////
////            if (result) {
////                // 登録成功
////                req.setAttribute("message", "ブックマークが登録されました！");
////            } else {
////                // 登録失敗
////                req.setAttribute("message", "ブックマーク登録に失敗しました。");
////            }
//        }
//
//        req.getRequestDispatcher("suggest_institusion.jsp").forward(req, res);
//    }
