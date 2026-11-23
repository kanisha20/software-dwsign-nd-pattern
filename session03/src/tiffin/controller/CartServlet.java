package tiffin.controller;

import tiffin.dao.TiffinPlanDao;
import tiffin.dao.TiffinPlanDaoImpl;
import tiffin.model.TiffinPlan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ============================================================
 *  Q3 TASK 1 — CART SERVLET (HttpSession mein cart rakhna)
 * ============================================================
 *  URL: /cart
 *
 *  /cart?action=add&planId=3  → Plan 3 ka ek day-pack cart mein daalo
 *  /cart                      → Cart dikhao (cart.jsp)
 *
 *  Cart kahan hai?
 *  → HttpSession mein! session.getAttribute("cart")
 *  → Map<Integer, Integer>  =  planId → quantity
 *
 *  CONSTRAINT: Cart ko static field ya database mein MAT rakho!
 *  Sirf session mein rakhna hai.
 * ============================================================
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private TiffinPlanDao dao;

    @Override
    public void init() throws ServletException {
        String jdbcUrl    = getServletContext().getInitParameter("jdbcUrl");
        String dbUser     = getServletContext().getInitParameter("dbUser");
        String dbPassword = getServletContext().getInitParameter("dbPassword");
        dao = new TiffinPlanDaoImpl(jdbcUrl, dbUser, dbPassword);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Session lo (create karo agar nahi hai)
        HttpSession session = request.getSession();

        // Cart lo session se (nahi hai to nayi Map banao)
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        // ---- ACTION: Add to cart ----
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            String planIdStr = request.getParameter("planId");
            if (planIdStr != null) {
                try {
                    int planId = Integer.parseInt(planIdStr);

                    // Pehle check karo plan exist karta hai ya nahi
                    TiffinPlan plan = dao.findById(planId);
                    if (plan != null) {
                        // Cart mein quantity badhao (pehle se hai to +1, nahi to 1)
                        int currentQty = cart.getOrDefault(planId, 0);
                        cart.put(planId, currentQty + 1);
                        session.setAttribute("cart", cart);
                    }
                } catch (NumberFormatException ignored) {}
            }
            // Redirect to /cart (clean URL)
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // ---- SHOW CART ----
        // Har cart item ke liye plan details laao
        // cartItems = List of Object[] { TiffinPlan, quantity(int), lineTotal(double) }
        List<Object[]> cartItems = new ArrayList<>();
        double grandTotal = 0;

        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            TiffinPlan plan = dao.findById(entry.getKey());
            if (plan != null) {
                int qty = entry.getValue();
                double lineTotal = plan.getPricePerDay() * qty;
                grandTotal += lineTotal;
                cartItems.add(new Object[]{ plan, qty, lineTotal });
            }
        }

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("grandTotal", grandTotal);

        request.getRequestDispatcher("/WEB-INF/views/cart.jsp")
               .forward(request, response);
    }
}
