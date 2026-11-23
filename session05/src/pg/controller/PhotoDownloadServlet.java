package pg.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * ============================================================
 *  TASK 2: PhotoDownloadServlet (Serving from Disk)
 * ============================================================
 *  URL: /pg/photo?id=2
 *  Constraints:
 *    - Stream using FileInputStream
 *    - Return HTTP 404 when listing or file is missing
 *    - Set the right Content-Type
 * ============================================================
 */
@WebServlet("/pg/photo")
public class PhotoDownloadServlet extends HttpServlet {

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

        String photoPath = null;
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
             PreparedStatement ps = conn.prepareStatement("SELECT photo_path FROM pg_listings WHERE listing_id = ?")) {
            ps.setInt(1, listingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    photoPath = rs.getString("photo_path");
                }
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DB Error");
            return;
        }

        if (photoPath == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No photo path found for listing ID " + listingId);
            return;
        }

        File imageFile = new File(photoPath);
        if (!imageFile.exists() || !imageFile.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Photo file not found on disk: " + photoPath);
            return;
        }

        // Set proper Content-Type
        String mimeType = getServletContext().getMimeType(imageFile.getName());
        if (mimeType == null) {
            mimeType = "image/jpeg";
        }
        response.setContentType(mimeType);
        response.setContentLengthLong(imageFile.length());

        // Stream with FileInputStream (Constraint)
        try (FileInputStream in = new FileInputStream(imageFile);
             OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
