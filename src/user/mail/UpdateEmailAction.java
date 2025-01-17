package user.mail;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import tool.Action;

public class UpdateEmailAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // JSONレスポンスを返すための設定
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();

        try {
            req.setCharacterEncoding("UTF-8");

            // メールアドレスを取得
            String emailAddress = req.getParameter("EmailAddress");
            List<String> errors = new ArrayList<>();

            // 入力チェック
            if (emailAddress == null || emailAddress.trim().isEmpty()) {
                errors.add("メールアドレスを入力してください。");
            } else if (!emailAddress.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                errors.add("正しい形式のメールアドレスを入力してください。");
            }

            if (!errors.isEmpty()) {
                sendErrorResponse(out, String.join(", ", errors));
                return;
            }

            UserDao userDao = new UserDao();

            // メールアドレスが登録済みかチェック
            if (userDao.isEmailExists(emailAddress)) {
                sendErrorResponse(out, "このメールアドレスは既に登録されています。");
                return;
            }

            // 6桁のOTPを生成
            String otp = generateOTP();

            // OTPを保存（メールアドレスベースで）
            try {
                userDao.saveUpdateOTP(emailAddress, otp);
                System.out.println("OTP保存成功。EmailAddress: " + emailAddress + ", OTP: " + otp);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("OTPの保存に失敗しました。");
            }



            // OTPをメールで送信
            boolean isSent = sendEmail(emailAddress, otp);
            if (isSent) {
                sendSuccessResponse(out, "認証コードをメールで送信しました。ご確認ください。");
            } else {
                sendErrorResponse(out, "メール送信に失敗しました。再度お試しください。");
            }
        } catch (Exception e) {
            e.printStackTrace(); // エラーログを出力
            sendErrorResponse(out, "エラーが発生しました。");
        } finally {
            out.flush();
        }
    }

    // 6桁のOTPを生成
    private String generateOTP() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }

    // メール送信処理
    private boolean sendEmail(String emailAddress, String otp) {
        final String smtpHost = "smtp.gmail.com";
        final String smtpPort = "587";
        final String senderEmail = "cteam256000@gmail.com";
        final String senderPassword = "diiv akzn xshb kofp"; // アプリパスワードを使用

        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", smtpPort);
            props.put("mail.debug", "true"); // デバッグログを有効化

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress));
            message.setSubject("認証コードのご案内");
            message.setText("以下の認証コードを入力し、登録を完了してください。\n\n認証コード: " + otp);

            Transport.send(message);

            System.out.println("メール送信成功: " + emailAddress);
            return true;

        } catch (Exception e) {
            System.err.println("メール送信に失敗しました: " + e.getMessage());
            e.printStackTrace();
            return false;
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
