package tiffin.dao;

import tiffin.model.DashboardStats;
import tiffin.model.Order;
import java.util.List;

/**
 * ============================================================
 *  Q4 TASK 2 — ORDER DAO INTERFACE
 * ============================================================
 *  Orders ke liye data access methods.
 * ============================================================
 */
public interface OrderDao {

    // Saare orders laao (Task 2)
    List<Order> findAll();

    // Dashboard stats laao: total, pending, revenue (Task 3)
    DashboardStats getDashboardStats();
}
