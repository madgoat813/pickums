<%-- 
    Document   : editUser
    Created on : Aug 29, 2016, 8:31:41 AM
    Author     : Taylor
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit User</title>
        <jsp:include page="resources/css/headingLink.jsp"/>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <h1>Edit User</h1>
        <form method="POST" action="UserController" >
            <br/>
            <div class="alert alert-info" role="alert">Add User</div>
            <table class="table">
                <tr>
                <thead>
                <td>User Id</td>
                </thead>
                <td><input type="text" name="userId" value="${user.userId}" class="form-control" required readonly/></td>
                </tr>
                <thead>
                <td>Username</td>
                </thead>
                <td><input type="text" name="username" value="${user.userName}" class="form-control" required/></td>
                </tr>
                <tr>
                <thead>
                <td>Password</td>
                </thead>
                <td><input type="text" name="password" value="${user.password}" class="form-control" required/></td>
                </tr>
                <thead>
                <td>Email</td>
                </thead>
                <td><input type="text" name="email" value="${user.email}" class="form-control" required/></td>
                </tr>
            </table>
            <input type="submit" value="save" name="action" class="btn btn-primary"/>&nbsp;
        </form>
        <a href="UserController?action=cancel"><button  class="btn btn-danger">Cancel</button></a>
        <jsp:include page="resources/js/jsLink.jsp"/>
    </body>
</html>
