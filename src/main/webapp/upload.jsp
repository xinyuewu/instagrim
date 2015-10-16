<%-- 
    Document   : upload
    Created on : Sep 22, 2014, 6:31:50 PM
    Author     : Administrator
--%>

<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <body>
   
        <nav>
            <% LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");%>
            <a href="Index">Home</a><br>
            <a href="/Instagrim/Images/<%=lg.getUsername()%>">My Images</a><br>
            <a href="UserProfile">My Account</a><br>
            <form method="POST" action="Logout"> 
                <button type="submit" class="fakeLink" value="Log out">Log out </button>
            </form>
        </nav>
 
        <article>
            <h3>File Upload</h3>
            <form method="POST" enctype="multipart/form-data" action="Image">
                File to upload: <input type="file" name="upfile"><br/>
                <br/>
                <input type="submit" value="Press"> to upload the file!
            </form>
        </article>
        
    </body>
</html>
