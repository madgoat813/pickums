<%-- 
    Document   : teamPicks
    Created on : Aug 29, 2016, 8:27:00 PM
    Author     : Taylor
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pick'ums Page</title>
        <jsp:include page="resources/css/headingLink.jsp"/>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <h1>Pick'ums Page</h1>
        <table>
            <tr>
                <td>
                    <input type="radio" name="matchup1" id="teamId1" value="teamName1">
                    <label for="teamId1"><img src="resources/images/packers.png" class="helmet-picks grow" id="teamName1"/>Packers</label>
                </td>
                <td>
                    <input type="radio" name="matchup1" id="teamId2" value="teamName2">
                    <label for="teamId2"><img src="resources/images/bears.png" class="helmet-picks grow" id="teamName2"/>Bears</label>
                </td>
            </tr>
        </table>
        <jsp:include page="resources/js/jsLink.jsp"/>
    </body>
</html>
