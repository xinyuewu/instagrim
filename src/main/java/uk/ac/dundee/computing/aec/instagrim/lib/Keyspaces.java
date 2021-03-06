package uk.ac.dundee.computing.aec.instagrim.lib;

import com.datastax.driver.core.*;

public final class Keyspaces {

    public Keyspaces() {

    }

    public static void SetUpKeySpaces(Cluster c) {
        try {
            String createkeyspace = "create keyspace if not exists instagrimXinyue  WITH replication = {'class':'SimpleStrategy', 'replication_factor':1}";
            String CreatePicTable = "CREATE TABLE if not exists instagrimXinyue.Pics ("
                    + " user varchar,"
                    + " picid uuid, "
                    + " interaction_time timestamp,"
                    + " title varchar,"
                    + " image blob,"
                    + " thumb blob,"
                    + " processed blob,"
                    + " imagelength int,"
                    + " thumblength int,"
                    + " processedlength int,"
                    + " type  varchar,"
                    + " name  varchar,"
                    + " description  text,"
                    + " PRIMARY KEY (picid,interaction_time)"
                    + ")WITH CLUSTERING ORDER BY (interaction_time desc);";
            
            String Createuserpiclist = "CREATE TABLE if not exists instagrimXinyue.userpiclist ("
                    + "picid uuid,"
                    + "user varchar,"
                    + "pic_added timestamp,"
                    + "PRIMARY KEY (user,pic_added)"
                    + ") WITH CLUSTERING ORDER BY (pic_added desc);";
            
            String picidInPiclist = "Create INDEX picid ON instagrimXinyue.userpiclist (picid)";

            String CreatePicComment = "CREATE TABLE if not exists instagrimXinyue.comments ("
                    + "picid uuid,"
                    + "commenter varchar,"
                    + "time timestamp,"
                    + "comment text,"
                    + "PRIMARY KEY (picid,time)"
                    + ") WITH CLUSTERING ORDER BY (time desc);";            
            
            String CreateUserProfile = "CREATE TABLE if not exists instagrimXinyue.userprofiles ("
                    + " fname text,"
                    + " lname text,"
                    + " username text PRIMARY KEY,"
                    + " password text,"
                    + " email text,"
                    + " profilePic uuid,"
                    + "  );";

            Session session = c.connect();
            try {
                PreparedStatement statement = session.prepare(createkeyspace);
                BoundStatement boundStatement = new BoundStatement(statement);
                ResultSet rs = session.execute(boundStatement);
                System.out.println("created instagrimXinyue ");
            } catch (Exception et) {
                System.out.println("Can't create instagrimXinyue " + et);
            }

            System.out.println("" + CreatePicTable);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreatePicTable);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create tweet table " + et);
            }
            
            System.out.println("" + picidInPiclist);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(picidInPiclist);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create index table " + et);
            }
            
            System.out.println("" + Createuserpiclist);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(Createuserpiclist);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create user pic list table " + et);
            }
            
            
            System.out.println("" + CreatePicComment);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreatePicComment);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create comments table " + et);
            }

            System.out.println("" + CreateUserProfile);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateUserProfile);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create user Profile " + et);
            }
            session.close();

        } catch (Exception et) {
            System.out.println("Other keyspace or coulm definition error" + et);
        }

    }
}
