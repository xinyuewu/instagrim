/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.stores;

import com.datastax.driver.core.utils.Bytes;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

/**
 *
 * @author Administrator
 */
public class Pic implements Comparable<Pic> {

    private ByteBuffer bImage;
    private int length;
    private String type;
    private java.util.UUID UUID;
    private String dc;
    private String un;
    private Date date;
    LinkedList<Comments> c;

    public void Pic() {

    }

    @Override
    public int compareTo(Pic p) {
        if (this.date.before(p.date)) {
            return 1;
        } else if (this.date.equals(p.date)) {
            return 0;
        } else {
            return -1;
        }
    }

    public LinkedList getComments() {
        return c;
    }

    public void setComments(LinkedList c) {
        this.c=c;
    }

    public void setUUID(UUID UUID) {
        this.UUID = UUID;
    }

    public String getSUUID() {
        return UUID.toString();
    }

    public void setPic(ByteBuffer bImage, int length, String type) {
        this.bImage = bImage;
        this.length = length;
        this.type = type;
    }

    public ByteBuffer getBuffer() {
        return bImage;
    }

    public int getLength() {
        return length;
    }

    public String getType() {
        return type;
    }

    public byte[] getBytes() {

        byte image[] = Bytes.getArray(bImage);
        return image;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getDc() {
        return dc;
    }

    public void setUn(String un) {
        this.un = un;
    }

    public String getUn() {
        return un;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

}
