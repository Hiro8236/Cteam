package user.calendar;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {

    private static Properties mailProperties = new Properties();

    // プロパティを読み込むメソッド
 // プロパティを読み込むメソッド
    static {
        try {
            // `execute()` メソッドと同じ方法で `mail.properties` を読み込む
            FileReader reader = new FileReader("C:\\pleiades\\workspace\\Cteam1\\WebContent\\META-INF\\mail.properties");
            mailProperties.load(reader);
            System.out.println("[DEBUG] mail.properties の読み込み成功");
        } catch (IOException e) {
            System.err.println("[エラー] mail.properties の読み込みに失敗しました: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void sendEmail(String to, String subject, String body) {
        final String from = mailProperties.getProperty("mailaddress"); // メールアドレス
        final String password = mailProperties.getProperty("password"); // パスワード
        final String host = mailProperties.getProperty("mail.smtp.host");
        final String port = mailProperties.getProperty("mail.smtp.port");
        final boolean auth = Boolean.parseBoolean(mailProperties.getProperty("mail.smtp.auth"));
        final boolean starttls = Boolean.parseBoolean(mailProperties.getProperty("mail.smtp.starttls.enable"));

        System.out.println("[DEBUG] メール送信準備開始");
        System.out.println("[DEBUG] 送信元: " + from);
        System.out.println("[DEBUG] 送信先: " + to);
        System.out.println("[DEBUG] 件名: " + subject);
        System.out.println("[DEBUG] 本文: " + body);
        System.out.println("[DEBUG] SMTP ホスト: " + host);
        System.out.println("[DEBUG] SMTP ポート: " + port);
        System.out.println("[DEBUG] SMTP 認証: " + auth);
        System.out.println("[DEBUG] STARTTLS: " + starttls);

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", String.valueOf(auth));
        props.put("mail.smtp.starttls.enable", String.valueOf(starttls));

        // Gmail などの SMTP サーバーで TLS を使用する場合の追加設定
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.connectiontimeout", "10000"); // 10秒
        props.put("mail.smtp.timeout", "10000"); // 10秒
        props.put("mail.smtp.writetimeout", "10000"); // 10秒

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            System.out.println("[DEBUG] メール送信中...");
            Transport.send(message);
            System.out.println("[成功] メールを送信しました: " + to);
        } catch (MessagingException e) {
            System.err.println("[エラー] メール送信に失敗しました: " + e.getMessage());
            e.printStackTrace(); // 詳細なスタックトレースを出力
        }
    }
}
