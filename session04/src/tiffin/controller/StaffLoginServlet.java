package tiffin.controller;
import javax.servlet.ServletException; import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*; import java.io.IOException;

/** StaffLoginServlet — same as Q3 */
@WebServlet("/staff/login")
public class StaffLoginServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/staffLogin.jsp").forward(req, resp);
    }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("username"), pass = req.getParameter("password");
        String cUser = getServletContext().getInitParameter("staffUser"), cPass = getServletContext().getInitParameter("staffPass");
        if (cUser.equals(user) && cPass.equals(pass)) {
            HttpSession s = req.getSession(); req.changeSessionId();
            s.setAttribute("staff", user); s.setMaxInactiveInterval(600);
            resp.sendRedirect(req.getContextPath() + "/staff/home");
        } else {
            req.setAttribute("error", "Wrong username or password!");
            req.getRequestDispatcher("/WEB-INF/views/staffLogin.jsp").forward(req, resp);
        }
    }
}
