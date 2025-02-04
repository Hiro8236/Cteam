package staff.normalstaff.notification;

import java.io.PrintWriter;
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

import bean.Notification;
import dao.NotificationDao;
import tool.Action;

public class StaffNotificationCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // JSONレスポンスを返すための設定
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            request.setCharacterEncoding("UTF-8");

            // フォームの値を取得
            String title = request.getParameter("notification_title");
            String detail = request.getParameter("notification_detail");

            if (title == null || title.trim().isEmpty() || detail == null || detail.trim().isEmpty()) {
                sendErrorResponse(out, "件名と詳細を入力してください。");
                return;
            }

            // データベースに通知を登録
            Notification notification = new Notification();
            notification.setTitle(title);
            notification.setDetail(detail);

            NotificationDao dao = new NotificationDao();
            dao.insert(notification);

            // メール送信処理
            List<String> emailList = dao.getAllEmails();
            if (emailList.isEmpty()) {
                sendErrorResponse(out, "送信するメールアドレスがありません。");
                return;
            }

            int successCount = 0;
            for (String email : emailList) {
                boolean isSent = sendEmail(email, title, detail);
                if (isSent) {
                    successCount++;
                }
            }

            if (successCount > 0) {
                sendSuccessResponse(out, successCount + " 件のメールを送信しました。お知らせが投稿されました。");
                // メール送信後、お知らせ一覧画面にリダイレクト
                response.sendRedirect("StaffNotification.action");
            } else {
                sendErrorResponse(out, "メール送信に失敗しました。");
            }

        } catch (Exception e) {
            e.printStackTrace(); // エラーログを出力
            sendErrorResponse(out, "エラーが発生しました。");
        } finally {
            out.flush();
        }
    }

    // メール送信処理
    private boolean sendEmail(String emailAddress, String title, String detail) {
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
            message.setSubject(title);
            message.setText(detail);

            Transport.send(message);

            System.out.println("メール送信成功: " + emailAddress);
            return true;

        } catch (Exception e) {
            System.err.println("メール送信に失敗しました: " + emailAddress + " - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void sendSuccessResponse(PrintWriter out, String message) {
        out.print("{\"success\":true,\"message\":\"" + message + "\"}");
    }

    private void sendErrorResponse(PrintWriter out, String message) {
        out.print("{\"success\":false,\"message\":\"" + message + "\"}");
    }
}
