package itp341.gu.han.finalproject.app.model;

import java.io.Serializable;
import java.util.*;

public class Post {
    private String name;
    private String subject;
    private String time;
    private String note;
    private String contact;
    private String location;
    public Post() {
        super();
        this.name = "default name";
        this.contact = "default";
        this.location = "default";
        this.note = "default";
        this.subject = "default";
        this.time = "default";
    }
    public Post(String n, String s, String t, String nt, String c, String l) {
        super();
        this.name = n;
        this.contact = c;
        this.location = l;
        this.note = nt;
        this.subject = s;
        this.time = t;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
