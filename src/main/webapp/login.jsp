<%-- 
    Document   : login.jsp
    Created on : Sep 28, 2014, 12:04:14 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <body>
        
        <nav>
            <a href="Index">Home</a><br>
            <a href="Register">Register</a><br>
        </nav>
       
        <article>
            <form method="POST"  action="Login">
                User Name <input type="text" name="username" autofocus></li><br>
                Password <input type="password" name="password"></li><br>
                <input type="submit" value="Login"> <br>
            </form>
            
                <% if((Boolean)request.getAttribute("failed") != null){%>
                <p>Wrong username or password!</p><br> <%}%>
        </article>

    </body>
</html>
