<%-- 
    Document   : index
    Created on : Sep 28, 2014, 7:01:44 PM
    Author     : Administrator
--%>


<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <body>
        <article>
            <% LinkedList<Pic> lsPics = (LinkedList<Pic>) request.getAttribute("Pics");
                if (lsPics == null) { %>
            <img class="oneImage" src="http://www.goliath.com/wp-content/uploads/2015/06/highlands-photography-guy-photographer-mountains-elevation-landscape-the-camera-the-sky.jpg" alt="Show Yourself!">
            
            <%} else {
                Iterator<Pic> iterator;
                iterator = lsPics.iterator();
                while (iterator.hasNext()) {
                    Pic p = (Pic) iterator.next();
            %>
            <figure>
                <p><a href="/InstagrimXinyue/Images/<%=p.getUn()%>"><%=p.getUn()%></p>
                
                <a href="/InstagrimXinyue/Image/<%=p.getSUUID()%>" ><img src="/InstagrimXinyue/Thumb/<%=p.getSUUID()%>" alt="<%=p.getSUUID()%>"></a><br/>
                
                <% if (p.getDc() != null) {%><figcaption><%=p.getDc()%></figcaption><%}%>
                
                <figcaption>
                    <% if (p.getComments() != null) {
                            Iterator<Comments> citerator;
                            citerator = p.getComments().iterator();
                            while (citerator.hasNext()) {
                                Comments c = (Comments) citerator.next();%>
                    <a href="/InstagrimXinyue/Images/<%=c.getCommenter()%>"><%=c.getCommenter()%></a> &nbsp;&nbsp;
                    <a class="time"><%=c.getTime()%></a><br/>
                    <a><%=c.getComment()%></a><br/>
                    <%}
                        }%>
                </figcaption>
                
                <%if (lg != null) {
                            if (lg.getlogedin()) {%>
                <form method="POST" action="Comment">
                    <textarea name="comment" rows="1" cols="47" placeholder="How do you like this picture?" required></textarea>
                    &nbsp;&nbsp;<input type="submit" value="Comment" > 
                    <input type="hidden" name="commenter" value="<%=lg.getUsername()%>">
                    <input type="hidden" name="username" value="<%=p.getUn()%>">
                    <input type="hidden" name="picid" value="<%=p.getSUUID()%>">
                </form> <%}%>
                <br/><br/>
            </figure>
            <% }
                    }
                }%>
        </article>
    </body>
</html>
