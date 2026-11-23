package tiffin.model;

/**
 * ============================================================
 *  MODEL / BEAN — TiffinPlan (Same as Q2)
 * ============================================================
 */
public class TiffinPlan {

    private int    planId;
    private String planName;
    private double pricePerDay;
    private String city;

    public TiffinPlan() { }

    public TiffinPlan(int planId, String planName, double pricePerDay, String city) {
        this.planId      = planId;
        this.planName    = planName;
        this.pricePerDay = pricePerDay;
        this.city        = city;
    }

    public int    getPlanId()      { return planId;      }
    public String getPlanName()    { return planName;    }
    public double getPricePerDay() { return pricePerDay; }
    public String getCity()        { return city;        }

    public void setPlanId(int planId)            { this.planId      = planId;      }
    public void setPlanName(String planName)      { this.planName    = planName;    }
    public void setPricePerDay(double pricePerDay){ this.pricePerDay = pricePerDay; }
    public void setCity(String city)              { this.city        = city;        }

    @Override
    public String toString() {
        return planId + " | " + planName + " | Rs." + pricePerDay + "/day | " + city;
    }
}
