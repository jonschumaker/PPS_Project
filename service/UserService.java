package service;

import dao.UserDao;
import model.User;
import util.PasswordHasher;

import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service class to handle user-related business logic
 */
public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private final UserDao userDao;
    
    public UserService() {
        this.userDao = new UserDao();
    }
    
    /**
     * Register a new user
     * @param user The user to register
     * @return true if registration successful
     * @throws ServiceException if registration fails
     */
    public boolean registerUser(User user) throws ServiceException {
        try {
            // Validate user data
            validateUserData(user);
            
            // Check if user already exists
            if (userDao.findByEmail(user.getEmailId()).isPresent()) {
                throw new ServiceException("User with email " + user.getEmailId() + " already exists");
            }
            
            return userDao.insertUser(user);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error registering user", e);
            throw new ServiceException("Failed to register user", e);
        }
    }
    
    /**
     * Authenticate a user
     * @param emailId User's email
     * @param password User's password
     * @return Optional containing the user if authentication successful
     * @throws ServiceException if authentication fails
     */
    public Optional<User> authenticateUser(String emailId, String password) throws ServiceException {
        try {
            if (userDao.verifyCredentials(emailId, password)) {
                return userDao.findByEmail(emailId);
            }
            return Optional.empty();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error authenticating user", e);
            throw new ServiceException("Failed to authenticate user", e);
        }
    }
    
    /**
     * Transfer PPS between users
     * @param fromEmail Sender's email
     * @param toEmail Recipient's email
     * @param amount Amount to transfer
     * @return true if transfer successful
     * @throws ServiceException if transfer fails
     */
    public boolean transferPPS(String fromEmail, String toEmail, long amount) throws ServiceException {
        if (amount <= 0) {
            throw new ServiceException("Transfer amount must be positive");
        }
        
        try {
            Optional<User> fromUser = userDao.findByEmail(fromEmail);
            Optional<User> toUser = userDao.findByEmail(toEmail);
            
            if (!fromUser.isPresent()) {
                throw new ServiceException("Sender account not found");
            }
            if (!toUser.isPresent()) {
                throw new ServiceException("Recipient account not found");
            }
            
            // Check sufficient balance
            if (fromUser.get().getPpsBalance() < amount) {
                throw new ServiceException("Insufficient PPS balance");
            }
            
            // Perform transfer
            boolean debitSuccess = userDao.updatePPSBalance(fromEmail, -amount);
            if (!debitSuccess) {
                throw new ServiceException("Failed to debit sender account");
            }
            
            boolean creditSuccess = userDao.updatePPSBalance(toEmail, amount);
            if (!creditSuccess) {
                // Rollback debit if credit fails
                userDao.updatePPSBalance(fromEmail, amount);
                throw new ServiceException("Failed to credit recipient account");
            }
            
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error transferring PPS", e);
            throw new ServiceException("Failed to transfer PPS", e);
        }
    }
    
    private void validateUserData(User user) throws ServiceException {
        if (user.getEmailId() == null || user.getEmailId().trim().isEmpty()) {
            throw new ServiceException("Email is required");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new ServiceException("Password is required");
        }
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
            throw new ServiceException("First name is required");
        }
        if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            throw new ServiceException("Last name is required");
        }
        if (user.getBirthday() == null) {
            throw new ServiceException("Birthday is required");
        }
    }
}
