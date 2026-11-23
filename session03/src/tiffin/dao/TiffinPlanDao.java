package tiffin.dao;

import tiffin.model.TiffinPlan;
import java.util.List;

/**
 * ============================================================
 *  DAO INTERFACE — Updated for Q3
 *  Naya method: findById(int planId) — Task 1 ke liye
 * ============================================================
 */
public interface TiffinPlanDao {

    List<TiffinPlan> findAll();
    List<TiffinPlan> findByCity(String city);
    void addPlan(TiffinPlan plan);

    // ---- NEW in Q3 Task 1: ID se plan laao (cart ke liye) ----
    TiffinPlan findById(int planId);
}
