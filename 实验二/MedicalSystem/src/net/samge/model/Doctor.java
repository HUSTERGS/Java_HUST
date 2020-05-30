package net.samge.model;


import javax.print.Doc;
import java.sql.Timestamp;

public class Doctor {

    private String docid;
    private String depid;
    private String name;
    private String py;
    private String password;
    private long speciallist;
    private java.sql.Timestamp lastLoginDatetime;


    public Doctor(String docid, String depid, String name, String py, String password, long speciallist, Timestamp lastLoginDatetime) {
        this.docid = docid;
        this.depid = depid;
        this.name = name;
        this.py = py;
        this.password = password;
        this.speciallist = speciallist;
        this.lastLoginDatetime = lastLoginDatetime;
    }
    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }


    public String getDepid() {
        return depid;
    }

    public void setDepid(String depid) {
        this.depid = depid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public long getSpeciallist() {
        return speciallist;
    }

    public void setSpeciallist(long speciallist) {
        this.speciallist = speciallist;
    }


    public Timestamp getLastLoginDatetime() {
        return lastLoginDatetime;
    }

    public void setLastLoginDatetime(Timestamp lastLoginDatetime) {
        this.lastLoginDatetime = lastLoginDatetime;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Doctor) {
            return this.docid.equals(((Doctor) obj).getDocid());
        } else {
            return false;
        }
    }
}
