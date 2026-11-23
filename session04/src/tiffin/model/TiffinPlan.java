package tiffin.model;

/** TiffinPlan Bean — same as Q2/Q3 */
public class TiffinPlan {
    private int planId; private String planName; private double pricePerDay; private String city;
    public TiffinPlan() {}
    public TiffinPlan(int planId, String planName, double pricePerDay, String city) {
        this.planId = planId; this.planName = planName; this.pricePerDay = pricePerDay; this.city = city;
    }
    public int getPlanId() { return planId; } public void setPlanId(int v) { planId = v; }
    public String getPlanName() { return planName; } public void setPlanName(String v) { planName = v; }
    public double getPricePerDay() { return pricePerDay; } public void setPricePerDay(double v) { pricePerDay = v; }
    public String getCity() { return city; } public void setCity(String v) { city = v; }
    @Override public String toString() { return planId+" | "+planName+" | Rs."+pricePerDay+"/day | "+city; }
}
