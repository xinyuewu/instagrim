<%-- 
    Document   : register.jsp
    Created on : Sep 28, 2014, 6:29:51 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
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
                height:770px;
                padding:10px;
                text-align:center;
                font-size: 20px;
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
           <a href="index.jsp">Home</a><br>
           <a href="login.jsp">Log in</a><br>
        </nav>
        
        <section>
            <h3>Register as user</h3>
            <form method="POST"  action="Register">
                Title <input type="text" name="username"><br>
                First Name  <input type="text" name="username"><br>
                Last Name <input type="text" name="username"><br>
                User Name  <input type="text" name="username" required><br>
                Password <input type="password" name="password" required><br>
                Gender <input type="radio" name="gender" value="male">Male</input>
                       <input type="radio" name="gender" value="female">Female</input><br>
                Birthday <input type="date" name="birthday" max="2015-1-1"><br>
                Email <input type="email" name="email" required><br>
                <input type="submit" value="Creat account"> 
            </form>
        </section>
        
        <footer>
            &COPY; Xinyue Wu
        </footer>
    </body>
</html>
