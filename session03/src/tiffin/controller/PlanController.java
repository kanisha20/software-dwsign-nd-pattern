package tiffin.controller;

import tiffin.dao.TiffinPlanDao;
import tiffin.dao.TiffinPlanDaoImpl;
import tiffin.model.TiffinPlan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * ============================================================
 *  CONTROLLER — PlanController (Updated for Q3 Task 2)
 *  NEW: Jab ?city parameter nahi ho, to 'deliveryCity' COOKIE
 *       se city padho aur filter karo.
 * ============================================================
 */
@WebServlet("/plans")
public class PlanController extends HttpServlet {

    private TiffinPlanDao dao;

    @Override
    public void init() throws ServletException {
        String jdbcUrl    = getServletContext().getInitParameter("jdbcUrl");
        String dbUser     = getServletContext().getInitParameter("dbUser");
        String dbPassword = getServletContext().getInitParameter("dbPassword");
        dao = new TiffinPlanDaoImpl(jdbcUrl, dbUser, dbPassword);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ---- Task 3 (Q2): Show add plan form ----
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/views/addPlan.jsp")
                   .forward(request, response);
            return;
        }

        // ---- City decide karo: URL param > Cookie > All ----
        String city = request.getParameter("city");

        // ======== Q3 TASK 2: Cookie se city padho (agar URL mein nahi diya) ========
        if (city == null || city.isEmpty()) {
            city = getCityFromCookie(request);
            // Cookie se mili to woh use karo, nahi mili to null (all plans)
        }

        List<TiffinPlan> plans;
        if (city != null && !city.isEmpty()) {
            plans = dao.findByCity(city);
        } else {
            plans = dao.findAll();
        }

        request.setAttribute("plans", plans);
        request.setAttribute("selectedCity", city);

        request.getRequestDispatcher("/WEB-INF/views/plans.jsp")
               .forward(request, response);
    }

    /**
     * Cookie se 'deliveryCity' ki value padho.
     * HINT: request.getCookies() NULL return karta hai jab
     *       browser ne koi cookie nahi bheji!
     */
    private String getCityFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;  // koi cookie nahi hai

        for (Cookie c : cookies) {
            if ("deliveryCity".equals(c.getName())) {
                return c.getValue();
            }
        }
        return null;  // deliveryCity cookie nahi mili
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Same as Q2 — plan add karo
        String planName = request.getParameter("planName");
        String priceStr = request.getParameter("pricePerDay");
        String city     = request.getParameter("city");

        String error = null;
        if (planName == null || planName.trim().isEmpty()) {
            error = "Plan name cannot be empty!";
        }
        double price = 0;
        if (error == null) {
            try {
                price = Double.parseDouble(priceStr);
                if (price < 30 || price > 300) {
                    error = "Price must be between Rs.30 and Rs.300!";
                }
            } catch (NumberFormatException e) {
                error = "Invalid price!";
            }
        }
        if (error == null && (city == null || city.trim().isEmpty())) {
            error = "City cannot be empty!";
        }

        if (error != null) {
            request.setAttribute("error", error);
            request.getRequestDispatcher("/WEB-INF/views/addPlan.jsp")
                   .forward(request, response);
            return;
        }

        dao.addPlan(new TiffinPlan(0, planName.trim(), price, city.trim()));
        response.sendRedirect(request.getContextPath() + "/plans");
    }
}
