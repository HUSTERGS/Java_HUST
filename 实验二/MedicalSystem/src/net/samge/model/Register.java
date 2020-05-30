package net.samge.model;

public class Register {

    private String regId;
    private String catid;
    private String docid;
    private String pid;
    private long currentRegCount;
    private long unreg;
    private double regFee;
    private java.sql.Timestamp regDatetime;


    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }


    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }


    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }


    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }


    public long getCurrentRegCount() {
        return currentRegCount;
    }

    public void setCurrentRegCount(long currentRegCount) {
        this.currentRegCount = currentRegCount;
    }


    public long getUnreg() {
        return unreg;
    }

    public void setUnreg(long unreg) {
        this.unreg = unreg;
    }


    public double getRegFee() {
        return regFee;
    }

    public void setRegFee(double regFee) {
        this.regFee = regFee;
    }


    public java.sql.Timestamp getRegDatetime() {
        return regDatetime;
    }

    public void setRegDatetime(java.sql.Timestamp regDatetime) {
        this.regDatetime = regDatetime;
    }

}

