<%-- 
    Document   : upload
    Created on : Sep 22, 2014, 6:31:50 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <body>
   
        <nav>
            <a href="index.jsp">Home</a><br>
            <a href="/Instagrim/Images/majed">Sample Images</a><br>
            <a href="userProfile.jsp">My Account</a><br>
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
