//package user;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import bean.Suggest;
//import dao.SuggestListDao;
//
//@WebServlet("/suggest/list")
//public class SuggestListServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        try {
//            SuggestListDao dao = new SuggestListDao();
//            List<Suggest> suggestions = dao.getAll();
//
//            // データをリクエストスコープに設定
//            request.setAttribute("suggestions", suggestions);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            request.setAttribute("message", "エラーが発生しました。");
//        }
//
//        // JSP にフォワード
//        request.getRequestDispatcher("bookmark_list.jsp").forward(request, response);
//    }
//}
