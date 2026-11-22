TIFFINBOX - SESSION 2 (MVC + DAO) - ECLIPSE PROJECT

PROJECT IMPORT
1. Extract TiffinBox_Eclipse_Project.zip.
2. Open Eclipse.
3. Select File > Import > Maven > Existing Maven Projects.
4. Browse to the extracted TiffinBox folder.
5. Click Finish and wait for Maven dependencies to download.
6. Use Apache Tomcat 9 as the server.

DATABASE SETUP
1. Open MySQL Workbench.
2. Run the complete SQL script in database.sql.
3. Open src/main/webapp/WEB-INF/web.xml.
4. Change dbPassword from your_password to your MySQL password.
5. Also change the password in PlanDaoTest.java if you run that test.
6. Make sure MySQL Server is running.

RUN THE DAO TEST
- Open src/main/java/tiffin/dao/PlanDaoTest.java.
- Right-click > Run As > Java Application.

RUN THE WEB APPLICATION
- Right-click project > Run As > Run on Server.
- Select Apache Tomcat 9.
- Open: http://localhost:8080/TiffinBox/plans

PROJECT FEATURES
- Shows all tiffin plans.
- Filters plans by city.
- Adds a new plan.
- Validates that plan name is not empty.
- Validates price from Rs. 30 to Rs. 300.
- Uses MVC + DAO pattern.

IMPORTANT
- This project uses javax.servlet and is intended for Tomcat 9.
- Do not use Tomcat 10/10.1 with these javax imports.
- Database name: tiffindb
- Table name: tiffin_plans
- Cities: Ahmedabad, Surat, Rajkot
