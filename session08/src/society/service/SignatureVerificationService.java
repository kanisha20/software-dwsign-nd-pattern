package society.service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * ============================================================
 *  TASK 3: SignatureVerificationService
 * ============================================================
 *  Recomputes HMAC-SHA256 of `orderId + "|" + paymentId` with Mac
 *  and your key secret.
 *  Compares it to `razorpay_signature` using MessageDigest.isEqual().
 *  Converts bytes to lowercase hex with String.format("%02x", b).
 * ============================================================
 */
public class SignatureVerificationService {

    private static final String HMAC_SHA256 = "HmacSHA256";

    /**
     * Verifies the Razorpay payment signature.
     */
    public static boolean verifySignature(String orderId, String paymentId, String receivedSignature, String keySecret) {
        if (orderId == null || paymentId == null || receivedSignature == null || keySecret == null) {
            return false;
        }

        try {
            // Data string: orderId + "|" + paymentId
            String payload = orderId + "|" + paymentId;

            // Initialize Mac with SecretKeySpec
            Mac mac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keySecret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
            mac.init(secretKeySpec);

            // Compute hash
            byte[] hashBytes = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));

            // Hint: Convert bytes to lowercase hex with String.format("%02x", b)
            StringBuilder calculatedSignature = new StringBuilder();
            for (byte b : hashBytes) {
                calculatedSignature.append(String.format("%02x", b));
            }

            // Constraint: compare using MessageDigest.isEqual() to prevent timing attacks
            byte[] expectedBytes = calculatedSignature.toString().getBytes(StandardCharsets.UTF_8);
            byte[] actualBytes = receivedSignature.getBytes(StandardCharsets.UTF_8);

            return MessageDigest.isEqual(expectedBytes, actualBytes);

        } catch (Exception e) {
            System.err.println("[SignatureVerification Error]: " + e.getMessage());
            return false;
        }
    }

    /**
     * Helper to compute signature for testing
     */
    public static String computeSignature(String orderId, String paymentId, String keySecret) {
        try {
            String payload = orderId + "|" + paymentId;
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(new SecretKeySpec(keySecret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256));
            byte[] hashBytes = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }
}
