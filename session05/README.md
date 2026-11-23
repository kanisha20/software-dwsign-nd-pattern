# Q5 — File & Image Upload / Download (PgFinder)

## 📁 Project Overview
Is project mein File Upload aur Download ke dono approaches (Disk storage vs Database BLOB storage) implement kiye gaye hain:

### Tasks Implemented
1. **Task 1: File Upload with Constraints**:
   - Servlet: `PhotoUploadServlet` at `/pg/upload` with `@MultipartConfig`.
   - File saved in: `~user.home/pg_uploads/listing_<listingId>_<timestamp>.<ext>`.
   - Constraints: Custom code se check kiya gaya hai:
     - Allowed extensions: `.jpg`, `.jpeg`, `.png` only (PDF rejected).
     - Max size: `2 MB` (e.g. 500 KB accepted, 3 MB rejected).
2. **Task 2: Path in DB + Disk Streaming**:
   - Table `pg_listings` in `pgdb` with columns (`listing_id`, `pg_name`, `city`, `rent`, `photo_path`, `photo_blob`).
   - Download Servlet: `PhotoDownloadServlet` at `/pg/photo?id=2`.
   - Streams file back using `FileInputStream` with proper MIME type. Missing file/listing pe `HTTP 404` return karta hai.
3. **Task 3: Database MEDIUMBLOB + Side-by-Side Comparison**:
   - `PhotoUploadServlet` saves binary stream using `setBinaryStream()`.
   - `BlobPhotoServlet` at `/pg/photoDb?id=2` reads back bytes using `rs.getBytes()`.
   - `listing.jsp?id=2` dono images ko side-by-side compare karta hai.

---

## 🗄️ Database Setup
1. MySQL mein `pg_setup.sql` run karein:
```sql
SOURCE C:/Users/dipak/Desktop/project 2/Q5_File_Upload_Download/PgFinder/pg_setup.sql;
```

---

## 🧪 Testing Checklist
- [x] **500 KB JPG**: Upload successfully, saved to `pg_uploads` and database blob.
- [x] **3 MB JPG**: Rejected with error: `File rejected! Size exceeds maximum allowed 2 MB limit.`
- [x] **PDF file**: Rejected with error: `File rejected! Extension '.pdf' is not supported.`
- [x] `/listing?id=2`: Dono images side by side render hongi.
