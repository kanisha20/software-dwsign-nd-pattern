package society.calculator;

/**
 * ============================================================
 *  TASK 1: MaintenanceCalculator
 * ============================================================
 *  totalDue(int baseAmount, int daysLate)
 *  Rules:
 *    - Negative inputs: throws IllegalArgumentException
 *    - Up to day 10: Late fee is 0 (nothing)
 *    - Beyond day 10: ₹50 base late fee + ₹10 for each day beyond day 10
 *    - Late fee is capped at ₹300 maximum
 * ============================================================
 */
public class MaintenanceCalculator {

    private static final int GRACE_PERIOD_DAYS = 10;
    private static final int BASE_LATE_FEE = 50;
    private static final int PER_DAY_CHARGE = 10;
    private static final int MAX_LATE_FEE = 300;

    /**
     * Calculates the total maintenance due including late fee.
     *
     * @param baseAmount The original maintenance charge
     * @param daysLate   Number of days payment is late
     * @return Total due amount
     * @throws IllegalArgumentException if baseAmount < 0 or daysLate < 0
     */
    public static int totalDue(int baseAmount, int daysLate) {
        // Negative inputs validation
        if (baseAmount < 0 || daysLate < 0) {
            throw new IllegalArgumentException("Base amount and days late cannot be negative! Found: baseAmount="
                    + baseAmount + ", daysLate=" + daysLate);
        }

        // Up to day 10: No late fee
        if (daysLate <= GRACE_PERIOD_DAYS) {
            return baseAmount;
        }

        // Days beyond day 10
        int daysBeyond = daysLate - GRACE_PERIOD_DAYS;

        // ₹50 plus ₹10 for each day beyond day 10
        int calculatedLateFee = BASE_LATE_FEE + (daysBeyond * PER_DAY_CHARGE);

        // Capped at ₹300
        int lateFee = Math.min(calculatedLateFee, MAX_LATE_FEE);

        return baseAmount + lateFee;
    }
}
