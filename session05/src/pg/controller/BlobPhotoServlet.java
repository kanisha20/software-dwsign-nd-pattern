package pg.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * ============================================================
 *  TASK 3: BlobPhotoServlet (Serving from Database MEDIUMBLOB)
 * ============================================================
 *  URL: /pg/photoDb?id=2
 *  Hint: Use `rs.getBytes()` for reading back image bytes
 * ============================================================
 */
@WebServlet("/pg/photoDb")
public class BlobPhotoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Missing listing ID");
            return;
        }

        int listingId;
        try {
            listingId = Integer.parseInt(idParam.trim());
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid listing ID");
            return;
        }

        String jdbcUrl = getServletContext().getInitParameter("jdbcUrl");
        String dbUser  = getServletContext().getInitParameter("dbUser");
        String dbPass  = getServletContext().getInitParameter("dbPassword");

        byte[] photoBytes = null;
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
             PreparedStatement ps = conn.prepareStatement("SELECT photo_blob FROM pg_listings WHERE listing_id = ?")) {
            ps.setInt(1, listingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Hint: rs.getBytes() for reading back
                    photoBytes = rs.getBytes("photo_blob");
                }
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            return;
        }

        if (photoBytes == null || photoBytes.length == 0) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No photo BLOB found for listing ID " + listingId);
            return;
        }

        response.setContentType("image/jpeg");
        response.setContentLength(photoBytes.length);

        try (OutputStream out = response.getOutputStream()) {
            out.write(photoBytes);
            out.flush();
        }
    }
}
