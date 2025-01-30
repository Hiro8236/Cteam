package user.calendar;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import bean.Event;
import dao.EventDao;

public class EventNotificationService {

    public void sendEventNotifications() {
        try {
            EventDao eventDao = new EventDao();
            List<Event> events = eventDao.getEventsWithNotification();

            for (Event event : events) {
                sendEmail(event);
            }
        } catch (Exception e) {
            System.err.println("[エラー] 通知処理中に問題が発生しました: " + e.getMessage());
        }
    }

    private void sendEmail(Event event) {
        final String smtpHost = "smtp.gmail.com";
        final String smtpPort = "587";
        final String senderEmail = "cteam256000@gmail.com";
        final String senderPassword = "diiv akzn xshb kofp"; // アプリパスワード

        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", smtpPort);

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("user@example.com")); // TODO: ユーザーのメールアドレスを取得する処理を追加！
            message.setSubject("📅 イベント通知: " + event.getTitle());
            message.setText("こんにちは！\n\n" +
                    "明日は「" + event.getTitle() + "」のイベントがあります！\n" +
                    "日時: " + event.getStartTime() + "\n" +
                    "説明: " + event.getDescription() + "\n\n" +
                    "お忘れなく！✨");

            Transport.send(message);
            System.out.println("[成功] 通知メールを送信しました！📩");
        } catch (MessagingException mex) {
            System.err.println("[エラー] メール送信中にエラー発生: " + mex.getMessage());
        }
    }
}
