<%-- 
    Document   : index
    Created on : Sep 28, 2014, 7:01:44 PM
    Author     : Administrator
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            header {
                background-color:black;
                color:white;
                text-align:center;
                padding:1px;
                font-size: 25px;
            }
            nav {
                line-height:50px;
                background-color:#eeeeee;
                height:770px;
                width:200px;
                float:left;
                padding:10px;
                text-align:center;
                font-size: 20px;
            }             
            section {
                width:350px;
                float:left;
                padding:10px;	 
            }
            footer {
                background-color:black;
                color:white;
                clear:both;
                text-align:center;
                padding:10px;	 	 
            }
        </style>
    </head>
    
    <body>
        <header>
            <h1>InstaGrim</h1>
        </header>
        
        <nav>
            <% LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
            if (lg != null) 
            {
                String UserName = lg.getUsername();
                if (lg.getlogedin()) 
                { %>
                    <a href="upload.jsp">Upload</a><br>
                    <a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Images</a><br>
                    <a href="index.jsp">Log out</a><br>
                <% }
            }
            else
            {%>
                <a href="register.jsp">Register</a><br>
                <a href="login.jsp">Log in</a>
            <%}%>
        </nav>
             
        <section>
            <img src="http://www.goliath.com/wp-content/uploads/2015/06/highlands-photography-guy-photographer-mountains-elevation-landscape-the-camera-the-sky.jpg" 
                 alt="Show Yourself!"
                 style="width:330%"
                 hspace="65%">
        </section>
             
        <footer>
            &COPY; Xinyue Wu
        </footer>
    </body>
</html>
