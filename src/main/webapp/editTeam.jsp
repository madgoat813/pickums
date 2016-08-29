<%-- 
    Document   : editTeam
    Created on : Aug 29, 2016, 11:36:56 AM
    Author     : Taylor
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Team</title>
        <jsp:include page="resources/css/headingLink.jsp"/>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <h1>Edit Team</h1>
        <form method="POST" action="TeamController" >
            <br/>
            <div class="alert alert-info" role="alert">Add Team</div>
            <table class="table">
                <tr>
                <thead>
                <td>Team Id</td>
                </thead>
                <td><input type="text" name="teamId" value="${team.teamId}" class="form-control" required readonly/></td>
                </tr>
                <tr>
                <thead>
                <td>Team Name</td>
                </thead>
                <td><input type="text" name="teamName" value="${team.teamName}" class="form-control" required/></td>
                </tr>
                <tr>
                <thead>
                <td>Team City</td>
                </thead>
                <td><input type="text" name="teamCity" value="${team.teamCity}" class="form-control" required/></td>
                </tr>

            </table>
            <input type="submit" value="save" name="action" class="btn btn-primary"/>&nbsp;
        </form>
        <a href="TeamController?action=cancel"><button  class="btn btn-danger">Cancel</button></a>
        <jsp:include page="resources/js/jsLink.jsp"/>
    </body>
</html>
