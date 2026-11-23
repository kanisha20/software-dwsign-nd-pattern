package pg.security;

import java.security.SecureRandom;

/**
 * ============================================================
 *  TASK 1: OtpGenerator
 * ============================================================
 *  Generates a cryptographically strong 6-digit OTP string
 *  between 100000 and 999999 using SecureRandom.
 * ============================================================
 */
public class OtpGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Generates a 6-digit OTP.
     * Range: 100000 to 999999 inclusive.
     */
    public static String generateOtp() {
        // SecureRandom nextInt(max - min + 1) + min
        // 999999 - 100000 + 1 = 900000
        int otpNumber = 100000 + RANDOM.nextInt(900000);
        return String.valueOf(otpNumber);
    }
}
