package uk.ac.dundee.computing.aec.instagrim.models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.UUID;
import uk.ac.dundee.computing.aec.instagrim.lib.AeSimpleSHA1;

public class User {

    Cluster cluster;

    public User() {

    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public boolean RegisterUser(String fname, String lname, String username, String Password, String email) {
        String EncodedPassword = null;
        try {
            EncodedPassword = AeSimpleSHA1.SHA1(Password);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException et) {
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagrimXinyue");

        if (checkUser(username)) {
            return false;
        } else {
            PreparedStatement ps1 = session.prepare("insert into userprofiles (fname, lname, username, password, email) Values(?,?,?,?,?)");
            BoundStatement bs1 = new BoundStatement(ps1);
            session.execute(bs1.bind(fname, lname, username, EncodedPassword, email));
            return true;
        }
    }

    public boolean IsValidUser(String username, String Password) {
        String EncodedPassword = null;
        try {
            EncodedPassword = AeSimpleSHA1.SHA1(Password);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException et) {
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagrimXinyue");
        PreparedStatement ps = session.prepare("select password from userprofiles where username =?");
        BoundStatement boundStatement = new BoundStatement(ps);
        ResultSet rs = session.execute(boundStatement.bind(username));
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return false;
        } else {
            for (Row row : rs) {
                String StoredPass = row.getString("password");
                if (StoredPass.compareTo(EncodedPassword) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public LinkedList getUserProfile(String username) {
        Session session = cluster.connect("instagrimXinyue");
        PreparedStatement ps = session.prepare("SELECT fname,lname,username,email,profilePic from userprofiles where username =?");
        LinkedList<String> userInfo = new LinkedList<>();
        BoundStatement boundStatement = new BoundStatement(ps);
        ResultSet rs = session.execute(boundStatement.bind(username));
        if (rs.isExhausted()) {
            System.out.println("No user info");
        } else {
            for (Row row : rs) {
                userInfo.add(row.getString("fname"));
                userInfo.add(row.getString("lname"));
                userInfo.add(row.getString("username"));
                userInfo.add(row.getString("email"));

                UUID pic = row.getUUID("profilePic");
                if (pic == null) {
                    userInfo.add("");
                } else {
                    userInfo.add(pic.toString());
                }
            }
        }
        return userInfo;
    }

    public boolean changeUserProfile(String fname, String lname, String email, String username) {
        Session session = cluster.connect("instagrimXinyue");
        PreparedStatement ps = session.prepare("UPDATE userprofiles SET fname=?,lname=?,email=? WHERE username =?");
        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute(boundStatement.bind(fname, lname, email, username));
        return true;
    }

    public boolean checkUser(String username) {
        String u = "";
        Session session = cluster.connect("instagrimXinyue");
        PreparedStatement ps = session.prepare("SELECT username from userprofiles where username=?");
        BoundStatement bs = new BoundStatement(ps);
        ResultSet rs = session.execute(bs.bind(username));
        for (Row row : rs) {
            u = row.getString("username");
        }
        return username.equals(u);
    }

}
