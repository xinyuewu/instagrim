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
            <a href="index.jsp">Home</a><br>
            <a href="/Instagrim/Images/majed">Sample Images</a></li>
        </nav>
       
        <article>
            <form method="POST"  action="Login">
                User Name <input type="text" name="username" autofocus></li><br/>
                Password <input type="password" name="password"></li><br/>
                <input type="submit" value="Login"> 
            </form>
        </article>

    </body>
</html>
