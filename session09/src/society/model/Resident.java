package society.model;

/**
 * Model representing a Registered Resident.
 */
public class Resident {
    private String flatNo;
    private String residentName;
    private String email;

    public Resident() {}

    public Resident(String flatNo, String residentName, String email) {
        this.flatNo = flatNo;
        this.residentName = residentName;
        this.email = email;
    }

    public String getFlatNo() { return flatNo; }
    public void setFlatNo(String flatNo) { this.flatNo = flatNo; }

    public String getResidentName() { return residentName; }
    public void setResidentName(String residentName) { this.residentName = residentName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
