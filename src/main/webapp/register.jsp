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
           <a href="Index">Home</a><br>
           <a href="Login">Log in</a><br>
        </nav>
        
        <article>
            <h3>Register as user</h3>
            <form method="POST"  action="Register">
                First Name  <input type="text" name="fname"><br>
                Last Name <input type="text" name="lname"><br>
                User Name  <input type="text" name="username" required><br>
                Password <input type="password" name="password" required><br>
                Gender <input type="radio" name="gender" value="male">Male</input>
                       <input type="radio" name="gender" value="female">Female</input><br>
                Birthday <input type="date" name="birthday" max="2015-1-1"><br>
                Email <input type="email" name="email" required><br>
                <br><input type="submit" value="Creat account"> 
            </form>
        </article>
        
    </body>
</html>
