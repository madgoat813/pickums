<%-- 
    Document   : listMatchUp
    Created on : Aug 30, 2016, 4:06:33 AM
    Author     : Taylor
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List Match Page</title>
        <jsp:include page="resources/css/headingLink.jsp"/>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <h1>List Matchup</h1>
        <form method="POST" action="MatchController?action=crud">
            <div class="alert alert-info" role="alert">Matchups</div>
            <table class="table" id="listTable">
                <thead><td>ID</td><td>Home</td><td>Away</td></thead>
                <c:forEach var="i" items="${match}">
                    <tr>
                        <td><input type="checkbox" class="checkBox" name="matchId" value="${i.matchId}"/></td>
                        <td>${i.homeTeam}</td>
                        <td>${i.awayTeam}</td>
                    </tr>
                </c:forEach>
            </table>
            <input type="submit" class="btn btn-primary" value="add" name="submit" id="newButton"/>&nbsp
            <input type="submit" class="btn btn-warning" value="edit" name="submit" id="editButton" style="display:none;"/>&nbsp
            <input type="submit" class="btn btn-danger" value="delete" name="submit" id="deleteButton" style="display:none;"/>
        </form>
        <br>      
        <jsp:include page="resources/js/jsLink.jsp"/>
    </body>
</html>
