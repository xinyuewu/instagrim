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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Styles.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>InstagrimXinyue</title>
    </head>
    <body>
        <header>
            <h1>InstaGrim</h1>
        </header>
        <nav>
            <% LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                if (lg != null) {
                    if (lg.getlogedin()) {
            %>
            <p><%=lg.getUsername()%></p>
            <a href="<%=request.getContextPath()%>/Index">Home</a><br>
            <a href="<%=request.getContextPath()%>/Upload">Upload</a><br>
            <a href="<%=request.getContextPath()%>/Images/<%=lg.getUsername()%>">My Images</a><br>
            <a href="<%=request.getContextPath()%>/UserProfile">My Account</a><br>
            <form method="POST" action="<%=request.getContextPath()%>/Logout"> 
                <button type="submit" class="fakeLink" value="Log out">Log out </button>
            </form>
            <% } else {%>
            <p>Welcome!</p>
            <a href="<%=request.getContextPath()%>/Index">Home</a><br>
            <a href="<%=request.getContextPath()%>/Register">Register</a><br>
            <a href="<%=request.getContextPath()%>/Login">Log in</a>
            <%}
            } else {%>
            <p>Welcome!</p>
            <a href="<%=request.getContextPath()%>/Index">Home</a><br>
            <a href="<%=request.getContextPath()%>/Register">Register</a><br>
            <a href="<%=request.getContextPath()%>/Login">Log in</a>
            <%}%>
        </nav>
    </body>
</html>
