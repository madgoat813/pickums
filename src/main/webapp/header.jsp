
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="resources/css/headingLink.jsp"/>
<nav class='navbar navbar-inverse navbar-fixed-top'>
    <div class='container'>
        <div class="navbar-header">

            <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="UserController?action=login" class="navbar-brand navbar-left" style="text-align: center">
                Home
            </a>
        </div>
        <div class="navbar-collapse collapse" id="navbar-main">
            <ul class="nav navbar-nav" navbar-left>
                <li class="dropdown">
                    <a class="dropdown-toggle"  id="dropdownMenu1" data-toggle="dropdown" >
                        Administrative Tools
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                        <li><a href="TeamController?action=list">Teams</a></li>
                        <li><a href="UserController?action=list">Users</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle"  id="dropdownMenu2" data-toggle="dropdown" >
                        ${userLogin}<span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
                        <li><a href="profilePage.jsp">View Profile</a></li>
                        <li><a href="UserController?action=logout">Logout</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
<jsp:include page="resources/js/jsLink.jsp"/>