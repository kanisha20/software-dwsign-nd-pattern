package pg.model;

/**
 * Model class representing a Student Enquiry.
 */
public class Enquiry {
    private int enquiryId;
    private String studentName;
    private String studentEmail;
    private int listingId;

    public Enquiry() {}

    public Enquiry(int enquiryId, String studentName, String studentEmail, int listingId) {
        this.enquiryId = enquiryId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.listingId = listingId;
    }

    public int getEnquiryId() { return enquiryId; }
    public void setEnquiryId(int enquiryId) { this.enquiryId = enquiryId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentEmail() { return studentEmail; }
    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }

    public int getListingId() { return listingId; }
    public void setListingId(int listingId) { this.listingId = listingId; }
}
