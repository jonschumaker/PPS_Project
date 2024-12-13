
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
            <form action="transfer" method="post">
        <table border="1" cellpadding="5">
           
                <c:if test="${people != null}">
                    <input type="hidden" name="Email" value="<c:out value='${people.emailid}' />" />
                </c:if>           
    
            <form action="transfer" method="post">
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                        Transfer PPS
                </h2>
            </caption>
     
            <tr>
                <th>Email </th>
                <td>
                    <input type="text" name="fuser" size="45"
                            value="<c:out value='${people.fuser}' />"
                        />
                </td>
            </tr>
            <tr>
                <th>Transfer To </th>
                <td>
                    <input type="text" name="tuser" size="45"
                            value="<c:out value='${people.tuser}' />"
                        />
                </td>
            </tr>
            <tr>
                <th>PPS  </th>
                <td>
                    <input type="text" name="ppsamt" size="45"
                            value="<c:out value='${people.ppsamt}' />"
                        />
                </td>
            </tr>
            
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Send" />
                    
                </td>
            </tr>
        </table>
        </form>
    </div>   

<div></div>
</body>
</html>
