package pg.controller;

import pg.model.PgListing;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * ============================================================
 *  TASK 2 + 3: ListingDetailsServlet
 * ============================================================
 *  URL: /listing?id=2
 *  Loads listing info and forwards to listing.jsp
 * ============================================================
 */
@WebServlet("/listing")
public class ListingDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/pg/upload");
            return;
        }

        int listingId = Integer.parseInt(idParam.trim());
        String jdbcUrl = getServletContext().getInitParameter("jdbcUrl");
        String dbUser  = getServletContext().getInitParameter("dbUser");
        String dbPass  = getServletContext().getInitParameter("dbPassword");

        PgListing listing = null;
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM pg_listings WHERE listing_id = ?")) {
            ps.setInt(1, listingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    listing = new PgListing(
                        rs.getInt("listing_id"),
                        rs.getString("pg_name"),
                        rs.getString("city"),
                        rs.getDouble("rent"),
                        rs.getString("photo_path")
                    );
                }
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }

        request.setAttribute("listing", listing);
        request.getRequestDispatcher("/WEB-INF/views/listing.jsp").forward(request, response);
    }
}
