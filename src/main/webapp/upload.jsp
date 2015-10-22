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
            <p><%=lg.getUsername()%></p>
            <a href="Index">Home</a><br>
            <a href="/Instagrim/Images/<%=lg.getUsername()%>">My Images</a><br>
            <a href="UserProfile">My Account</a><br>
            <form method="POST" action="Logout"> 
                <button type="submit" class="fakeLink" value="Log out">Log out </button>
            </form>
        </nav>
 
        <article>
            <h3>Image Upload</h3>
            <form method="POST" enctype="multipart/form-data" action="Image">
                <input type="file" name="upfile"><br/><br/>
                <textarea name="message" rows="5" cols="30" placeholder="Describe your picture!"></textarea>
                <br/><br/>
                <input type="submit" value="Upload" onclick="<%session.setAttribute("Location","upload");%>" > 
            </form>
        </article>
        
    </body>
</html>
