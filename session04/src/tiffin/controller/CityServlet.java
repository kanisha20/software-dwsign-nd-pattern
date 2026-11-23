package tiffin.controller;
import javax.servlet.ServletException; import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*; import java.io.IOException;

/** CityServlet — same as Q3 */
@WebServlet("/setCity")
public class CityServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String city = req.getParameter("city");
        if (city != null && !city.trim().isEmpty()) {
            Cookie c = new Cookie("deliveryCity", city.trim());
            c.setMaxAge(7*24*60*60); c.setHttpOnly(true); c.setPath("/");
            resp.addCookie(c);
        }
        resp.sendRedirect(req.getContextPath() + "/plans");
    }
}
