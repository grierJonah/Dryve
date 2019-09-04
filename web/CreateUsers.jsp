<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Create a User</title>

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
    <h1>Create User</h1>

    <form action="usercreate" method="post">
        <p>
            <label for="username">UserName</label>
            <input id="username" name="username" value="">
        </p>
        <p>
            <label for="password">Password</label>
            <input id="password" name="password" value="">
        </p>
        <p>
            <label for="firstname">FirstName</label>
            <input id="firstname" name="firstname" value="">
        </p>
        <p>
            <label for="lastname">LastName</label>
            <input id="lastname" name="lastname" value="">
        </p>
        <p>
            <label for="email">Email</label>
            <input id="email" name="email" value="">
        </p>
        <p>
            <input type="submit">
        </p>
    </form>
<br/><br/>
<p>
    <span id="successMessage"><b>${messages.success}</b></span>
</p>
</body>
</html>