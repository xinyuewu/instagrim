<%-- 
    Document   : index
    Created on : Sep 28, 2014, 7:01:44 PM
    Author     : Administrator
--%>


<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <body>
        <nav>
            <% LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                if (lg != null) {
                    if (lg.getlogedin()) {
            %>
            <p><%=lg.getUsername()%></p>
            <a href="Upload">Upload</a><br>
            <a href="/Instagrim/Images/<%=lg.getUsername()%>">My Images</a><br>
            <a href="UserProfile">My Account</a><br>
            <form method="POST" action="Logout"> 
                <button type="submit" class="fakeLink" value="Log out">Log out </button>
            </form>
            <% } else {%>
            <p>Welcome!</p>
            <a href="Register">Register</a><br>
            <a href="Login">Log in</a>
            <%}
            } else {%>
            <p>Welcome!</p>
            <a href="Register">Register</a><br>
            <a href="Login">Log in</a>
            <%}%>
        </nav>

        <article>

            <%
                LinkedList<Pic> lsPics = (LinkedList<Pic>) request.getAttribute("Pics");
                if (lsPics == null) {
            %>
            <img width="60%" 
                 src="http://www.goliath.com/wp-content/uploads/2015/06/highlands-photography-guy-photographer-mountains-elevation-landscape-the-camera-the-sky.jpg" 
                 alt="Show Yourself!">
            <%
            } else {
                Iterator<Pic> iterator;
                iterator = lsPics.iterator();
                while (iterator.hasNext()) {
                    Pic p = (Pic) iterator.next();
            %>

            <figure>
                <figcaption><a href="/Instagrim/Images/<%=p.getUn()%>"><%=p.getUn()%></figcaption>
                <a href="/Instagrim/Image/<%=p.getSUUID()%>" ><img src="/Instagrim/Thumb/<%=p.getSUUID()%>" alt="<%=p.getSUUID()%>"></a><br/>
                <figcaption><%=p.getDc()%></figcaption>
            </figure>
            <% }
                }%>
        </article>
    </body>
</html>
