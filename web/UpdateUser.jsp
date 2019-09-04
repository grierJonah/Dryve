<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Update a User</title>

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

    <h1>Update BlogUser</h1>

    <form action="userupdate" method="post">
        <p>
            <label for="username">UserName</label>
            <input id="username" name="username" value="${fn:escapeXml(param.username)}">
        </p>
        <p>
            <label for="newusername">New UserName</label>
            <input id="newusername" name="newusername" value="">
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