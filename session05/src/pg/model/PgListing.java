package pg.model;

/**
 * ============================================================
 *  PG LISTING MODEL (Bean)
 * ============================================================
 */
public class PgListing {
    private int    listingId;
    private String pgName;
    private String city;
    private double rent;
    private String photoPath;

    public PgListing() {}

    public PgListing(int listingId, String pgName, String city, double rent, String photoPath) {
        this.listingId = listingId;
        this.pgName    = pgName;
        this.city      = city;
        this.rent      = rent;
        this.photoPath = photoPath;
    }

    public int    getListingId() { return listingId; }
    public void   setListingId(int v) { listingId = v; }
    public String getPgName() { return pgName; }
    public void   setPgName(String v) { pgName = v; }
    public String getCity() { return city; }
    public void   setCity(String v) { city = v; }
    public double getRent() { return rent; }
    public void   setRent(double v) { rent = v; }
    public String getPhotoPath() { return photoPath; }
    public void   setPhotoPath(String v) { photoPath = v; }
}
