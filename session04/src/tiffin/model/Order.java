package tiffin.model;

/**
 * ============================================================
 *  Q4 TASK 2 — ORDER MODEL (Bean)
 * ============================================================
 *  orders table ke columns se match karti hai:
 *  order_id, customer_name, plan_name, quantity,
 *  amount, status, order_date
 * ============================================================
 */
public class Order {

    private int    orderId;
    private String customerName;
    private String planName;
    private int    quantity;
    private double amount;
    private String status;      // "DELIVERED" or "PENDING"
    private String orderDate;   // Date as String (simple)

    public Order() { }

    public Order(int orderId, String customerName, String planName,
                 int quantity, double amount, String status, String orderDate) {
        this.orderId      = orderId;
        this.customerName = customerName;
        this.planName     = planName;
        this.quantity     = quantity;
        this.amount       = amount;
        this.status       = status;
        this.orderDate    = orderDate;
    }

    // Getters
    public int    getOrderId()      { return orderId;      }
    public String getCustomerName() { return customerName; }
    public String getPlanName()     { return planName;     }
    public int    getQuantity()     { return quantity;     }
    public double getAmount()       { return amount;       }
    public String getStatus()       { return status;       }
    public String getOrderDate()    { return orderDate;    }

    // Setters
    public void setOrderId(int v)       { orderId = v;      }
    public void setCustomerName(String v){ customerName = v; }
    public void setPlanName(String v)    { planName = v;     }
    public void setQuantity(int v)       { quantity = v;     }
    public void setAmount(double v)      { amount = v;       }
    public void setStatus(String v)      { status = v;       }
    public void setOrderDate(String v)   { orderDate = v;    }
}
