package tiffin.controller;

import tiffin.dao.OrderDao;
import tiffin.dao.OrderDaoImpl;
import tiffin.model.DashboardStats;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * ============================================================
 *  Q4 TASK 1+3 — STAFF HOME SERVLET (Updated)
 * ============================================================
 *  URL: /staff/home
 *
 *  Task 1: staffHome.jsp pe forward karo (with template includes)
 *  Task 3: DashboardStats bean pass karo (3 cards ke liye)
 *         + activePage = "home" (sidebar highlighting)
 * ============================================================
 */
@WebServlet("/staff/home")
public class StaffHomeServlet extends HttpServlet {

    private OrderDao orderDao;

    @Override
    public void init() throws ServletException {
        String url  = getServletContext().getInitParameter("jdbcUrl");
        String user = getServletContext().getInitParameter("dbUser");
        String pass = getServletContext().getInitParameter("dbPassword");
        orderDao = new OrderDaoImpl(url, user, pass);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ---- Session protection (Q3 Task 4) ----
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("staff") == null) {
            response.sendRedirect(request.getContextPath() + "/staff/login");
            return;
        }

        // ---- Staff name ----
        request.setAttribute("staffName", session.getAttribute("staff"));

        // ---- Task 3: Dashboard Stats ----
        DashboardStats stats = orderDao.getDashboardStats();
        request.setAttribute("stats", stats);

        // ---- Task 3: Active page for sidebar highlighting ----
        request.setAttribute("activePage", "home");

        // ---- Task 1: Forward to staffHome.jsp (with template) ----
        request.getRequestDispatcher("/WEB-INF/views/staffHome.jsp")
               .forward(request, response);
    }
}
