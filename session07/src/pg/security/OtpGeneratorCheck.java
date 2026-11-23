package pg.security;

import java.util.HashSet;
import java.util.Set;

/**
 * ============================================================
 *  TASK 1: OtpGeneratorCheck
 * ============================================================
 *  Calls generateOtp() 10,000 times.
 *  Verifies:
 *    1. Count of results not exactly 6 digits (MUST BE 0).
 *    2. Count of distinct unique values generated.
 * ============================================================
 */
public class OtpGeneratorCheck {

    public static void main(String[] args) {
        System.out.println("========== Starting OTP Generator Verification ==========");
        int totalIterations = 10000;
        int notSixDigitsCount = 0;
        Set<String> uniqueOtps = new HashSet<>();

        for (int i = 0; i < totalIterations; i++) {
            String otp = OtpGenerator.generateOtp();

            // Check if exactly 6 digits
            if (otp == null || otp.length() != 6 || !otp.matches("\\d{6}")) {
                notSixDigitsCount++;
            }

            uniqueOtps.add(otp);
        }

        System.out.println("Total iterations               : " + totalIterations);
        System.out.println("OTPs not exactly 6 digits      : " + notSixDigitsCount + " (Expected: 0)");
        System.out.println("Distinct unique OTPs generated : " + uniqueOtps.size() + " / " + totalIterations);

        if (notSixDigitsCount == 0) {
            System.out.println(">>> PASS: All 10,000 OTPs are valid 6-digit numbers! <<<");
        } else {
            System.err.println(">>> FAIL: Found invalid OTPs! <<<");
        }
    }
}
