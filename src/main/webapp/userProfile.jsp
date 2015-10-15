<%-- 
    Document   : userProfile
    Created on : 14-Oct-2015, 19:10:40
    Author     : xinyuewu
--%>

<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <body>
        
        <nav>
            <a href="index.jsp">Home</a><br>
            <a href="/Instagrim/Images/majed">Sample Images</a><br>
            <form method="POST" action="Logout"> 
                <button type="submit" class="fakeLink" value="Log out">Log out </button>
            </form>
        </nav>
        
        <article>
            <% LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");%>
            <h1>Hi <%=lg.getUsername()%>!</h1>
            <form method="POST"  action="UserProfile">
                First Name  <input type="text" name="fname" value="<%=(String)request.getAttribute("fname")%>"><br>
                Last Name <input type="text" name="lname" value="<%=(String)request.getAttribute("lname")%>"><br>
                User Name  <input type="text" name="username" value="<%=(String)request.getAttribute("username")%>" disabled="true"><br>
                Gender <input type="radio" name="gender" value="<%=(String)request.getAttribute("gender")%>">Male</input>
                       <input type="radio" name="gender" value="<%=(String)request.getAttribute("gender")%>">Female</input><br>
                Birthday <input type="date" name="birthday" max="2015-1-1" value="<%=(String)request.getAttribute("birthday")%>"><br>
                Email <input type="email" name="email" value="<%=(String)request.getAttribute("email")%>" required><br>
                <br><input type="submit" value="Submit"> 
            </form>
        </article>

    </body>
</html>
