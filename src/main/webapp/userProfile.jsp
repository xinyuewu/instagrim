<%-- 
    Document   : userProfile
    Created on : 14-Oct-2015, 19:10:40
    Author     : xinyuewu
--%>

<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <body>
        <article>
            <h1>Hi <%=lg.getUsername()%>!</h1>
            <form method="POST" enctype="multipart/form-data" action="Image">
                <%if (request.getAttribute("profilePic") != "") {System.out.println(request.getAttribute("profilePic"));%>
                    <a href="/InstagrimXinyue/Image/<%=request.getAttribute("profilePic")%>" ><img src="/InstagrimXinyue/Thumb/<%=request.getAttribute("profilePic")%>" width=200px></a><br/>
                        <%} else {%> 
                    <img width="200px" src="https://oodt.apache.org/images/profile.png" alt="Profile Picture"> <%}%>
                    <input type="file" name="upfile"> &nbsp;&nbsp;
                <input type="submit" value="Upload" onclick="<%session.setAttribute("Location", "profile");%>" > <br><br>
            </form>
            <form method="POST"  action="UserProfile">
                First Name  <input type="text" name="fname" value="<%=(String) request.getAttribute("fname")%>"><br>
                Last Name <input type="text" name="lname" value="<%=(String) request.getAttribute("lname")%>"><br>
                User Name  <input type="text" name="username" value="<%=(String) request.getAttribute("username")%>" readonly><br>
                Email &nbsp;&nbsp;&nbsp;<input type="email" name="email" value="<%=(String) request.getAttribute("email")%>" required><br>
                <br><input type="submit" value="Update"> 
            </form>
        </article>
    </body>
</html>
