package user.login;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDao;
import tool.Action;


public class LoginExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		String url = "";
		String EmailAddress = "";
		String password = "";

		System.out.println("UserDaoを生成");

		UserDao userDao = new UserDao();
		User user = null;

		//リクエストパラメータ―の取得 2
		EmailAddress = req.getParameter("EmailAddress");// メールアドレス
		password = req.getParameter("password");//パスワード

		//DBからデータ取得 3
		user = userDao.login(EmailAddress, password);//利用者データ取得

		//条件で手順4~7の内容が分岐
		if (user!= null) {
			// セッション情報を取得
			HttpSession session = req.getSession(true);
			// 認証済みフラグを立てる
			user.setAuthenticated(true);
			// セッションにログイン情報を保存
			// セッションにログイン情報を保存
			session.setAttribute("userID", user.getUserID());

			//リダイレクト
			url = "/Cteam1/user/Home.action";
			res.sendRedirect(url);
		} else {
			// 認証失敗の場合
			// エラーメッセージをセット
			List<String> errors = new ArrayList<>();
			errors.add("メールアドレスまたはパスワードが違います");
			req.setAttribute("errors", errors);

			//フォワード
			url = "Login.action";
			req.getRequestDispatcher(url).forward(req, res);
		}

	}

}
