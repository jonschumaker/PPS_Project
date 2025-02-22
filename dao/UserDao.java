package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.User;
import util.DatabaseConnection;
import util.PasswordHasher;

/**
 * Data Access Object for User-related database operations
 */
public class UserDao {
    private final DatabaseConnection dbConnection;
    
    public UserDao() {
        this.dbConnection = DatabaseConnection.getInstance();
    }
    
    /**
     * Insert a new user into the database
     * @param user The user to insert
     * @return true if successful, false otherwise
     */
    public boolean insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users (emailid, password, password2, fName, lname, address, birthday, PPSbalance, Dollarbal) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, 0, 0)";
                    
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String hashedPassword = PasswordHasher.hashPassword(user.getPassword());
            
            stmt.setString(1, user.getEmailId());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, hashedPassword); // Store same hash for both password fields
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getAddress());
            stmt.setDate(7, new java.sql.Date(user.getBirthday().getTime()));
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Find a user by email
     * @param emailId The email to search for
     * @return Optional containing the user if found
     */
    public Optional<User> findByEmail(String emailId) throws SQLException {
        String sql = "SELECT * FROM users WHERE emailid = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, emailId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setEmailId(rs.getString("emailid"));
                user.setFirstName(rs.getString("fName"));
                user.setLastName(rs.getString("lname"));
                user.setAddress(rs.getString("address"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPpsBalance(rs.getLong("PPSbalance"));
                user.setDollarBalance(rs.getLong("Dollarbal"));
                return Optional.of(user);
            }
            
            return Optional.empty();
        }
    }
    
    /**
     * Verify user credentials
     * @param emailId The email
     * @param password The password to verify
     * @return true if credentials are valid
     */
    public boolean verifyCredentials(String emailId, String password) throws SQLException {
        String sql = "SELECT password FROM users WHERE emailid = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, emailId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String storedHash = rs.getString("password");
                return PasswordHasher.verifyPassword(password, storedHash);
            }
            
            return false;
        }
    }
    
    /**
     * Update user's PPS balance
     * @param emailId The user's email
     * @param amount Amount to add (positive) or subtract (negative)
     * @return true if successful
     */
    public boolean updatePPSBalance(String emailId, long amount) throws SQLException {
        String sql = "UPDATE users SET PPSbalance = PPSbalance + ? WHERE emailid = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, amount);
            stmt.setString(2, emailId);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Update user's dollar balance
     * @param emailId The user's email
     * @param amount Amount to add (positive) or subtract (negative)
     * @return true if successful
     */
    public boolean updateDollarBalance(String emailId, long amount) throws SQLException {
        String sql = "UPDATE users SET Dollarbal = Dollarbal + ? WHERE emailid = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, amount);
            stmt.setString(2, emailId);
            
            return stmt.executeUpdate() > 0;
        }
    }
}
