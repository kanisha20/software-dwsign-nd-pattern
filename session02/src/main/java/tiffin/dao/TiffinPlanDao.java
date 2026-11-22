package tiffin.dao;

import java.util.List;
import tiffin.model.TiffinPlan;

public interface TiffinPlanDao {

    List<TiffinPlan> findAll() throws Exception;

    List<TiffinPlan> findByCity(String city) throws Exception;

    void addPlan(TiffinPlan plan) throws Exception;
}
