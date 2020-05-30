package net.samge.model;


public class Department {

    private String depid;
    private String name;
    private String py;

    public Department(String depid, String name, String py) {
        this.depid = depid;
        this.name = name;
        this.py = py;
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

    @Override
    public String toString() {
        return this.depid + " " + this.name + " " + this.py;
    }
}
