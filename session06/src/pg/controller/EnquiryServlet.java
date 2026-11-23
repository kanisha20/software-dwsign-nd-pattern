package pg.controller;

import pg.mail.PgMailer;
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
 *  TASK 3: EnquiryServlet
 * ============================================================
 *  URL: /pg/enquiry
 *  - Handles student enquiry submission.
 *  - Validations:
 *      1. Email must contain '@'
 *      2. listingId must exist in pg_listings
 *      3. studentName must not be empty
 *  - Saves enquiry into `enquiries` table in `pgdb`.
 *  - Invokes `PgMailer.sendEnquiryMail` and `PgMailer.sendHouseRulesMail`.
 * ============================================================
 */
@WebServlet("/pg/enquiry")
public class EnquiryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/enquiry.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String studentName = request.getParameter("studentName");
        String studentEmail = request.getParameter("studentEmail");
        String listingIdStr = request.getParameter("listingId");

        // 1. Validation: studentName
        if (studentName == null || studentName.trim().isEmpty()) {
            request.setAttribute("error", "Student name cannot be empty.");
            forwardBack(request, response);
            return;
        }

        // 2. Validation: email must contain '@' (Constraint)
        if (studentEmail == null || !studentEmail.contains("@") || studentEmail.trim().indexOf('@') == 0 || studentEmail.trim().endsWith("@")) {
            request.setAttribute("error", "Invalid email address! Email must contain '@' and follow a valid format.");
            forwardBack(request, response);
            return;
        }

        // 3. Validation: listingId parse
        int listingId;
        try {
            listingId = Integer.parseInt(listingIdStr.trim());
        } catch (Exception e) {
            request.setAttribute("error", "Invalid listing ID format.");
            forwardBack(request, response);
            return;
        }

        String jdbcUrl = getServletContext().getInitParameter("jdbcUrl");
        String dbUser  = getServletContext().getInitParameter("dbUser");
        String dbPass  = getServletContext().getInitParameter("dbPassword");

        PgListing listing = null;

        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass)) {

            // 4. Check if listingId exists in pg_listings (Constraint)
            String selectSql = "SELECT listing_id, pg_name, city, rent FROM pg_listings WHERE listing_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
                ps.setInt(1, listingId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        listing = new PgListing(
                            rs.getInt("listing_id"),
                            rs.getString("pg_name"),
                            rs.getString("city"),
                            rs.getInt("rent")
                        );
                    }
                }
            }

            if (listing == null) {
                request.setAttribute("error", "Error: Listing ID #" + listingId + " does not exist in our database.");
                forwardBack(request, response);
                return;
            }

            // 5. Insert enquiry record into enquiries table
            String insertSql = "INSERT INTO enquiries (student_name, student_email, listing_id) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setString(1, studentName.trim());
                ps.setString(2, studentEmail.trim());
                ps.setInt(3, listingId);
                ps.executeUpdate();
            }

            // 6. Trigger PgMailer notifications
            PgMailer.sendEnquiryMail(studentEmail.trim(), listing.getPgName(), listing.getCity(), listing.getRent());
            PgMailer.sendHouseRulesMail(studentEmail.trim(), listing.getPgName());

            request.setAttribute("success", "Enquiry registered successfully! Confirmation email and House Rules sent to " + studentEmail);
            request.setAttribute("listing", listing);
            forwardBack(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "Database or Mailer error: " + e.getMessage());
            forwardBack(request, response);
        }
    }

    private void forwardBack(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/enquiry.jsp").forward(request, response);
    }
}
