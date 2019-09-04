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

    <style type="text/css">
        .navButton {
            display: inline-block;
            border: 2px solid black;
            padding: 3px;
            border-radius: 10px;
            background-color: grey;
        }
    </style>
</head>
<body>

    <div id="'navbar">
        <div class="navButton" id="usercreate"><a href="usercreate">Create User</a></div>
        <div class="navButton" id="userfind"><a href="findusers">Find User</a></div>
        <div class="navButton" id="userdelete"><a href="userdelete">Delete User</a></div>
        <div class="navButton" id="userupdate"><a href="userupdate">Update User</a></div>
        <div class="navButton" id="findClosestAccidents"><a href="findClosestAccidents">Find Closest Accidents</a></div>
    </div>


	<form action="findusers" method="post">
		<h1>Search for a User by UserName</h1>
		<p>
			<label for="username">UserName</label>
			<input id="username" name="username" value="${fn:escapeXml(param.userName)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>

    <br/>
	<h1>Matching User</h1>
        <table border="1">
            <tr>
                <th>UserName</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>Email</th>
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
