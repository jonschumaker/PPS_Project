# Personal Payment System (PPS)

A Java web application that enables users to manage digital currency transactions, user following, and account management.

## System Architecture

The application follows a layered architecture:

```
├── model/          # Data models
├── dao/            # Data Access Objects for database operations
├── service/        # Business logic layer
├── util/          # Utility classes
└── web/           # Web layer (servlets and JSPs)
```

### Key Components

- **User Management**: Registration, authentication, and profile management
- **Transaction System**: PPS transfers, deposits, and withdrawals
- **Following System**: User following and social features
- **Security**: Password hashing, input validation, and SQL injection prevention

## Prerequisites

- Java JDK 8 or higher
- MySQL 5.7 or higher
- Maven 3.6 or higher
- Tomcat 9.0 or higher

## Setup Instructions

1. **Database Setup**
```sql
CREATE DATABASE testdb;
USE testdb;

-- Create required user
CREATE USER 'john'@'localhost' IDENTIFIED BY 'pass1234';
GRANT ALL PRIVILEGES ON testdb.* TO 'john'@'localhost';
FLUSH PRIVILEGES;
```

2. **Clone the Repository**
```bash
git clone <repository-url>
cd pps-project
```

3. **Configure Database Connection**
- Update database credentials in `util/DatabaseConnection.java` if needed
- Default configuration:
  - URL: jdbc:mysql://127.0.0.1:3306/testdb
  - Username: john
  - Password: pass1234

4. **Build the Project**
```bash
mvn clean install
```

5. **Deploy to Tomcat**
- Copy the generated WAR file to Tomcat's webapps directory
- Start Tomcat server

## Usage Guide

### User Registration
- Access `/register` to create a new account
- Required fields: email, password, name, address, birthday
- System automatically initializes account with 0 PPS and dollar balance

### Authentication
- Login at `/login` with email and password
- System uses secure password hashing with PBKDF2

### PPS Operations
1. **Deposit**
   - Add dollars to your account
   - Minimum deposit: $1

2. **Buy PPS**
   - Convert dollars to PPS
   - Current rate: 1 PPS = $1

3. **Transfer PPS**
   - Send PPS to other users
   - Requires sufficient PPS balance
   - Instant transfer with transaction logging

4. **Withdraw**
   - Convert dollars back to your bank account
   - Requires sufficient dollar balance

### Social Features
- Follow other users
- View follower/following lists
- See transaction history

## Security Features

1. **Password Security**
   - PBKDF2 hashing with random salt
   - 10,000 iterations for key derivation
   - Secure storage of hashed passwords

2. **Database Security**
   - Prepared statements to prevent SQL injection
   - Connection pooling with HikariCP
   - Transaction management with rollback support

3. **Input Validation**
   - Server-side validation of all inputs
   - Custom exception handling
   - Proper error messages

## Database Schema

```sql
CREATE TABLE users (
    emailid VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50) NOT NULL,
    fName VARCHAR(20),
    lname VARCHAR(20),
    Address VARCHAR(50),
    birthday DATE,
    PPSbalance BIGINT,
    Dollarbal BIGINT
);

CREATE TABLE Follow (
    emailid VARCHAR(50),
    followerid VARCHAR(20),
    followdate datetime,
    FOREIGN KEY (emailid) REFERENCES Users(emailid)
);

CREATE TABLE Transaction (
    transid INTEGER AUTO_INCREMENT PRIMARY KEY,
    transname VARCHAR(50),
    dollaramt INTEGER,
    PPSamt INTEGER,
    PPSbal INTEGER,
    fromuser VARCHAR(50),
    touser VARCHAR(50),
    FOREIGN KEY (fromuser) REFERENCES Users(emailid),
    FOREIGN KEY (touser) REFERENCES Users(emailid)
);
```

## Error Handling

The system implements a comprehensive error handling strategy:
- Custom ServiceException for business logic errors
- Proper transaction rollback on failures
- Detailed error logging
- User-friendly error messages

## Performance Considerations

1. **Connection Pooling**
   - HikariCP for efficient connection management
   - Configurable pool size (default: 10)
   - Connection timeout handling

2. **Query Optimization**
   - Prepared statement caching
   - Proper indexing on frequently queried columns
   - Efficient transaction management

## Development Guidelines

1. **Code Style**
   - Follow Java naming conventions
   - Use proper JavaDoc documentation
   - Implement proper exception handling
   - Write unit tests for new features

2. **Version Control**
   - Create feature branches for new development
   - Write meaningful commit messages
   - Review code before merging

3. **Testing**
   - Write unit tests for new features
   - Test all error scenarios
   - Validate security measures

## Troubleshooting

Common issues and solutions:

1. **Database Connection Failed**
   - Verify MySQL is running
   - Check credentials in DatabaseConnection.java
   - Ensure database and tables exist

2. **Deployment Issues**
   - Check Tomcat logs
   - Verify WAR file structure
   - Confirm Java version compatibility

3. **Transaction Failures**
   - Check user balances
   - Verify database constraints
   - Review transaction logs

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
