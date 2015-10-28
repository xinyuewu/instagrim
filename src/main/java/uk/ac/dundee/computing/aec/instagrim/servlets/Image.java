package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

/**
 * Servlet implementation class Image
 */
@WebServlet(urlPatterns = {
    "/Image",
    "/Image/*",
    "/Thumb/*",
    "/Images",
    "/Images/*"
})
@MultipartConfig

public class Image extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Cluster cluster;
    private HashMap CommandsMap = new HashMap();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Image() {
        super();
        // TODO Auto-generated constructor stub
        CommandsMap.put("Image", 1);
        CommandsMap.put("Images", 2);
        CommandsMap.put("Thumb", 3);

    }

    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String args[] = Convertors.SplitRequestPath(request);
        System.out.println("args[0]=" + (String) args[0] + "     args[1]=" + (String) args[1] + "    args[2]=" + (String) args[2]);
        int command;
        try {
            command = (Integer) CommandsMap.get(args[1]);
        } catch (Exception et) {
            error("Bad Operator", response);
            return;
        }
        switch (command) {
            case 1:
                DisplayImage(Convertors.DISPLAY_PROCESSED, args[2], response, request);
                break;
            case 2:
                DisplayImageList(args[2], request, response);
                break;
            case 3:
                DisplayImage(Convertors.DISPLAY_THUMB, args[2], response, request);
                break;
            default:
                error("Bad Operator", response);
        }
    }

    private void DisplayImageList(String User, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setCluster(cluster);
        if (user.checkUser(User)) {
            PicModel tm = new PicModel();
            tm.setCluster(cluster);
            LinkedList<Pic> lsPics = tm.getPicsForUser(User);
            request.setAttribute("Pics", lsPics);
            request.setAttribute("user", User);

            UUID pp = tm.getProfilePic(User);
            request.setAttribute("profilePic", pp);

            RequestDispatcher rd = request.getRequestDispatcher("/UsersPics.jsp");
            rd.forward(request, response);
        } else {
            response.sendError(404);
        }

    }

    private void DisplayImage(int type, String Image, HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
        UUID picid = UUID.fromString(Image);
        Pic p = tm.getPic(type, picid);

        OutputStream out = response.getOutputStream();

        response.setContentType(p.getType());
        response.setContentLength(p.getLength());

        InputStream is = new ByteArrayInputStream(p.getBytes());
        BufferedInputStream input = new BufferedInputStream(is);
        byte[] buffer = new byte[8192];
        for (int length = 0; (length = input.read(buffer)) > 0;) {
            out.write(buffer, 0, length);
        }
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = "";
        for (Part part : request.getParts()) {
            if (part.getName().equals("message")) {
                part.delete();
                break;
            }
            String type = part.getContentType();

            if (!type.startsWith("image/")) {
                request.setAttribute("invalidType", true);
                RequestDispatcher rd = request.getRequestDispatcher("upload.jsp");
                rd.forward(request, response);
            }
            String filename = part.getSubmittedFileName();

            InputStream is = request.getPart(part.getName()).getInputStream();
            int i = is.available();

            LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");

            if (lg.getlogedin()) {
                username = lg.getUsername();
                if (i > 0) {
                    byte[] b = new byte[i + 1];
                    is.read(b);
                    PicModel tm = new PicModel();
                    tm.setCluster(cluster);
                    String description = request.getParameter("message");
                    if (session.getAttribute("Location").equals("profile")) {
                        tm.insertPic(b, type, filename, username, description, true);
                    } else {
                        tm.insertPic(b, type, filename, username, description, false);
                    }
                    is.close();
                }

            }
        }
        if (session.getAttribute("Location").equals("profile")) {
            response.sendRedirect("UserProfile");
            //RequestDispatcher rd = request.getRequestDispatcher("/userProfile.jsp");
            // rd.forward(request, response);
        } else {
            //response.sendRedirect("Image");
            response.sendRedirect("/InstagrimXinyue/Images/" + username);
            // RequestDispatcher rd = request.getRequestDispatcher("Image");
            // rd.forward(request, response);
        }

    }

    private void error(String mess, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = null;
        out = new PrintWriter(response.getOutputStream());
        out.println("<h1>You have a na error in your input</h1>");
        out.println("<h2>" + mess + "</h2>");
        out.close();
        return;
    }
}
