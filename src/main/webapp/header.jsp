<%-- 
    Document   : header
    Created on : 13-Oct-2015, 15:08:20
    Author     : xinyuewu
--%>

<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Styles.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>InstagrimXinyue</title>
    </head>
    <body>
        <header>
            <h1><a class="homeLink" href="<%=request.getContextPath()%>/Index">InstaGrim</a></h1>
        </header>
        <nav>
            <% LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                if (lg != null) {
                    if (lg.getlogedin()) {
            %>
            <h1><%=lg.getUsername()%></h1>
            <ul id="menu">
                <li><a href="<%=request.getContextPath()%>/Index">Home</a></li><br>
                <li><a href="<%=request.getContextPath()%>/Upload">Upload</a></li><br>
                <li><a href="<%=request.getContextPath()%>/Images/<%=lg.getUsername()%>">My Images</a></li><br>
                <li><a href="<%=request.getContextPath()%>/UserProfile">My Account</a></li><br>
                <form method="POST" action="<%=request.getContextPath()%>/Logout"> 
                    <li><button type="submit" class="fakeLink" value="Log out">Log out </button></li>
                </form>
                <% }
                } else {%>
                <h1>Welcome!</h1>
                <ul id="menu">
                    <li><a href="<%=request.getContextPath()%>/Index">Home</a></li><br>
                    <li><a href="<%=request.getContextPath()%>/Register">Register</a></li><br>
                    <li><a href="<%=request.getContextPath()%>/Login">Log in</a></li>
                        <%}%>
                </ul>
        </nav>
    </body>
</html>
