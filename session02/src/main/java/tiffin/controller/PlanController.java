package tiffin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tiffin.dao.TiffinPlanDao;
import tiffin.dao.TiffinPlanDaoImpl;
import tiffin.model.TiffinPlan;

public class PlanController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TiffinPlanDao dao;

    public void init() throws ServletException {
        String url = getServletContext().getInitParameter("jdbcUrl");
        String user = getServletContext().getInitParameter("dbUser");
        String password = getServletContext().getInitParameter("dbPassword");

        dao = new TiffinPlanDaoImpl(url, user, password);
    }

    // GET: show all plans or filter plans by city
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String city = request.getParameter("city");
            List<TiffinPlan> plans;

            if (city == null || city.trim().isEmpty()) {
                plans = dao.findAll();
            } else {
                plans = dao.findByCity(city);
            }

            request.setAttribute("plans", plans);
            request.setAttribute("selectedCity", city);
            request.getRequestDispatcher("/WEB-INF/views/plans.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // POST: validate and add a new plan
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("planName");
        String priceText = request.getParameter("pricePerDay");
        String city = request.getParameter("city");

        String error = null;
        double price = 0;

        if (name == null || name.trim().isEmpty()) {
            error = "Plan name cannot be empty";
        }

        if (error == null) {
            try {
                price = Double.parseDouble(priceText);
                if (price < 30 || price > 300) {
                    error = "Price must be between 30 and 300";
                }
            } catch (Exception e) {
                error = "Enter a valid price";
            }
        }

        if (error == null ||
            (city != null && !city.trim().isEmpty())) {
            if (city == null || city.trim().isEmpty()) {
                error = "Please select a city";
            }
        }

        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("planName", name);
            request.setAttribute("pricePerDay", priceText);
            request.setAttribute("selectedCity", city);
            request.getRequestDispatcher("/WEB-INF/views/addPlan.jsp")
                   .forward(request, response);
            return;
        }

        try {
            TiffinPlan plan = new TiffinPlan(0, name.trim(), price, city);
            dao.addPlan(plan);

            // Redirect after successful insert to prevent duplicate inserts
            response.sendRedirect(request.getContextPath() + "/plans");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
