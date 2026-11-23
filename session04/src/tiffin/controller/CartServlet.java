package tiffin.controller;
import tiffin.dao.TiffinPlanDao; import tiffin.dao.TiffinPlanDaoImpl; import tiffin.model.TiffinPlan;
import javax.servlet.ServletException; import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*; import java.io.IOException; import java.util.*;

/** CartServlet — same as Q3 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private TiffinPlanDao dao;
    @Override public void init() throws ServletException {
        dao = new TiffinPlanDaoImpl(getServletContext().getInitParameter("jdbcUrl"),
            getServletContext().getInitParameter("dbUser"), getServletContext().getInitParameter("dbPassword"));
    }
    @Override @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Map<Integer,Integer> cart = (Map<Integer,Integer>) session.getAttribute("cart");
        if (cart == null) { cart = new HashMap<>(); session.setAttribute("cart", cart); }
        if ("add".equals(req.getParameter("action"))) {
            try { int id = Integer.parseInt(req.getParameter("planId")); if(dao.findById(id)!=null) cart.put(id, cart.getOrDefault(id,0)+1); session.setAttribute("cart",cart); } catch(Exception ignored){}
            resp.sendRedirect(req.getContextPath()+"/cart"); return;
        }
        List<Object[]> items = new ArrayList<>(); double total = 0;
        for (Map.Entry<Integer,Integer> e : cart.entrySet()) {
            TiffinPlan p = dao.findById(e.getKey()); if(p!=null){double lt=p.getPricePerDay()*e.getValue(); total+=lt; items.add(new Object[]{p,e.getValue(),lt});}
        }
        req.setAttribute("cartItems", items); req.setAttribute("grandTotal", total);
        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }
}
