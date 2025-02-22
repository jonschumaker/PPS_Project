package model;

import java.util.Date;

/**
 * Model class representing a user in the system
 */
public class User {
    private String emailId;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private Date birthday;
    private long ppsBalance;
    private long dollarBalance;
    
    // Default constructor
    public User() {}
    
    // Constructor with all fields except balances (used for registration)
    public User(String emailId, String password, String firstName, String lastName, 
                String address, Date birthday) {
        this.emailId = emailId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthday = birthday;
        this.ppsBalance = 0;
        this.dollarBalance = 0;
    }
    
    // Getters and setters
    public String getEmailId() {
        return emailId;
    }
    
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public Date getBirthday() {
        return birthday;
    }
    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public long getPpsBalance() {
        return ppsBalance;
    }
    
    public void setPpsBalance(long ppsBalance) {
        this.ppsBalance = ppsBalance;
    }
    
    public long getDollarBalance() {
        return dollarBalance;
    }
    
    public void setDollarBalance(long dollarBalance) {
        this.dollarBalance = dollarBalance;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "emailId='" + emailId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", birthday=" + birthday +
                ", ppsBalance=" + ppsBalance +
                ", dollarBalance=" + dollarBalance +
                '}';
    }
}
