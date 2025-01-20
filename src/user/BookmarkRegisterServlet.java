//package user;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import bean.Bookmark;
//import bean.Suggest;
//import dao.BookmarkListDao;
//import dao.SuggestListDao;
//
//@WebServlet("/bookmark/register")
//public class BookmarkRegisterServlet extends HttpServlet {
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String suggestId = request.getParameter("suggestId");
//
//        try {
//            // Suggest テーブルからデータを取得
//            SuggestListDao suggestDao = new SuggestListDao();
//            int id = Integer.parseInt(suggestId); // 数値に変換
//            Suggest suggest = suggestDao.getSuggestById(id); // 整数型を渡す
//
//            // Suggest を Bookmark に変換
//            Bookmark bookmark = new Bookmark();
//            bookmark.setName(suggest.getName());
//            bookmark.setDetail(suggest.getDetail());
//
//            System.out.println("Bookmark Name: " + bookmark.getName());
//            System.out.println("Bookmark Detail: " + bookmark.getDetail());
//
//
//            // Bookmark テーブルにデータを登録
//            BookmarkListDao bookmarkDao = new BookmarkListDao();
//            bookmarkDao.insert(bookmark);
//
//            request.setAttribute("message", "登録が完了しました。");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            request.setAttribute("message", "エラーが発生しました。");
//        }
//
//
//        // 登録完了後のページにフォワード
//        request.getRequestDispatcher("bookmark_list.jsp").forward(request, response);
//    }
//}
