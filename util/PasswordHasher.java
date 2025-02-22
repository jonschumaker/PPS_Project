package util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for secure password hashing and verification
 */
public class PasswordHasher {
    private static final Logger LOGGER = Logger.getLogger(PasswordHasher.class.getName());
    
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;
    
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    
    private PasswordHasher() {
        // Utility class, no instantiation
    }
    
    /**
     * Hash a password using PBKDF2 with a random salt
     * @param password The password to hash
     * @return Base64 encoded string containing salt and hash
     */
    public static String hashPassword(String password) {
        byte[] salt = new byte[SALT_LENGTH];
        RANDOM.nextBytes(salt);
        
        byte[] hash = pbkdf2(password.toCharArray(), salt);
        
        // Format: salt:hash
        byte[] combined = new byte[salt.length + hash.length];
        System.arraycopy(salt, 0, combined, 0, salt.length);
        System.arraycopy(hash, 0, combined, salt.length, hash.length);
        
        return Base64.getEncoder().encodeToString(combined);
    }
    
    /**
     * Verify a password against a stored hash
     * @param password The password to verify
     * @param storedHash The stored hash to verify against
     * @return true if password matches
     */
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            byte[] combined = Base64.getDecoder().decode(storedHash);
            
            // Extract salt and hash
            byte[] salt = Arrays.copyOfRange(combined, 0, SALT_LENGTH);
            byte[] hash = Arrays.copyOfRange(combined, SALT_LENGTH, combined.length);
            
            byte[] testHash = pbkdf2(password.toCharArray(), salt);
            
            return Arrays.equals(hash, testHash);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Invalid hash format", e);
            return false;
        }
    }
    
    private static byte[] pbkdf2(char[] password, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            return factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.log(Level.SEVERE, "Error hashing password", e);
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
