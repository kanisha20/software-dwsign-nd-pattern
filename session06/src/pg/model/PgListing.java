package pg.model;

/**
 * Model class representing a PG Listing.
 */
public class PgListing {
    private int listingId;
    private String pgName;
    private String city;
    private int rent;

    public PgListing() {}

    public PgListing(int listingId, String pgName, String city, int rent) {
        this.listingId = listingId;
        this.pgName = pgName;
        this.city = city;
        this.rent = rent;
    }

    public int getListingId() { return listingId; }
    public void setListingId(int listingId) { this.listingId = listingId; }

    public String getPgName() { return pgName; }
    public void setPgName(String pgName) { this.pgName = pgName; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public int getRent() { return rent; }
    public void setRent(int rent) { this.rent = rent; }
}
