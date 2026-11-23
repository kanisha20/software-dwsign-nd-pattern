package tiffin.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * ============================================================
 *  Q3 TASK 3 — CHECKOUT SERVLET (Two-step, Hidden Fields)
 * ============================================================
 *  URL: /checkout
 *
 *  Step 1 (GET ?step=1):
 *    → Name form dikhao
 *
 *  Step 2 (POST, step="2"):
 *    → customerName hidden field mein rakh ke delivery slot form dikhao
 *    → Slots: "Lunch 12-1" ya "Dinner 7-8"
 *
 *  Step 3 (POST, step="3" — final):
 *    → Name + Slot + Cart total print karo
 *    → Hidden field RE-VALIDATE karo server pe!
 *
 *  CONSTRAINT:
 *  - Har link/form action ko response.encodeURL() mein wrap karo
 *    (URL Rewriting — cookies blocked hone pe bhi kaam kare)
 *  - Hidden field server pe validate karo (not empty, < 40 chars)
 * ============================================================
 */
@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    // ================================================================
    //  doGet — Step 1: Name form dikhao
    // ================================================================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Default step = 1 (name form)
        request.getRequestDispatcher("/WEB-INF/views/checkout_step1.jsp")
               .forward(request, response);
    }

    // ================================================================
    //  doPost — Step 2 ya Step 3 (final)
    // ================================================================
    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String step = request.getParameter("step");

        if ("2".equals(step)) {
            // ---- STEP 2: Name validate karo, slot form dikhao ----

            String customerName = request.getParameter("customerName");

            // Validate name
            if (customerName == null || customerName.trim().isEmpty()) {
                request.setAttribute("error", "Name cannot be empty!");
                request.getRequestDispatcher("/WEB-INF/views/checkout_step1.jsp")
                       .forward(request, response);
                return;
            }
            if (customerName.trim().length() > 40) {
                request.setAttribute("error", "Name must be under 40 characters!");
                request.getRequestDispatcher("/WEB-INF/views/checkout_step1.jsp")
                       .forward(request, response);
                return;
            }

            // Name theek hai → step 2 form dikhao (hidden field mein name rakhenge)
            request.setAttribute("customerName", customerName.trim());
            request.getRequestDispatcher("/WEB-INF/views/checkout_step2.jsp")
                   .forward(request, response);

        } else if ("3".equals(step)) {
            // ---- STEP 3 (FINAL): Confirm order ----

            String customerName = request.getParameter("customerName");
            String slot         = request.getParameter("slot");

            // ======== RE-VALIDATE hidden field (CONSTRAINT!) ========
            // User browser mein hidden field edit kar sakta hai!
            if (customerName == null || customerName.trim().isEmpty()) {
                request.setAttribute("error", "Name cannot be empty! (tampered hidden field)");
                request.getRequestDispatcher("/WEB-INF/views/checkout_step1.jsp")
                       .forward(request, response);
                return;
            }
            if (customerName.trim().length() > 40) {
                request.setAttribute("error", "Name must be under 40 characters! (tampered)");
                request.getRequestDispatcher("/WEB-INF/views/checkout_step1.jsp")
                       .forward(request, response);
                return;
            }

            // Validate slot
            if (slot == null || slot.trim().isEmpty()) {
                request.setAttribute("error", "Please select a delivery slot!");
                request.setAttribute("customerName", customerName.trim());
                request.getRequestDispatcher("/WEB-INF/views/checkout_step2.jsp")
                       .forward(request, response);
                return;
            }

            // ---- Cart total calculate karo ----
            HttpSession session = request.getSession();
            Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
            double cartTotal = 0;

            if (cart != null && !cart.isEmpty()) {
                // Cart total calculate — same DAO use karo
                String jdbcUrl    = getServletContext().getInitParameter("jdbcUrl");
                String dbUser     = getServletContext().getInitParameter("dbUser");
                String dbPassword = getServletContext().getInitParameter("dbPassword");
                tiffin.dao.TiffinPlanDao dao =
                    new tiffin.dao.TiffinPlanDaoImpl(jdbcUrl, dbUser, dbPassword);

                for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                    tiffin.model.TiffinPlan plan = dao.findById(entry.getKey());
                    if (plan != null) {
                        cartTotal += plan.getPricePerDay() * entry.getValue();
                    }
                }
            }

            // Sab kuch set karo confirmation ke liye
            request.setAttribute("customerName", customerName.trim());
            request.setAttribute("slot", slot);
            request.setAttribute("cartTotal", cartTotal);

            request.getRequestDispatcher("/WEB-INF/views/checkout_confirm.jsp")
                   .forward(request, response);
        } else {
            // Unknown step → wapas step 1 pe
            response.sendRedirect(response.encodeRedirectURL(
                request.getContextPath() + "/checkout?step=1"));
        }
    }
}
