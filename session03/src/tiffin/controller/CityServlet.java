package tiffin.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ============================================================
 *  Q3 TASK 2 — CITY SERVLET (Cookie mein city save karna)
 * ============================================================
 *  URL: /setCity?city=Surat
 *
 *  Cookie name   : deliveryCity
 *  Cookie expiry : 7 days (7 * 24 * 60 * 60 seconds)
 *  HttpOnly      : true (JavaScript se access nahi hoga — security)
 *
 *  Kaise test karo:
 *  1) /setCity?city=Surat → cookie set, redirect to /plans
 *  2) Tab close karo, /plans kholo → Surat filter hoga (cookie se)
 *  3) Browser settings se cookie delete karo → sab plans dikhenge
 * ============================================================
 */
@WebServlet("/setCity")
public class CityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String city = request.getParameter("city");

        if (city != null && !city.trim().isEmpty()) {
            // ---- Cookie banao ----
            Cookie cityCookie = new Cookie("deliveryCity", city.trim());

            // 7 din tak rahe (seconds mein)
            cityCookie.setMaxAge(7 * 24 * 60 * 60);  // 604800 seconds

            // HttpOnly = true → JavaScript se access nahi kar sakta (XSS se safe)
            cityCookie.setHttpOnly(true);

            // Cookie path set karo (poori site pe available)
            cityCookie.setPath("/");

            // Response mein cookie add karo
            response.addCookie(cityCookie);

            System.out.println("[CityServlet] Cookie set: deliveryCity = " + city);
        }

        // /plans pe redirect karo
        response.sendRedirect(request.getContextPath() + "/plans");
    }
}
