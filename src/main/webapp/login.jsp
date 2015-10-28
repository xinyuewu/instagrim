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
            <h1>Login</h1>
            <form method="POST"  action="Login">
                <div style=" margin: 0 auto;">
                    <div class="left">
                        <a>Username</a><br/>
                        <a>Password</a><br/>
                    </div>
                    <div class="right">
                        <input type="text" name="username" class="input" required><br>
                        <input type="password" name="password" class="input" required><br>
                    </div>
                </div>
                <input class="click" type="submit" value="Login"> <br>
            </form>
            <% if ((Boolean) request.getAttribute("failed") != null) {%>
            <p>Wrong username or password!</p><br> <%}%>
        </article>
    </body>
</html>
