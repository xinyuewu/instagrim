package uk.ac.dundee.computing.aec.instagrim.models;

/*
 * Expects a cassandra columnfamily defined as
 * use keyspace2;
 CREATE TABLE Tweets (
 user varchar,
 interaction_time timeuuid,
 tweet varchar,
 PRIMARY KEY (user,interaction_time)
 ) WITH CLUSTERING ORDER BY (interaction_time DESC);
 * To manually generate a UUID use:
 * http://www.famkruithof.net/uuid/uuidgen
 */
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.Bytes;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import static org.imgscalr.Scalr.*;
import org.imgscalr.Scalr.Method;

import uk.ac.dundee.computing.aec.instagrim.lib.*;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;
//import uk.ac.dundee.computing.aec.stores.TweetStore;

public class PicModel {

    Cluster cluster;

    public void PicModel() {

    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public void insertPic(byte[] b, String type, String name, String user, String dc, boolean profilePic) {
        Convertors convertor = new Convertors();

        String types[] = Convertors.SplitFiletype(type);
        ByteBuffer buffer = ByteBuffer.wrap(b);
        int length = b.length;
        java.util.UUID picid = convertor.getTimeUUID();

        byte[] thumbb = picresize(types[1], b);
        int thumblength = thumbb.length;
        ByteBuffer thumbbuf = ByteBuffer.wrap(thumbb);
        byte[] processedb = picdecolour(types[1], b);
        ByteBuffer processedbuf = ByteBuffer.wrap(processedb);
        int processedlength = processedb.length;

        Session session = cluster.connect("instagrim");

        PreparedStatement psInsertPic = session.prepare("insert into pics ( picid, image, thumb, processed, user, interaction_time,imagelength,thumblength,processedlength,type,name,description) values(?,?,?,?,?,?,?,?,?,?,?,?)");
        BoundStatement bsInsertPic = new BoundStatement(psInsertPic);
        Date DateAdded = new Date();
        session.execute(bsInsertPic.bind(picid, buffer, thumbbuf, processedbuf, user, DateAdded, length, thumblength, processedlength, type, name, dc));
        if (profilePic) {
            PreparedStatement psProfilePic = session.prepare("UPDATE userprofiles SET profilePic=? WHERE username =?");
            BoundStatement bsProfilePic = new BoundStatement(psProfilePic);
            session.execute(bsProfilePic.bind(picid, user));
        } else {
            PreparedStatement psInsertPicToUser = session.prepare("insert into userpiclist ( picid, user, pic_added) values(?,?,?)");
            BoundStatement bsInsertPicToUser = new BoundStatement(psInsertPicToUser);
            session.execute(bsInsertPicToUser.bind(picid, user, DateAdded));
        }

        session.close();
    }

    public byte[] picresize(String type, byte[] b) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            BufferedImage BI = ImageIO.read(bais);
            BufferedImage thumbnail = createThumbnail(BI);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(thumbnail, type, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException et) {

        }
        return null;
    }

    public byte[] picdecolour(String type, byte[] b) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            BufferedImage BI = ImageIO.read(bais);
            BufferedImage processed = createProcessed(BI);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(processed, type, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException et) {

        }
        return null;
    }

    public static BufferedImage createThumbnail(BufferedImage img) {
        img = resize(img, Method.SPEED, 500, OP_ANTIALIAS);
        return pad(img, 2);
    }

    public static BufferedImage createProcessed(BufferedImage img) {
        int Width = img.getWidth() - 1;
        img = resize(img, Method.SPEED, Width, OP_ANTIALIAS);
        return pad(img, 4);
    }

    public java.util.LinkedList<Pic> getAllPics() {
        java.util.LinkedList<Pic> Pics = new java.util.LinkedList<>();
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select picid,user,description,interaction_time from Pics");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                ));
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return null;
        } else {
            for (Row row : rs) {
                Pic pic = new Pic();
                java.util.UUID UUID = row.getUUID("picid");
                System.out.println("UUID" + UUID.toString());
                pic.setUUID(UUID);
                String dc = row.getString("description");
                pic.setDc(dc);
                String username = row.getString("user");
                pic.setUn(username);
                Date date=row.getDate("interaction_time");
                pic.setDate(date);
                Pics.add(pic);
            }
                Collections.sort(Pics);
        }
        return Pics;
    }

    public java.util.LinkedList<Pic> getPicsForUser(String User) {
        java.util.LinkedList<Pic> Pics = new java.util.LinkedList<>();
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select picid from userpiclist where user =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        User));
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return null;
        } else {
            for (Row row : rs) {
                Pic pic = new Pic();
                java.util.UUID UUID = row.getUUID("picid");
                System.out.println("UUID" + UUID.toString());
                pic.setUUID(UUID);

                PreparedStatement ps1 = session.prepare("select description from Pics where picid =?");
                ResultSet rs1 = null;
                BoundStatement boundStatement1 = new BoundStatement(ps1);
                rs1 = session.execute(boundStatement1.bind(UUID));
                if (rs1.isExhausted()) {
                    System.out.println("No Images returned");
                    return null;
                } else {
                    for (Row row1 : rs1) {
                        String dc = row1.getString("description");
                        pic.setDc(dc);
                    }
                }

                Pics.add(pic);
            }
        }
        return Pics;
    }

    public Pic getPic(int image_type, java.util.UUID picid) {
        Session session = cluster.connect("instagrim");
        ByteBuffer bImage = null;
        String type = null;
        String dc = null;
        int length = 0;
        try {
            Convertors convertor = new Convertors();
            ResultSet rs = null;
            PreparedStatement ps = null;

            if (image_type == Convertors.DISPLAY_IMAGE) {
                ps = session.prepare("select image,imagelength,type,description from pics where picid =?");
            } else if (image_type == Convertors.DISPLAY_THUMB) {
                ps = session.prepare("select thumb,imagelength,thumblength,type,description from pics where picid =?");
            } else if (image_type == Convertors.DISPLAY_PROCESSED) {
                ps = session.prepare("select processed,processedlength,type,description from pics where picid =?");
            }
            BoundStatement boundStatement = new BoundStatement(ps);
            rs = session.execute( // this is where the query is executed
                    boundStatement.bind( // here you are binding the 'boundStatement'
                            picid));

            if (rs.isExhausted()) {
                System.out.println("No Images returned");
                return null;
            } else {
                for (Row row : rs) {
                    if (image_type == Convertors.DISPLAY_IMAGE) {
                        bImage = row.getBytes("image");
                        length = row.getInt("imagelength");
                    } else if (image_type == Convertors.DISPLAY_THUMB) {
                        bImage = row.getBytes("thumb");
                        length = row.getInt("thumblength");

                    } else if (image_type == Convertors.DISPLAY_PROCESSED) {
                        bImage = row.getBytes("processed");
                        length = row.getInt("processedlength");
                    }

                    type = row.getString("type");
                    dc = row.getString("description");
                }
            }
        } catch (Exception et) {
            System.out.println("Can't get Pic" + et);
            return null;
        }
        session.close();
        Pic p = new Pic();
        p.setPic(bImage, length, type);

        return p;

    }

}
