<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Users Favorite Locations</title>
</head>
<body>
	<h1>${messages.title}</h1>
        <table border="1">
            <tr>
                <th>CommentId</th>
                <th>PostId</th>
                <th>UserName</th>
                <th>Content</th>
                <th>Created</th>
            </tr>
            <c:forEach items="${favLocation}" var="favLocation" >
                <tr>
                    <td><c:out value="${favLocation.getFavoriteLocId}" /></td>
                    <td><c:out value="${favLocation.getUserName}" /></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
