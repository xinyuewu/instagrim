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
            <h1>Register as user</h1>
            <form method="POST"  action="Register">
                <div style=" margin: 0 auto;">
                <div class="left">
                    <a>First Name</a><br/>
                    <a>Last Name</a><br/>
                    <a>Username</a><a class="asterisk">*</a><br/>
                    <a>Password</a><a class="asterisk">*</a><br/>
                    <a>Email</a><a class="asterisk">*</a><br/>
                </div>
                <div class="right">
                    <input type="text" name="fname" class="input"><br>
                    <input type="text" name="lname" class="input"><br>
                    <input type="text" name="username" class="input" required><br>
                    <input type="password" name="password" class="input" required><br>
                    <input type="email" name="email" class="input" required><br>
                </div>
                </div>
                <br><input class="click" type="submit" value="Creat account"> 
            </form>
            <% if ((Boolean) request.getAttribute("registerFailed") != null) {%>
            <p>The username has been registered. Please select a new username!</p><br> <%}%>
        </article>

    </body>
</html>
