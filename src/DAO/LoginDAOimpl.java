package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAOimpl {

    // Method to check if the username and password are valid
    public boolean authenticate(String username, String password) {
        String sql = "SELECT password FROM login WHERE username = ?";
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    return storedPassword.equals(password); // Return true if passwords match
                }
            }
        } catch (SQLException | ClassNotFoundException exception) {
            System.err.println("Error during authentication: " + exception.getMessage());
            exception.printStackTrace();
        }
        return false; 
    }
}
