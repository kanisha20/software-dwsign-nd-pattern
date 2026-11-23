package society.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ============================================================
 *  TASK 1: MaintenanceCalculatorTest
 * ============================================================
 *  5 Automated JUnit 5 Unit Tests:
 *    1. (2000, 5)  -> gives 2000  (Within grace period)
 *    2. (2000, 10) -> gives 2000  (Exactly on day 10 threshold)
 *    3. (2000, 11) -> gives 2060  (50 + 10 = 60 late fee)
 *    4. (2000, 40) -> gives 2300  (50 + 300 = 350, capped at 300)
 *    5. (2000, -1) -> throws IllegalArgumentException
 * ============================================================
 */
public class MaintenanceCalculatorTest {

    @Test
    @DisplayName("Test 1: (2000, 5) gives 2000 - No late fee within grace period")
    void testWithinGracePeriod() {
        int result = MaintenanceCalculator.totalDue(2000, 5);
        assertEquals(2000, result, "5 days late should incur no fee");
    }

    @Test
    @DisplayName("Test 2: (2000, 10) gives 2000 - Exactly on day 10 boundary")
    void testAtGracePeriodBoundary() {
        int result = MaintenanceCalculator.totalDue(2000, 10);
        assertEquals(2000, result, "10 days late should incur no fee");
    }

    @Test
    @DisplayName("Test 3: (2000, 11) gives 2060 - ₹50 + (1 * ₹10) late fee")
    void testOneDayBeyondGracePeriod() {
        int result = MaintenanceCalculator.totalDue(2000, 11);
        assertEquals(2060, result, "11 days late should incur 50 + 10 = 60 fee");
    }

    @Test
    @DisplayName("Test 4: (2000, 40) gives 2300 - Capped at ₹300 maximum late fee")
    void testLateFeeCappedAt300() {
        int result = MaintenanceCalculator.totalDue(2000, 40);
        assertEquals(2300, result, "40 days late fee should be capped at 300");
    }

    @Test
    @DisplayName("Test 5: (2000, -1) throws IllegalArgumentException for negative input")
    void testNegativeDaysLateThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            MaintenanceCalculator.totalDue(2000, -1);
        }, "Negative days late must throw IllegalArgumentException");
    }
}
