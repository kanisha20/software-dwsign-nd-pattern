package pg.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

/**
 * ============================================================
 *  TASK 1 & 2: PgMailer (JavaMail API Integration)
 * ============================================================
 *  - Sends HTML enquiry email over smtp.gmail.com:587 with STARTTLS.
 *  - Reads MAIL_USER and MAIL_APP_PASSWORD from environment variables.
 *  - Always prints message via message.writeTo(System.out).
 *  - Skips actual network dispatch if env vars are missing (print-only mode).
 *  - Sends House Rules with attachment using MimeMultipart.
 * ============================================================
 */
public class PgMailer {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    /**
     * Helper: builds mail Session.
     */
    private static Session getMailSession(final String username, final String password) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        if (username != null && password != null) {
            return Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        } else {
            // Default session for print/offline mode
            return Session.getInstance(props, null);
        }
    }

    /**
     * Task 1: Sends HTML email containing PG details in a table.
     */
    public static void sendEnquiryMail(String toEmail, String pgName, String city, int rent) {
        String mailUser = System.getenv("MAIL_USER");
        String mailPass = System.getenv("MAIL_APP_PASSWORD");

        try {
            Session session = getMailSession(mailUser, mailPass);
            Message message = new MimeMessage(session);

            String fromAddress = (mailUser != null && !mailUser.trim().isEmpty()) ? mailUser : "no-reply@pgfinder.com";
            message.setFrom(new InternetAddress(fromAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Your enquiry for " + pgName);

            // Build HTML body with table
            String htmlContent = "<div style='font-family: Arial, sans-serif; line-height: 1.6; color: #333; max-width: 600px; border: 1px solid #ddd; padding: 20px; border-radius: 8px;'>"
                    + "<h2 style='color: #0056b3; border-bottom: 2px solid #0056b3; padding-bottom: 8px;'>PgFinder - Enquiry Confirmation</h2>"
                    + "<p>Hello,</p>"
                    + "<p>Thank you for expressing interest in our PG accommodation. Here are the details of the listing you enquired about:</p>"
                    + "<table style='width: 100%; border-collapse: collapse; margin-top: 15px; margin-bottom: 15px;'>"
                    + "<tr style='background-color: #f2f2f2;'>"
                    + "<th style='padding: 10px; border: 1px solid #ddd; text-align: left;'>Field</th>"
                    + "<th style='padding: 10px; border: 1px solid #ddd; text-align: left;'>Details</th>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style='padding: 10px; border: 1px solid #ddd; font-weight: bold;'>PG Name</td>"
                    + "<td style='padding: 10px; border: 1px solid #ddd;'>" + pgName + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style='padding: 10px; border: 1px solid #ddd; font-weight: bold;'>City</td>"
                    + "<td style='padding: 10px; border: 1px solid #ddd;'>" + city + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td style='padding: 10px; border: 1px solid #ddd; font-weight: bold;'>Monthly Rent</td>"
                    + "<td style='padding: 10px; border: 1px solid #ddd;'>₹ " + rent + "</td>"
                    + "</tr>"
                    + "</table>"
                    + "<p>Our property manager will get in touch with you shortly.</p>"
                    + "<p style='margin-top: 20px; color: #777; font-size: 12px;'>Best regards,<br>Team PgFinder</p>"
                    + "</div>";

            message.setContent(htmlContent, "text/html; charset=utf-8");

            // Hint: call message.saveChanges() before writeTo()
            message.saveChanges();

            System.out.println("\n========== [MAIL STREAM: writeTo(System.out)] ==========");
            message.writeTo(System.out);
            System.out.println("========================================================\n");

            // Network dispatch check
            if (mailUser == null || mailPass == null || mailUser.trim().isEmpty() || mailPass.trim().isEmpty()) {
                System.out.println("[PgMailer] MAIL_USER or MAIL_APP_PASSWORD not set in environment. Skipped network send.");
            } else {
                Transport.send(message);
                System.out.println("[PgMailer] Enquiry email sent successfully to: " + toEmail);
            }

        } catch (Exception e) {
            System.err.println("[PgMailer ERROR in sendEnquiryMail]: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Task 2: Sends HTML email with attached pg_house_rules.txt using MimeMultipart.
     */
    public static void sendHouseRulesMail(String toEmail, String pgName) {
        String mailUser = System.getenv("MAIL_USER");
        String mailPass = System.getenv("MAIL_APP_PASSWORD");

        try {
            Session session = getMailSession(mailUser, mailPass);
            Message message = new MimeMessage(session);

            String fromAddress = (mailUser != null && !mailUser.trim().isEmpty()) ? mailUser : "no-reply@pgfinder.com";
            message.setFrom(new InternetAddress(fromAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Your enquiry for " + pgName + " - House Rules Attached");

            // MimeMultipart for HTML body + file attachment
            Multipart multipart = new MimeMultipart();

            // 1. Body Part: HTML text
            MimeBodyPart htmlPart = new MimeBodyPart();
            String htmlContent = "<div style='font-family: Arial, sans-serif; color: #333; line-height: 1.5;'>"
                    + "<h2 style='color: #0056b3;'>PgFinder - Important House Rules</h2>"
                    + "<p>Dear Student,</p>"
                    + "<p>Attached with this email are the <strong>10 Mandatory House Rules</strong> for staying at <strong>" + pgName + "</strong>.</p>"
                    + "<p>Please review the attached document <code>pg_house_rules.txt</code> carefully prior to your visit.</p>"
                    + "<br><p>Best regards,<br>PgFinder Support Team</p>"
                    + "</div>";
            htmlPart.setContent(htmlContent, "text/html; charset=utf-8");
            multipart.addBodyPart(htmlPart);

            // 2. Body Part: Attachment
            MimeBodyPart attachmentPart = new MimeBodyPart();

            // Locate rules file (checks current working directory, classpath, and project path)
            File rulesFile = new File("pg_house_rules.txt");
            if (!rulesFile.exists()) {
                rulesFile = new File("C:/Users/dipak/Desktop/project 2/Q6_Mail_Integration/PgFinder/pg_house_rules.txt");
            }

            if (rulesFile.exists()) {
                attachmentPart.attachFile(rulesFile);
                attachmentPart.setFileName("pg_house_rules.txt");
                multipart.addBodyPart(attachmentPart);
            } else {
                System.out.println("[PgMailer] Warning: pg_house_rules.txt not found on disk, sending body only.");
            }

            message.setContent(multipart);
            message.saveChanges();

            System.out.println("\n========== [HOUSE RULES MAIL STREAM: writeTo(System.out)] ==========");
            message.writeTo(System.out);
            System.out.println("===================================================================\n");

            if (mailUser == null || mailPass == null || mailUser.trim().isEmpty() || mailPass.trim().isEmpty()) {
                System.out.println("[PgMailer] MAIL_USER or MAIL_APP_PASSWORD not set in environment. Skipped network send.");
            } else {
                Transport.send(message);
                System.out.println("[PgMailer] House rules email sent with attachment to: " + toEmail);
            }

        } catch (Exception e) {
            System.err.println("[PgMailer ERROR in sendHouseRulesMail]: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
