<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Find Accidents</title>
    <style>
        /* Set the size of the div element that contains the map */
        #map {
            height: 400px;  /* The height is 400 pixels */
            width: 100%;  /* The width is the width of the web page */
        }

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

    <form action="findClosestAccidents" method="post">
        <h1>Find the 10 Closest Accidents to you</h1>
        <p>
            <label for="x">X:</label>
            <input id="x" name="x" value="${fn:escapeXml(param.x)}">
            <label for="y">Y:</label>
            <input id="y" name="y" value="${fn:escapeXml(param.y)}">
        </p>
        <p>
            <input type="submit">
            <br/><br/><br/>
            <span id="successMessage"><b>${messages.success}</b></span>
        </p>
    </form>
    <br/>

    <h1>Matching Accidents Results</h1>
    <table border="1">
        <tr>
            <th>accidentId</th>
            <th>SeverityCode</th>
            <th>CollisionType</th>
            <th>PersonCount</th>
            <th>PedCount</th>
            <th>PedCycCount</th>
            <th>VehCount</th>
            <th>X Location</th>
            <th>Y Location</th>
        </tr>

    <c:forEach items="${accidents}" var="Accidents" >
        <tr>
            <td><c:out value="${Accidents.getAccidentId()}" /></td>
            <td><c:out value="${Accidents.getSeverityCode()}" /></td>
            <td><c:out value="${Accidents.getCollisionType()}" /></td>
            <td><c:out value="${Accidents.getPersonCount()}" /></td>
            <td><c:out value="${Accidents.getPedCount()}" /></td>
            <td><c:out value="${Accidents.getPedCycCount()}" /></td>
            <td><c:out value="${Accidents.getVehCount()}" /></td>
            <td id="xLoc"><c:out value="${Accidents.getxLoc()}" /></td>
            <td id="yLoc"><c:out value="${Accidents.getyLoc()}" /></td>
        </tr>
    </c:forEach>
</table>



<h3>Nearest Accidents</h3>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC2x9zeE-dnXXKSAVBt-r20Gaak9ZL0zVI&callback=initMap">
</script>


<!--The div element for the map -->
<div id="map"></div>
<script>
    // Initialize and add the map
    function initMap() {
        console.log("0");
        // The location of Uluru
        //var map;
        var map = new google.maps.Map(
            document.getElementById('map'), {zoom: 4});
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (p) {
                map.setCenter(new google.maps.LatLng(p.coords.latitude, p.coords.longitude));

                console.log(p.coords.latitude);
                console.log(p.coords.longitude);

                var marker = new google.maps.Marker({position: new google.maps.LatLng(p.coords.latitude, p.coords.longitude), map: map});
                // var accidentsMarker = new google.maps.Marker({position: });
                var myBody = "x=" + p.coords.latitude + "&y=" + p.coords.longitude;
                var accidents2 = fetch('http://localhost:8080/databaseSystemsTest_war_exploded', {
                    method: 'POST',
                    body: myBody
                });

                var xArray = Array();
                var yArray = Array();

                var elements = document.getElementsByTagName('td');
                for(var i = 0; i < elements.length; i++) {
                    // console.log(elements[i]);
                    console.log("1");
                    if (elements[i].id === "xLoc") {
                        xArray.push(elements[i].innerHTML)
                    } else if (elements[i].id === "yLoc") {
                        yArray.push(elements[i].innerHTML)
                    }
                }

                for(var j = 0; j < xArray.length; j++) {
                    console.log(xArray[j]);
                    console.log(yArray[j]);

                    var accidentMarker = new google.maps.Marker({position: new google.maps.LatLng(yArray[j], xArray[j]), map: map});
                }

                var variableFromServer = '${accidents}';
                console.log("The accidents from the jsp: " + variableFromServer);


            }, function (positionError) {
                console.log(positionError.message);
            })


        }

        // The marker, positioned at Uluru
    }
</script>
<%--<script async defer--%>
        <%--src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC2x9zeE-dnXXKSAVBt-r20Gaak9ZL0zVI&callback=initMap">--%>
<%--</script>--%>



</body>
</html>
