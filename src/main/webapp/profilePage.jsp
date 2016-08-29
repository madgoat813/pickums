<%-- 
    Document   : profilePage
    Created on : Aug 27, 2016, 9:20:44 PM
    Author     : Taylor
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${userLogin}'s Profile Page</title>
        <jsp:include page="resources/css/headingLink.jsp"/>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <h1>Hello ${userLogin}</h1>
        <jsp:include page="resources/js/jsLink.jsp"/>
    </body>
</html>
