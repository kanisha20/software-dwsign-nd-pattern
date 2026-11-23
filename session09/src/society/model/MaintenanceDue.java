package society.model;

/**
 * Model representing a Monthly Maintenance Due.
 */
public class MaintenanceDue {
    private String flatNo;
    private String residentName;
    private String month;
    private double amount;

    public MaintenanceDue() {}

    public MaintenanceDue(String flatNo, String residentName, String month, double amount) {
        this.flatNo = flatNo;
        this.residentName = residentName;
        this.month = month;
        this.amount = amount;
    }

    public String getFlatNo() { return flatNo; }
    public void setFlatNo(String flatNo) { this.flatNo = flatNo; }

    public String getResidentName() { return residentName; }
    public void setResidentName(String residentName) { this.residentName = residentName; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
