
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>PPSWeb</title>
</head>
<body>
    <center>
        <h1>PPSWeb</h1>

    </center>
    <div align="center">
            <form action="insert" method="post">
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                        Add a New User
                </h2>
            </caption>
     
            <tr>
                <th>Email: </th>
                <td>
                    <input type="text" name="email" size="45"
                            value="<c:out value='${user.email}' />"
                        />
                </td>
            </tr>
             <tr>
                <th>First Name: </th>
                <td>
                    <input type="text" name="firstName" size="45"
                            value="<c:out value='${user.firstName}' />"
                        />
                </td>
            </tr>
             <tr>
                <th>Last Name: </th>
                <td>
                    <input type="text" name="lastName" size="45"
                            value="<c:out value='${user.lastName}' />"
                        />
                </td>
            </tr>
             <tr>
                <th>Birthday: </th>
                <td>
                    <input type="date" name="birthday" size="45"
                            value="<c:out value='${user.birthday}' />"
                        />
                </td>
            </tr>
           
             <tr>
                <th>Password: </th>
                <td>
                    <input type="password" name="password" size="45"
                            value="<c:out value='${user.password}' />"
                        />
                </td>
            </tr>
             <tr>
                <th>Re-Enter Password: </th>
                <td>
                    <input type="password" name="password2" size="45"
                            value="<c:out value='${user.password2}' />"
                        />
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Insert New User" />
                </td>
            </tr>
        </table>
        </form>
    </div>   
</body>
</html>