GYM PATTERNS - ECLIPSE PROJECT

IMPORT INTO ECLIPSE
1. Extract GymPatterns.zip.
2. Open Eclipse.
3. Select File > Import > Maven > Existing Maven Projects.
4. Browse to the extracted GymPatterns folder and click Finish.
5. Wait for Maven to download MySQL Connector/J.

DATABASE SETUP
1. Open MySQL Workbench and run the SQL in database.sql.
2. Open src/main/java/gym/db/ConnectionPool.java.
3. Change the MySQL username and password to match your computer.
4. Make sure MySQL Server is running.

RUN
- To test DAO: right-click gym.dao.DaoDemo.java > Run As > Java Application.
- To run the full console program: right-click gym.mvc.GymApp.java > Run As > Java Application.

NOTES
- The project uses MySQL database gymdb.
- ConnectionPool creates one Singleton instance and initializes a pool of 3 connections.
- Do not run the JDBC option until the database and password are configured.
- In GymApp, the DAO type is set to "jdbc". Change it to "memory" in
  MemberController.java if you want to use the in-memory ArrayList instead.
