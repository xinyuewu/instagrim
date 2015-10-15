/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import uk.ac.dundee.computing.aec.instagrim.lib.AeSimpleSHA1;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

/**
 *
 * @author Administrator
 */
public class User {

    Cluster cluster;

    public User() {

    }

    public boolean RegisterUser(String fname, String lname, String username, String Password, String gender, String birthday, String email) {
        AeSimpleSHA1 sha1handler = new AeSimpleSHA1();
        String EncodedPassword = null;
        try {
            EncodedPassword = sha1handler.SHA1(Password);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException et) {
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("insert into userprofiles (fname, lname, username, password, gender, birthday, email) Values(?,?,?,?,?,?,?)");

        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        fname, lname, username, EncodedPassword, gender, birthday, email));
        //We are assuming this always works.  Also a transaction would be good here !

        return true;
    }

    public boolean IsValidUser(String username, String Password) {
        AeSimpleSHA1 sha1handler = new AeSimpleSHA1();
        String EncodedPassword = null;
        try {
            EncodedPassword = sha1handler.SHA1(Password);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException et) {
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select password from userprofiles where username =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));
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

        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select fname,lname,username,gender,birthday,email from userprofiles where username =?");
        LinkedList<String> userInfo = new LinkedList<>();
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));
        if (rs.isExhausted()) {
            System.out.println("No user info");
            for (int i = 0; i < 6; i++) {
                userInfo.add(null);
            }
        } else {
            for (Row row : rs) {
                userInfo.add(row.getString("fname"));
                System.out.println("name :"+row.getString("fname"));
                userInfo.add(row.getString("lname"));
                System.out.println("lname :"+row.getString("lname"));
                userInfo.add(row.getString("username"));
                System.out.println("username :"+row.getString("username"));
                userInfo.add(row.getString("gender"));
                System.out.println("gender :"+row.getString("gender"));
                userInfo.add(row.getString("birthday"));
                System.out.println("birthday :"+row.getString("birthday"));
                userInfo.add(row.getString("email"));
            }
        }
        return userInfo;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
    
    public boolean changeUserProfile(String fname, String lname, String gender, String birthday, String email, String username) {
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("UPDATE userprofiles SET fname=?,lname=?,gender=?,birthday=?,email=? WHERE username =?" );
        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        fname, lname, gender, birthday, email, username));
        System.out.println("fname = "+ fname);
        System.out.println("lname = "+ lname);
        System.out.println("gender = "+ gender);
        System.out.println("birthday = "+ birthday);
        System.out.println("email = "+ email);
        //We are assuming this always works.  Also a transaction would be good here !

        return true;
    }
    

}
