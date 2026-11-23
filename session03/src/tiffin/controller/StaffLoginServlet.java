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
 *  Q3 TASK 4 — STAFF LOGIN SERVLET
 * ============================================================
 *  URL: /staff/login
 *
 *  GET  → login form dikhao
 *  POST → username/password check karo
 *
 *  Username & password web.xml ke context-param se aate hain:
 *    staffUser, staffPass
 *
 *  Login SUCCESS pe:
 *  1) request.getSession() — pehle session banao/lo
 *  2) request.changeSessionId() — session fixation attack se bachao
 *  3) session.setAttribute("staff", username) — mark as logged in
 *  4) session.setMaxInactiveInterval(600) — 10 min timeout
 *  5) redirect to /staff/home
 * ============================================================
 */
@WebServlet("/staff/login")
public class StaffLoginServlet extends HttpServlet {

    // ---- GET: Login form dikhao ----
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/staffLogin.jsp")
               .forward(request, response);
    }

    // ---- POST: Credentials check karo ----
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Form se username/password lo
        String inputUser = request.getParameter("username");
        String inputPass = request.getParameter("password");

        // web.xml se correct credentials padho
        String correctUser = getServletContext().getInitParameter("staffUser");
        String correctPass = getServletContext().getInitParameter("staffPass");

        // ---- Match check karo ----
        if (correctUser.equals(inputUser) && correctPass.equals(inputPass)) {
            // ---- LOGIN SUCCESS ----

            // Step 1: Session lo (ya banao agar nahi hai)
            HttpSession session = request.getSession();
            // HINT: changeSessionId() needs existing session, so getSession() pehle!

            // Step 2: Session ID change karo (Session Fixation Attack se bachao)
            request.changeSessionId();

            // Step 3: "staff" attribute set karo (logged in mark)
            session.setAttribute("staff", inputUser);

            // Step 4: 10 minute timeout (600 seconds)
            session.setMaxInactiveInterval(600);

            // Step 5: Redirect to staff home
            response.sendRedirect(request.getContextPath() + "/staff/home");

        } else {
            // ---- LOGIN FAILED ----
            request.setAttribute("error", "Wrong username or password!");
            request.getRequestDispatcher("/WEB-INF/views/staffLogin.jsp")
                   .forward(request, response);
        }
    }
}
