<%-- 
    Document   : UsersPics
    Created on : Sep 24, 2014, 2:52:48 PM
    Author     : Administrator
--%>

<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="../Styles.css" />
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
            <a href="../Index">Home</a><br>
            <a href="../Upload">Upload</a><br>
            <a href="/Instagrim/Images/<%=lg.getUsername()%>">My Images</a><br>
            <a href="../UserProfile">My Account</a><br>
            <form method="POST" action="Logout"> 
                <button type="submit" class="fakeLink" value="Log out">Log out </button>
            </form>
            <% } else {%>
            <p>Welcome!</p>
            <a href="../Index">Home</a><br>
            <a href="../Register">Register</a><br>
            <a href="../Login">Log in</a>
            <%}
            } else {%>
            <p>Welcome!</p>
            <a href="../Index">Home</a><br>
            <a href="../Register">Register</a><br>
            <a href="../Login">Log in</a>
            <%}%>
        </nav>

        <article>  

            <figcaption><a><%=request.getAttribute("user")%></a></figcaption>
                    <% if (lg != null) {
                            if (lg.getlogedin()) {
                                if (!request.getAttribute("user").equals(lg.getUsername())) {
                                    System.out.println("myUn: " + lg.getUsername() + " otherUser: " + request.getAttribute("user"));
                    %> <button type="submit" value="Follow">Follow</button><br> <%} else {%><br><%}
                            }
                        }

                        LinkedList<Pic> lsPics = (LinkedList<Pic>) request.getAttribute("Pics");

                        if (lsPics == null) {
            %>
            <p>No Pictures found</p>
            <%
            } else {
                Iterator<Pic> iterator;
                iterator = lsPics.iterator();
                while (iterator.hasNext()) {
                    Pic p = (Pic) iterator.next();
            %>
            <figure>
                <a href="/Instagrim/Image/<%=p.getSUUID()%>" ><img src="/Instagrim/Thumb/<%=p.getSUUID()%>" alt="<%=p.getSUUID()%>"></a><br/>
                <figcaption><%=p.getDc()%></figcaption>
            </figure>
            <% }
                }%>

        </article>

    </body>
</html>
