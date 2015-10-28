<%-- 
    Document   : UsersPics
    Created on : Sep 24, 2014, 2:52:48 PM
    Author     : Administrator
--%>

<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <body>  
        <article>  
            <figure>
                <%if (request.getAttribute("profilePic") != null) {%>
                <a href="<%=request.getContextPath()%>/Image/<%=request.getAttribute("profilePic")%>" ><img src="<%=request.getContextPath()%>/Thumb/<%=request.getAttribute("profilePic")%>" width=100px></a><br/>
                    <%} else {%> 
                <img width="100px" src="https://oodt.apache.org/images/profile.png" alt="Profile Picture"><br/> <%}%>
                <a><%=request.getAttribute("user")%></a>
            </figure>

            <%  LinkedList<Pic> lsPics = (LinkedList<Pic>) request.getAttribute("Pics");

                if (lsPics == null) {
            %>
            <p>No Pictures found</p>
            <%
            } else {
                Iterator<Pic> iterator;
                iterator = lsPics.iterator();
                while (iterator.hasNext()) {
                    Pic p = (Pic) iterator.next();
            %>
            <figure>
                <a href="<%=request.getContextPath()%>/Image/<%=p.getSUUID()%>" ><img src="<%=request.getContextPath()%>/Thumb/<%=p.getSUUID()%>" alt="<%=p.getSUUID()%>"></a><br/>
                <%if(p.getDc()!=null){%><figcaption><%=p.getDc()%></figcaption> <%}%>

                <figcaption>
                    <% if (p.getComments() != null) {
                            Iterator<Comments> citerator;
                            citerator = p.getComments().iterator();
                            while (citerator.hasNext()) {
                                Comments c = (Comments) citerator.next();%>
                    <a href="/InstagrimXinyue/Images/<%=c.getCommenter()%>"><%=c.getCommenter()%></a> &nbsp;&nbsp;
                    <a class="time"><%=c.getTime()%></a><br/>
                    <a><%=c.getComment()%></a><br/>
                    <% }
                        }%>
                </figcaption>

                <%if (lg != null) {%>
                <form method="POST" action="<%=request.getContextPath()%>/Comment">
                    <textarea name="comment" rows="1" cols="48" placeholder="How do you like this picture?" required></textarea>
                    &nbsp;&nbsp;<input type="submit" value="Comment" >                     
                    <input type="hidden" name="commenter" value="<%=lg.getUsername()%>">
                    <input type="hidden" name="username" value="<%=request.getAttribute("user")%>">
                    <input type="hidden" name="picid" value="<%=p.getSUUID()%>">     
                </form> 

                <%   if (lg.getlogedin() && request.getAttribute("user").equals(lg.getUsername())) {%>
                <form method="POST" action="<%=request.getContextPath()%>/Delete">
                    <input type="hidden" name="delete" value="<%=p.getSUUID()%>">
                    <input type="submit" value="delete">
                </form> 
                <%}
                    }%>
            </figure>
            <% }
                }%>
        </article>

    </body>
</html>
