package net.samge.model;


public class RegisterCategory {

    private String catid;
    private String name;
    private String py;
    private String depid;
    private long speciallist;
    private long maxRegNumber;
    private double regFee;

    public RegisterCategory(String catid, String name, String py, String depid, long speciallist, long maxRegNumber, double regFee) {
        this.catid = catid;
        this.name = name;
        this.py = py;
        this.depid = depid;
        this.speciallist = speciallist;
        this.maxRegNumber = maxRegNumber;
        this.regFee = regFee;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
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


    public String getDepid() {
        return depid;
    }

    public void setDepid(String depid) {
        this.depid = depid;
    }


    public long getSpeciallist() {
        return speciallist;
    }

    public void setSpeciallist(long speciallist) {
        this.speciallist = speciallist;
    }


    public long getMaxRegNumber() {
        return maxRegNumber;
    }

    public void setMaxRegNumber(long maxRegNumber) {
        this.maxRegNumber = maxRegNumber;
    }


    public double getRegFee() {
        return regFee;
    }

    public void setRegFee(double regFee) {
        this.regFee = regFee;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
