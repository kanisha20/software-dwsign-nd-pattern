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
 *  Q3 TASK 4 — STAFF LOGOUT SERVLET
 * ============================================================
 *  URL: /staff/logout
 *
 *  session.invalidate() → poori session khatam!
 *  Saare attributes, cart, sab chala jaayega.
 *  Phir login page pe redirect karo.
 * ============================================================
 */
@WebServlet("/staff/logout")
public class StaffLogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Session lo (agar hai to)
        HttpSession session = request.getSession(false);

        if (session != null) {
            // ---- SESSION INVALIDATE — sab khatam! ----
            session.invalidate();
            System.out.println("[Logout] Session invalidated.");
        }

        // Login page pe redirect karo
        response.sendRedirect(request.getContextPath() + "/staff/login");
    }
}
