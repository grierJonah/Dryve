<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a User</title>
</head>
<body>


	<form action="findFavoriteParking" method="post">
		<h1>Search for a Favorite Parking by UserName</h1>
		<p>
			<label for="username">UserName</label>
			<input id="username" name="username" value="${fn:escapeXml(param.username)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="userCreate"><a href="usercreate">Create User</a></div>
	<br/>
	<h1>Matching User</h1>
        <table border="1">
            <tr>
                <th>ParkingID</th>
                <th>UserName</th>
            </tr>
            <c:forEach items="${users}" var="Users" >
                <tr>
                    <td><c:out value="${Users.getUserName()}" /></td>
                    <td><c:out value="${Users.getFirstName()}" /></td>
                    <td><c:out value="${Users.getLastName()}" /></td>
                    <td><c:out value="${Users.getEmail()}" /></td>
                    
                    
                    <td><a href="favoritelocations?username=<c:out value="${blogUser.getUserName()}"/>">FavoriteLocations</a></td>
                    <td><a href="favoriteparking?username=<c:out value="${blogUser.getUserName()}"/>">FavoriteParking</a></td>
                    <!-- <td><a href="userdelete?username=<c:out value="${blogUser.getUserName()}"/>">Delete</a></td> -->
                    <!-- <td><a href="userupdate?username=<c:out value="${blogUser.getUserName()}"/>">Update</a></td> -->
                </tr>
            </c:forEach>
       </table>
</body>
</html>
