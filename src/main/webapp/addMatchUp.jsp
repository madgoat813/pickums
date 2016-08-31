<%-- 
    Document   : addMatchUp
    Created on : Aug 30, 2016, 3:48:54 AM
    Author     : Taylor
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Match Page</title>
        <jsp:include page="resources/css/headingLink.jsp"/>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <jsp:include page="resources/js/jsLink.jsp"/>
        <h1>Add Match Page</h1>
        <form method="POST" action="MatchController" >
            <c:forEach begin="1" end="2" varStatus="i">
                ${i.count}
                <select  id="t${i.count}" onchange="check();">
                    <option value="">Choose</option>
                    <option value="1" >49ers</option>
                    <option value="2" >bears</option>
                    <option value="3" >bengals</option>
                    <option value="4" >bills</option>
                    <option value="5" >broncos</option>
                    <option value="6" >browns</option>
                    <option value="7" >bucks</option>
                    <option value="8" >cardnals</option>
                    <option value="9" >chargers</option>
                    <option value="10" >chiefs</option>
                    <option value="11" >colts</option>
                    <option value="12" >cowboys</option>
                    <option value="13" >dolphins</option>
                    <option value="14" >eagles</option>
                    <option value="15" >falcons</option>
                    <option value="16" >giants</option>
                    <option value="17" >jaguars</option>
                    <option value="18" >jets</option>
                    <option value="19" >lions</option>
                    <option value="20" >packers</option>
                    <option value="21" >panthers</option>
                    <option value="22" >patriots</option>
                    <option value="23" >raiders</option>
                    <option value="24" >rams</option>
                    <option value="25" >ravens</option>
                    <option value="26" >redskins</option>
                    <option value="27" >saints</option>
                    <option value="28" >seahawks</option>
                    <option value="29" >steelers</option>
                    <option value="30" >texans</option>
                    <option value="31" >titans</option>
                    <option value="32" >vikings</option>
                </select>
            </c:forEach>
            <input type="submit" value="add" name="action" class="btn btn-primary"/>&nbsp;
        </form>
        <a href="MatchController?action=cancel"><button  class="btn btn-danger">Cancel</button></a>

    </body>
</html>
