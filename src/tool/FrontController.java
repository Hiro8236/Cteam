package tool;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "*.action" })
public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            // パスを取得
            String path = req.getServletPath().substring(1);

            // ファイル名を取得しクラス名に変換
            String name = path.replace(".a", "A").replace('/', '.');

            // calendar パッケージを付与
            if (name.startsWith("staff.normalstaff.") && !name.contains(".calendar.")) {
                name = name.replace("staff.normalstaff.", "staff.normalstaff.calendar.");
            }

            // デバッグログ
            System.out.println("修正されたクラス名: " + name);

            // アクションクラスのインスタンスを返却
            Action action = (Action) Class.forName(name).getDeclaredConstructor().newInstance();

            // 遷移先URLを取得
            action.execute(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            // エラーページへリダイレクト
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
