package pg.mail;

/**
 * ============================================================
 *  STANDALONE TEST: MailTestDemo
 * ============================================================
 *  Demonstrates Task 1 & Task 2 directly via main() without server.
 *  Prints complete RFC 822 MIME message streams (including headers,
 *  MimeMultipart boundaries, and attached file metadata).
 * ============================================================
 */
public class MailTestDemo {
    public static void main(String[] args) {
        System.out.println(">>> Starting MailTestDemo for Session 6 Tasks <<<\n");

        String testEmail = "student.demo@example.com";
        String pgName = "Shiv Kripa PG & Rooms";
        String city = "Surat";
        int rent = 5500;

        System.out.println("--- 1. Testing Task 1: sendEnquiryMail ---");
        PgMailer.sendEnquiryMail(testEmail, pgName, city, rent);

        System.out.println("\n--- 2. Testing Task 2: sendHouseRulesMail with attachment ---");
        PgMailer.sendHouseRulesMail(testEmail, pgName);

        System.out.println("\n>>> MailTestDemo Completed Successfully <<<");
    }
}
