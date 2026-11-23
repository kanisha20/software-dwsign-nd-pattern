package tiffin.controller;
import tiffin.dao.TiffinPlanDao; import tiffin.dao.TiffinPlanDaoImpl; import tiffin.model.TiffinPlan;
import javax.servlet.ServletException; import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*; import java.io.IOException; import java.util.List;

/** PlanController — same as Q3 (with cookie filter) */
@WebServlet("/plans")
public class PlanController extends HttpServlet {
    private TiffinPlanDao dao;
    @Override public void init() throws ServletException {
        dao = new TiffinPlanDaoImpl(getServletContext().getInitParameter("jdbcUrl"),
            getServletContext().getInitParameter("dbUser"), getServletContext().getInitParameter("dbPassword"));
    }
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) { req.getRequestDispatcher("/WEB-INF/views/addPlan.jsp").forward(req, resp); return; }
        String city = req.getParameter("city");
        if (city == null || city.isEmpty()) { Cookie[] cookies = req.getCookies(); if (cookies != null) for (Cookie c : cookies) if ("deliveryCity".equals(c.getName())) city = c.getValue(); }
        List<TiffinPlan> plans = (city != null && !city.isEmpty()) ? dao.findByCity(city) : dao.findAll();
        req.setAttribute("plans", plans); req.setAttribute("selectedCity", city);
        req.getRequestDispatcher("/WEB-INF/views/plans.jsp").forward(req, resp);
    }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name=req.getParameter("planName"); String priceStr=req.getParameter("pricePerDay"); String city=req.getParameter("city");
        String error=null;
        if(name==null||name.trim().isEmpty()) error="Name empty!";
        double price=0; if(error==null){try{price=Double.parseDouble(priceStr);if(price<30||price>300)error="Price Rs.30-300!";}catch(NumberFormatException e){error="Bad price!";}}
        if(error==null&&(city==null||city.trim().isEmpty())) error="City empty!";
        if(error!=null){req.setAttribute("error",error);req.getRequestDispatcher("/WEB-INF/views/addPlan.jsp").forward(req,resp);return;}
        dao.addPlan(new TiffinPlan(0,name.trim(),price,city.trim())); resp.sendRedirect(req.getContextPath()+"/plans");
    }
}
