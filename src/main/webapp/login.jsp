<%-- 
    Document   : login.jsp
    Created on : Sep 28, 2014, 12:04:14 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <body>
        <article>
            <form method="POST"  action="Login">
                <br>User Name <input type="text" name="username" autofocus required></li><br><br>
                Password &nbsp;<input type="password" name="password" required></li><br><br>
                <input type="submit" value="Login"> <br>
            </form>
            <% if((Boolean)request.getAttribute("failed") != null){%>
            <p>Wrong username or password!</p><br> <%}%>
        </article>
    </body>
</html>
