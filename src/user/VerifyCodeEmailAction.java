package user;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import tool.Action;

public class VerifyCodeEmailAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // JSONレスポンスを返す設定
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();

        try {
            req.setCharacterEncoding("UTF-8");

            // リクエストからメールアドレスと認証コードを取得
            String emailAddress = req.getParameter("EmailAddress");
            String authCode = req.getParameter("AuthenticationCode");

            // デバッグログ
            System.out.println("Received EmailAddress: " + emailAddress);
            System.out.println("Received AuthenticationCode: " + authCode);

            // 入力チェック
            if (emailAddress == null || authCode == null || emailAddress.trim().isEmpty() || authCode.trim().isEmpty()) {
                sendErrorResponse(out, "メールアドレスまたは認証コードが入力されていません。");
                return;
            }

            UserDao userDao = new UserDao();

            HttpSession session = req.getSession();
            Integer userID = (Integer) session.getAttribute("userID");

            // 認証コードが正しいかチェック
            boolean isValid = userDao.EmailOTP(userID,emailAddress, authCode);

            // デバッグログ
            System.out.println("Validation result for Email: " + emailAddress + " is " + isValid);

            if (isValid) {
                // 認証成功
                sendSuccessResponse(out, "認証に成功しました。");
            } else {
                // 認証失敗
                sendErrorResponse(out, "認証コードが一致しません。もう一度確認してください。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(out, "エラーが発生しました。");
        } finally {
            out.flush();
        }
    }

    private void sendSuccessResponse(PrintWriter out, String message) {
        StringBuilder jsonResponse = new StringBuilder();
        jsonResponse.append("{");
        jsonResponse.append("\"success\":true,");
        jsonResponse.append("\"message\":\"").append(message).append("\"");
        jsonResponse.append("}");
        out.print(jsonResponse.toString());
    }

    private void sendErrorResponse(PrintWriter out, String message) {
        StringBuilder jsonResponse = new StringBuilder();
        jsonResponse.append("{");
        jsonResponse.append("\"success\":false,");
        jsonResponse.append("\"message\":\"").append(message).append("\"");
        jsonResponse.append("}");
        out.print(jsonResponse.toString());
    }
}
