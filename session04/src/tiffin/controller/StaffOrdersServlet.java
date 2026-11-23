package tiffin.controller;

import tiffin.dao.OrderDao;
import tiffin.dao.OrderDaoImpl;
import tiffin.model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * ============================================================
 *  Q4 TASK 2 — STAFF ORDERS SERVLET
 * ============================================================
 *  URL: /staff/orders
 *
 *  Saare orders DAO se laao, orders.jsp pe dikhao.
 *  Same admin layout use karo (header, sidebar, footer includes).
 *  activePage = "orders" (sidebar highlighting)
 * ============================================================
 */
@WebServlet("/staff/orders")
public class StaffOrdersServlet extends HttpServlet {

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

        // ---- Session protection ----
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("staff") == null) {
            response.sendRedirect(request.getContextPath() + "/staff/login");
            return;
        }

        request.setAttribute("staffName", session.getAttribute("staff"));

        // ---- Orders list ----
        List<Order> orders = orderDao.findAll();
        request.setAttribute("orders", orders);

        // ---- Active page for sidebar ----
        request.setAttribute("activePage", "orders");

        // ---- Forward to orders.jsp ----
        request.getRequestDispatcher("/WEB-INF/views/orders.jsp")
               .forward(request, response);
    }
}
