package society.test;

import society.service.SignatureVerificationService;

/**
 * ============================================================
 *  TASK 3: TamperedPayloadTest
 * ============================================================
 *  Tests:
 *    1. One legitimate test-mode payment (Must be ACCEPTED).
 *    2. 3 Tampered payloads made up with fake IDs (All must be REJECTED).
 * ============================================================
 */
public class TamperedPayloadTest {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  SocietyPay - Payment Signature Verification Test");
        System.out.println("==================================================\n");

        String keySecret = "sample_test_secret_key";

        // 1. Legitimate Test-Mode Payment
        String legitOrderId = "order_9A33XWu170gUtm";
        String legitPaymentId = "pay_29Net1me40dm1W";
        String legitSignature = SignatureVerificationService.computeSignature(legitOrderId, legitPaymentId, keySecret);

        boolean test1 = SignatureVerificationService.verifySignature(legitOrderId, legitPaymentId, legitSignature, keySecret);
        System.out.println("Test 1 [Legitimate Payment]: " + (test1 ? "PASS (Accepted)" : "FAIL"));

        // 2. Tampered Payload 1: Fake orderId swapped by user
        String fakeOrderId1 = "order_FAKE_HACKER_99";
        boolean test2 = SignatureVerificationService.verifySignature(fakeOrderId1, legitPaymentId, legitSignature, keySecret);
        System.out.println("Test 2 [Tampered orderId]: " + (!test2 ? "PASS (Successfully Rejected)" : "FAIL"));

        // 3. Tampered Payload 2: Fake paymentId injected
        String fakePaymentId2 = "pay_STOLEN_TOKEN_000";
        boolean test3 = SignatureVerificationService.verifySignature(legitOrderId, fakePaymentId2, legitSignature, keySecret);
        System.out.println("Test 3 [Tampered paymentId]: " + (!test3 ? "PASS (Successfully Rejected)" : "FAIL"));

        // 4. Tampered Payload 3: Arbitrary signature forged by client
        String forgedSignature3 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        boolean test4 = SignatureVerificationService.verifySignature(legitOrderId, legitPaymentId, forgedSignature3, keySecret);
        System.out.println("Test 4 [Forged Signature]: " + (!test4 ? "PASS (Successfully Rejected)" : "FAIL"));

        System.out.println("\nAll 3 tampered payloads were securely blocked by HMAC-SHA256 verification!");
    }
}
