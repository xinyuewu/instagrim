<%-- 
    Document   : register.jsp
    Created on : Sep 28, 2014, 6:29:51 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <body>  
        
        <nav>
           <p>Welcome!</p>
           <a href="Index">Home</a><br>
           <a href="Login">Log in</a><br>
        </nav>
        
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
        </article>
        
    </body>
</html>
