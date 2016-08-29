<%-- 
    Document   : listUsers
    Created on : Aug 27, 2016, 8:49:10 AM
    Author     : Taylor
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List Users</title>
        <jsp:include page="resources/css/headingLink.jsp"/>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <h1>Users Names</h1>
        <form method="POST" action="UserController?action=crud">
            <div class="alert alert-info" role="alert">All Users</div>
            <table class="table" id="listTable">
                <thead><td>ID</td><td>Name</td><td>Password</td><td>Email</td></thead>
            <c:forEach var="i" items="${users}">
                    <tr>
                        <td><input type="checkbox" class="checkBox" name="userId" value="${i.userId}"/></td>
                        <td>${i.userName}</td>
                        <td>${i.password}</td>
                        <td>${i.email}</td>
                    </tr>
                </c:forEach>
            </table>
            <input type="submit" class="btn btn-primary" value="add" name="submit" id="newButton"/>&nbsp
            <input type="submit" class="btn btn-warning" value="edit" name="submit" id="editButton" style="display:none;"/>&nbsp
            <input type="submit" class="btn btn-danger" value="delete" name="submit" id="deleteButton" style="display:none;"/>
        </form>
        <jsp:include page="resources/js/jsLink.jsp"/>
    </body>
</html>
