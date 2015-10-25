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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;
import javax.imageio.ImageIO;
import static org.imgscalr.Scalr.*;
import org.imgscalr.Scalr.Method;
import uk.ac.dundee.computing.aec.instagrim.lib.*;
import uk.ac.dundee.computing.aec.instagrim.stores.Comments;
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
        UUID picid = convertor.getTimeUUID();

        byte[] thumbb = picresize(types[1], b);
        int thumblength = thumbb.length;
        ByteBuffer thumbbuf = ByteBuffer.wrap(thumbb);
        byte[] processedb = picdecolour(types[1], b);
        ByteBuffer processedbuf = ByteBuffer.wrap(processedb);
        int processedlength = processedb.length;

        Session session = cluster.connect("instagrimXinyue");

        PreparedStatement psInsertPic = session.prepare("insert into pics ( picid, image, thumb, processed, user, interaction_time,imagelength,thumblength,processedlength,type,name,description) values(?,?,?,?,?,?,?,?,?,?,?,?)");
        BoundStatement bsInsertPic = new BoundStatement(psInsertPic);
        Date DateAdded = new Date();
        session.execute(bsInsertPic.bind(picid, buffer, thumbbuf, processedbuf, user, DateAdded, length, thumblength, processedlength, type, name, dc));
        if (profilePic) {

            PreparedStatement ps3 = session.prepare("DELETE from pics WHERE picid=?");
            BoundStatement bs3 = new BoundStatement(ps3);
            session.execute(bs3.bind(getProfilePic(user)));

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
        Session session = cluster.connect("instagrimXinyue");
        PreparedStatement ps = session.prepare("select picid,user,description,interaction_time from Pics");
        BoundStatement boundStatement = new BoundStatement(ps);
        ResultSet rs = session.execute(boundStatement.bind());
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return null;
        } else {
            for (Row row : rs) {
                Pic pic = new Pic();
                java.util.UUID UUID = row.getUUID("picid");
                pic.setUUID(UUID);
                String dc = row.getString("description");
                pic.setDc(dc);
                String username = row.getString("user");
                pic.setUn(username);
                Date date = row.getDate("interaction_time");
                pic.setDate(date);
                LinkedList<Comments> c = getComment(UUID);
                pic.setComments(c);
                if (getProfilePic(username) != null) {
                    if (!getProfilePic(username).equals(UUID)) {
                        Pics.add(pic);
                    }
                } else {
                    Pics.add(pic);
                }
            }
            Collections.sort(Pics);
        }
        return Pics;
    }

    public LinkedList<Pic> getPicsForUser(String User) {
        LinkedList<Pic> Pics = new LinkedList<>();
        Session session = cluster.connect("instagrimXinyue");
        PreparedStatement ps = session.prepare("select picid from userpiclist where user =?");
        BoundStatement boundStatement = new BoundStatement(ps);
        ResultSet rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        User));
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return null;
        } else {
            for (Row row : rs) {
                Pic pic = new Pic();
                java.util.UUID UUID = row.getUUID("picid");
                pic.setUUID(UUID);

                LinkedList<Comments> c = getComment(UUID);
                pic.setComments(c);

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
        Session session = cluster.connect("instagrimXinyue");
        ByteBuffer bImage = null;
        String type = null;
        String dc = null;
        int length = 0;
        try {

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

    public void deletePic(UUID picid) {
        Session session = cluster.connect("instagrimXinyue");

        PreparedStatement ps1 = session.prepare("DELETE from Pics WHERE picid =?");
        BoundStatement bs1 = new BoundStatement(ps1);
        session.execute(bs1.bind(picid));

        PreparedStatement ps2 = session.prepare("SELECT * from userpiclist WHERE picid =?");
        BoundStatement bs2 = new BoundStatement(ps2);
        ResultSet rs = session.execute(bs2.bind(picid));
        String username = "";
        Date date = new Date();
        for (Row row : rs) {
            username = row.getString("user");
            date = row.getDate("pic_added");
        }

        PreparedStatement ps3 = session.prepare("DELETE from userpiclist WHERE user=? and pic_added=?");
        BoundStatement bs3 = new BoundStatement(ps3);
        session.execute(bs3.bind(username, date));

        PreparedStatement ps4 = session.prepare("DELETE from comments WHERE picid=?");
        BoundStatement bs4 = new BoundStatement(ps4);
        session.execute(bs4.bind(picid));

        session.close();
    }

    public void setComment(UUID picid, String commenter, String comment) {
        Session session = cluster.connect("instagrimXinyue");
        Date date = new Date();

        PreparedStatement ps = session.prepare("INSERT into comments ( picid, commenter, time, comment) values(?,?,?,?)");
        BoundStatement bs = new BoundStatement(ps);
        session.execute(bs.bind(picid, commenter, date, comment));

        session.close();
    }

    public LinkedList<Comments> getComment(UUID picid) {
        LinkedList<Comments> comments = new LinkedList<>();
        Session session = cluster.connect("instagrimXinyue");
        PreparedStatement ps = session.prepare("SELECT commenter,time,comment from comments where picid =?");
        BoundStatement boundStatement = new BoundStatement(ps);
        ResultSet rs = session.execute(boundStatement.bind(picid));
        if (rs.isExhausted()) {
            System.out.println("No Comments returned");
            return null;
        } else {
            for (Row row : rs) {
                String commenter = row.getString("commenter");
                String comment = row.getString("comment");
                Date time = row.getDate("time");

                System.out.println("PicModel");
                System.out.println("commenter:" + commenter);
                System.out.println("comment:" + comment);
                System.out.println("time:" + time);

                Comments c = new Comments();
                c.setCom(commenter, comment, time);

                comments.add(c);
            }
        }
        return comments;
    }

    public UUID getProfilePic(String user) {
        UUID pp = null;
        System.out.println("profpic uuid: inside");
        Session session = cluster.connect("instagrimXinyue");
        PreparedStatement ps = session.prepare("SELECT profilePic from userprofiles where username =?");
        BoundStatement bs = new BoundStatement(ps);
        ResultSet rs = session.execute(bs.bind(user));
        for (Row row : rs) {
            pp = row.getUUID("profilePic");
        }
        System.out.println("profpic uuid: exit");
        return pp;
    }

}
