/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;

/**
 *
 * @author xinyuewu
 */
public class UserProfile extends HttpServlet {
    
        Cluster cluster=null;

    
        public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user =new User();
        user.setCluster(cluster);
        HttpSession session =request.getSession();
        LoggedIn lg=(LoggedIn)session.getAttribute("LoggedIn");
        LinkedList<String> userInfo=user.getUserProfile(lg.getUsername());

        request.setAttribute("fname",userInfo.get(0));
        request.setAttribute("lname",userInfo.get(1));
        request.setAttribute("username",userInfo.get(2));
        request.setAttribute("email",userInfo.get(3));
        request.setAttribute("profilePic",userInfo.get(4));
        System.out.println("userInfo.get(4)"+userInfo.get(4));
        
        RequestDispatcher rd;
        rd=request.getRequestDispatcher("userProfile.jsp");
        rd.forward(request, response);
        
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        String fname=request.getParameter("fname");
        String lname=request.getParameter("lname");
        String username=request.getParameter("username");
        String email=request.getParameter("email");
        User us=new User();
        us.setCluster(cluster);
        us.changeUserProfile(fname, lname, email, username);
     
        response.sendRedirect("UserProfile");
        
	//RequestDispatcher rd;
      //  rd=request.getRequestDispatcher("userProfile.jsp");
       // rd.forward(request, response);
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
