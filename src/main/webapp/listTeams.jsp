<%-- 
    Document   : Index
    Created on : Aug 27, 2016, 6:34:56 AM
    Author     : Taylor
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List Teams</title>
        <jsp:include page="resources/css/headingLink.jsp"/>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <h1>Team Names</h1>
        <form method="POST" action="ProductController?action=crud">
            <div class="alert alert-info" role="alert">All Teams</div>
            <table class="table" id="listTable">
                <thead><td>ID</td><td>Name</td><td>City</td></thead>
                <c:forEach var="i" items="${teams}">
                    <tr>
                        <td><input type="checkbox" class="checkBox" name="teamId" value="${i.teamId}"/></td>
                        <td>${i.teamName}</td>
                        <td>${i.teamCity}</td>
                    </tr>
                </c:forEach>
            </table>
        </form>
        <jsp:include page="resources/js/jsLink.jsp"/>
    </body>
</html>
