package pg.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

/**
 * ============================================================
 *  TASK 1 + 2 + 3: PhotoUploadServlet
 * ============================================================
 *  URL: /pg/upload
 *  Accepts: multipart/form-data
 *  Constraints:
 *    - @MultipartConfig
 *    - Save in user.home / pg_uploads / listing_<id>_<timestamp>.<ext>
 *    - Custom code checks: .jpg, .jpeg, .png only & size <= 2MB (2 * 1024 * 1024)
 *    - Saves file path in `photo_path` column
 *    - Saves file bytes in `photo_blob` column with setBinaryStream()
 * ============================================================
 */
@WebServlet("/pg/upload")
@MultipartConfig(
    maxFileSize = 10 * 1024 * 1024,      // 10MB maximum request threshold
    maxRequestSize = 20 * 1024 * 1024
)
public class PhotoUploadServlet extends HttpServlet {

    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2 MB
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/uploadPhoto.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String listingIdStr = request.getParameter("listingId");
        Part filePart = request.getPart("roomPhoto");

        if (listingIdStr == null || listingIdStr.trim().isEmpty() || filePart == null) {
            request.setAttribute("error", "Listing ID and photo file are required.");
            request.getRequestDispatcher("/WEB-INF/views/uploadPhoto.jsp").forward(request, response);
            return;
        }

        int listingId;
        try {
            listingId = Integer.parseInt(listingIdStr.trim());
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid Listing ID format.");
            request.getRequestDispatcher("/WEB-INF/views/uploadPhoto.jsp").forward(request, response);
            return;
        }

        // 1. Check File Size manually (Constraint: check in own code)
        long fileSize = filePart.getSize();
        if (fileSize <= 0) {
            request.setAttribute("error", "Please select a non-empty file.");
            request.getRequestDispatcher("/WEB-INF/views/uploadPhoto.jsp").forward(request, response);
            return;
        }
        if (fileSize > MAX_FILE_SIZE) {
            request.setAttribute("error", "File rejected! Size (" + (fileSize / 1024) + " KB) exceeds maximum allowed 2 MB limit.");
            request.getRequestDispatcher("/WEB-INF/views/uploadPhoto.jsp").forward(request, response);
            return;
        }

        // 2. Check File Extension manually (Hint: getSubmittedFileName())
        String originalFilename = filePart.getSubmittedFileName();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        }

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            request.setAttribute("error", "File rejected! Extension '." + extension + "' is not supported. Only .jpg, .jpeg, .png allowed.");
            request.getRequestDispatcher("/WEB-INF/views/uploadPhoto.jsp").forward(request, response);
            return;
        }

        // 3. Save file in user home / pg_uploads
        String userHome = System.getProperty("user.home");
        File uploadDir = new File(userHome, "pg_uploads");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        long timestamp = System.currentTimeMillis();
        String savedFilename = "listing_" + listingId + "_" + timestamp + "." + extension;
        File savedFile = new File(uploadDir, savedFilename);

        try (InputStream in = filePart.getInputStream();
             FileOutputStream out = new FileOutputStream(savedFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        // 4. Update Database: Path and Blob (setBinaryStream)
        String jdbcUrl = getServletContext().getInitParameter("jdbcUrl");
        String dbUser  = getServletContext().getInitParameter("dbUser");
        String dbPass  = getServletContext().getInitParameter("dbPassword");

        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
             PreparedStatement ps = conn.prepareStatement(
                 "UPDATE pg_listings SET photo_path = ?, photo_blob = ? WHERE listing_id = ?");
             InputStream blobInput = filePart.getInputStream()) {

            ps.setString(1, savedFile.getAbsolutePath());
            ps.setBinaryStream(2, blobInput, (int) fileSize);
            ps.setInt(3, listingId);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                request.setAttribute("message", "Uploaded successfully!");
                request.setAttribute("listingId", listingId);
                request.setAttribute("savedPath", savedFile.getAbsolutePath());
            } else {
                request.setAttribute("error", "Listing ID " + listingId + " not found in database.");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/views/uploadPhoto.jsp").forward(request, response);
    }
}
