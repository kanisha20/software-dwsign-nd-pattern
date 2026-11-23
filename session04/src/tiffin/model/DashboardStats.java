package tiffin.model;

/**
 * ============================================================
 *  Q4 TASK 3 — DASHBOARD STATS (Bean)
 * ============================================================
 *  Dashboard pe 3 cards dikhane ke liye data rakhta hai:
 *    - Total Orders
 *    - Pending Orders
 *    - Total Revenue (Rs.)
 * ============================================================
 */
public class DashboardStats {

    private int    totalOrders;
    private int    pendingOrders;
    private double totalRevenue;

    public DashboardStats() { }

    public DashboardStats(int totalOrders, int pendingOrders, double totalRevenue) {
        this.totalOrders   = totalOrders;
        this.pendingOrders = pendingOrders;
        this.totalRevenue  = totalRevenue;
    }

    public int    getTotalOrders()   { return totalOrders;   }
    public int    getPendingOrders() { return pendingOrders; }
    public double getTotalRevenue()  { return totalRevenue;  }

    public void setTotalOrders(int v)    { totalOrders = v;   }
    public void setPendingOrders(int v)  { pendingOrders = v; }
    public void setTotalRevenue(double v){ totalRevenue = v;  }
}
