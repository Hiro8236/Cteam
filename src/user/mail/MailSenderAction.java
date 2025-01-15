package user.mail;

import java.io.FileReader;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class MailSenderAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // プロパティファイルから認証に使用するデータを取得
        Properties prop = new Properties();
        prop.load(new FileReader("C:\\work\\pleiades\\workspace\\Cteam1\\WebContent\\META-INF\\mail.properties"));

        // 送信元のGmailアドレス
        final String username = prop.getProperty("mailaddress");
        // Gmailのアカウントのアプリパスワード
        final String password = prop.getProperty("password");

        // SMTPサーバへの認証とメールセッションの作成
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // メール送信準備
            Message message = new MimeMessage(session);
            // 送信元の設定
            message.setFrom(new InternetAddress(username));
            // 送信先の設定
            String recipient = request.getParameter("recipient");
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            // 件名の設定
            message.setSubject("JavaMail Test");
            // 本文の設定
            message.setText("This is a test email sent from JavaMail.");

            // メールの送信
            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}
