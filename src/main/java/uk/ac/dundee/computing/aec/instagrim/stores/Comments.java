/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.stores;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author xinyuewu
 */
public class Comments {

    private String commenter;
    private String comment;
    private Date date;


    public void setCom(String commenter,String comment,Date time) {
        this.commenter = commenter;
        this.comment = comment;
        this.date = time;
    }

    public String getCommenter() {
        return commenter;
    }
 

    public String getComment() {
        return comment;
    }

    public String getTime() {
        DateFormat time = new SimpleDateFormat("YYYY MMM dd, HH:mm");
        return time.format(date);
    }
}
