package tiffin.dao;

import tiffin.model.TiffinPlan;
import java.util.List;

/** TiffinPlanDao — same as Q3 (with findById) */
public interface TiffinPlanDao {
    List<TiffinPlan> findAll();
    List<TiffinPlan> findByCity(String city);
    void addPlan(TiffinPlan plan);
    TiffinPlan findById(int planId);
}
