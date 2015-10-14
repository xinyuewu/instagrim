<%-- 
    Document   : index
    Created on : Sep 28, 2014, 7:01:44 PM
    Author     : Administrator
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>

    <body>

        <nav>
            <% LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                if (lg != null) {
                    String UserName = lg.getUsername();
                    if (lg.getlogedin()) {
            %>
            <a href="upload.jsp">upload</a><br>
            <a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Images</a><br>
            <form method="POST"  action="Logout">
                <input type="submit" class="fakeLink" value="Log out"/>
            </form>
            <% } else {%>
            <a href="register.jsp">Register</a><br>
            <a href="login.jsp">Log in</a>
            <%}
                } else {%>
            <a href="register.jsp">Register</a><br>
            <a href="login.jsp">Log in</a>
            <%}%>
        </nav>

        <article>
            <img width="60%" 
                 src="http://www.goliath.com/wp-content/uploads/2015/06/highlands-photography-guy-photographer-mountains-elevation-landscape-the-camera-the-sky.jpg" 
                 alt="Show Yourself!">
        </article>

    </body>
</html>
