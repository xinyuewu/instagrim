<%-- 
    Document   : register.jsp
    Created on : Sep 28, 2014, 6:29:51 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <body>  
        <article>
            <h3>Register as user</h3>
            <form method="POST"  action="Register">
                First Name  <input type="text" name="fname"><br>
                Last Name <input type="text" name="lname"><br>
                <a class="asterisk">*</a>User Name <input type="text" name="username" required><br>
                <a class="asterisk">*</a>Password <input type="password" name="password" required><br>
                <a class="asterisk">*</a>Email <input type="email" name="email" required><br>
                <br><input type="submit" value="Creat account"> 
            </form>
            <% if ((Boolean) request.getAttribute("registerFailed") != null) {%>
            <p>The username has been registered. Please select a new username!</p><br> <%}%>
        </article>

    </body>
</html>
