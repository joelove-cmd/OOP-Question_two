package com.vu.exhibition.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseSetup {

    // This URL proved to be the only reliable one for creation in this environment.
    private static final String DB_URL = "jdbc:ucanaccess:///app/VUE_Exhibition.accdb;create=true;newDatabaseVersion=V2010";

    public static void main(String[] args) {
        // Full logic for creating and populating the database.
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            System.out.println("Connection to MS Access Database successful.");

            // Drop Participants table if it exists (for idempotency during testing)
            String dropTableSQL = "DROP TABLE Participants";
            try {
                stmt.execute(dropTableSQL);
                System.out.println("Existing Participants table dropped.");
            } catch (SQLException e) {
                // Table might not exist, which is fine
                System.out.println("Participants table does not exist or could not be dropped (might be the first run): " + e.getMessage());
            }

            // Create Participants table
            String createTableSQL = "CREATE TABLE Participants ("
                                  + "RegistrationID VARCHAR(255) PRIMARY KEY, "
                                  + "StudentName VARCHAR(255), "
                                  + "Faculty VARCHAR(255), "
                                  + "ProjectTitle VARCHAR(255), "
                                  + "ContactNumber VARCHAR(50), "
                                  + "EmailAddress VARCHAR(255), "
                                  + "ProjectImage VARCHAR(1024)" // Path to image
                                  + ")";
            stmt.execute(createTableSQL);
            System.out.println("Participants table created successfully.");

            // Insert sample data
            String[] insertSQLs = {
                "INSERT INTO Participants (RegistrationID, StudentName, Faculty, ProjectTitle, ContactNumber, EmailAddress, ProjectImage) VALUES ('S001', 'Atego Eunice', 'Science & Technology', 'AI Powered Drone', '0712345678', 'euniceatego@gmail.com', 'photos/drone.PNG')",
                "INSERT INTO Participants (RegistrationID, StudentName, Faculty, ProjectTitle, ContactNumber, EmailAddress, ProjectImage) VALUES ('S002', 'Mukisa Andrew ', 'Engineering', 'Smart Home System', '0787654321', 'andrewmk@gmail.com', 'images/smarthome.png')",
                "INSERT INTO Participants (RegistrationID, StudentName, Faculty, ProjectTitle, ContactNumber, EmailAddress, ProjectImage) VALUES ('S003', 'Kasirye Brian', 'Computing & IT', 'E-commerce Platform', '0777123456', 'briankasirye@gmail.com', 'images/ecommerce.gif')",
                "INSERT INTO Participants (RegistrationID, StudentName, Faculty, ProjectTitle, ContactNumber, EmailAddress, ProjectImage) VALUES ('S004', 'Opule Julius', 'Science & Technology', 'Robotic Arm', '0755678901', 'juliusop@gmail.com', 'images/roboticarm.jpeg')",
                "INSERT INTO Participants (RegistrationID, StudentName, Faculty, ProjectTitle, ContactNumber, EmailAddress, ProjectImage) VALUES ('S005', 'Egwelu Amos', 'Engineering', 'Water Purification System', '0700987654', 'amosegwelu@gmail.com', 'images/waterfilter.jpg')"
            };

            for (String sql : insertSQLs) {
                stmt.executeUpdate(sql);
            }
            System.out.println(insertSQLs.length + " sample records inserted into Participants table.");

            System.out.println("Database setup complete.");

        } catch (SQLException e) {
            System.err.println("Database setup failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1); // Indicate failure
        }
    }
}
