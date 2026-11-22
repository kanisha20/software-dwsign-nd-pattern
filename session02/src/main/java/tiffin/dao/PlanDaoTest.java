package tiffin.dao;

import java.util.List;
import tiffin.model.TiffinPlan;

public class PlanDaoTest {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/tiffindb";
        String user = "root";
        String password = "your_password"; // Change this

        try {
            TiffinPlanDao dao =
                new TiffinPlanDaoImpl(url, user, password);

            System.out.println("All Tiffin Plans:");
            List<TiffinPlan> plans = dao.findAll();

            for (TiffinPlan p : plans) {
                System.out.println(
                    p.getPlanId() + " " +
                    p.getPlanName() + " " +
                    p.getPricePerDay() + " " +
                    p.getCity()
                );
            }

            System.out.println("\nSurat Plans:");
            List<TiffinPlan> suratPlans = dao.findByCity("Surat");

            for (TiffinPlan p : suratPlans) {
                System.out.println(
                    p.getPlanName() + " " + p.getPricePerDay()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
