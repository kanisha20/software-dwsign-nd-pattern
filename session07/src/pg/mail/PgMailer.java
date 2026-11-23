package pg.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * ============================================================
 *  TASK 2: PgMailer (Added sendOtpMail method)
 * ============================================================
 *  Sends a 6-digit OTP to the owner's email address.
 *  Uses Gmail SMTP with STARTTLS on port 587.
 *  Logs RFC 822 stream using message.writeTo(System.out).
 * ============================================================
 */
public class PgMailer {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    private static Session getMailSession(final String username, final String password) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        if (username != null && password != null && !username.trim().isEmpty() && !password.trim().isEmpty()) {
            return Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        } else {
            return Session.getInstance(props, null);
        }
    }

    /**
     * Sends OTP authentication email.
     */
    public static void sendOtpMail(String toEmail, String otp) {
        String mailUser = System.getenv("MAIL_USER");
        String mailPass = System.getenv("MAIL_APP_PASSWORD");

        try {
            Session session = getMailSession(mailUser, mailPass);
            Message message = new MimeMessage(session);

            String from = (mailUser != null && !mailUser.trim().isEmpty()) ? mailUser : "security@pgfinder.com";
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Your PgFinder Owner Login OTP: " + otp);

            String html = "<div style='font-family: Arial, sans-serif; padding: 20px; border: 1px solid #e0e0e0; border-radius: 8px; max-width: 500px;'>"
                    + "<h2 style='color: #0056b3; margin-top: 0;'>🔐 PgFinder Owner Portal Login</h2>"
                    + "<p>Dear Owner,</p>"
                    + "<p>Use the following One-Time Password (OTP) to complete your 2-Step verification login:</p>"
                    + "<div style='background-color: #f8f9fa; border: 1px dashed #0056b3; padding: 15px; text-align: center; margin: 20px 0;'>"
                    + "<span style='font-size: 32px; font-weight: bold; letter-spacing: 5px; color: #0056b3;'>" + otp + "</span>"
                    + "</div>"
                    + "<p style='color: #666; font-size: 13px;'>This OTP is confidential. Please do not share it with anyone.</p>"
                    + "<p style='color: #888; font-size: 12px; margin-top: 25px;'>Team PgFinder Security</p>"
                    + "</div>";

            message.setContent(html, "text/html; charset=utf-8");
            message.saveChanges();

            System.out.println("\n========== [OTP EMAIL STREAM: writeTo(System.out)] ==========");
            message.writeTo(System.out);
            System.out.println("=============================================================\n");

            if (mailUser == null || mailPass == null || mailUser.trim().isEmpty() || mailPass.trim().isEmpty()) {
                System.out.println("[PgMailer] MAIL_USER or MAIL_APP_PASSWORD not set in env. Simulation mode: OTP logged to console.");
            } else {
                Transport.send(message);
                System.out.println("[PgMailer] OTP successfully emailed to: " + toEmail);
            }

        } catch (Exception e) {
            System.err.println("[PgMailer OTP Error]: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
