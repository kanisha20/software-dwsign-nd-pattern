package tiffin.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * ============================================================
 *  Q3 TASK 4 — STAFF HOME SERVLET (Protected Page)
 * ============================================================
 *  URL: /staff/home
 *
 *  Sirf logged-in staff access kar sakta hai.
 *  Agar session mein "staff" attribute nahi hai → login pe bhejo.
 * ============================================================
 */
@WebServlet("/staff/home")
public class StaffHomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Session check karo — false = nayi session mat banao
        HttpSession session = request.getSession(false);

        // ---- Protection: staff attribute nahi hai to login pe bhejo ----
        if (session == null || session.getAttribute("staff") == null) {
            // Logged in nahi hai → wapas login page
            response.sendRedirect(request.getContextPath() + "/staff/login");
            return;
        }

        // ---- Logged in hai → welcome page dikhao ----
        request.setAttribute("staffName", session.getAttribute("staff"));
        request.getRequestDispatcher("/WEB-INF/views/staffHome.jsp")
               .forward(request, response);
    }
}
