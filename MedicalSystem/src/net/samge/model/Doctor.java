package net.samge.model;


public class Doctor {

  private String docid;
  private String depid;
  private String name;
  private String py;
  private String password;
  private long speciallist;
  private java.sql.Timestamp lastLoginDatetime;


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


  public java.sql.Timestamp getLastLoginDatetime() {
    return lastLoginDatetime;
  }

  public void setLastLoginDatetime(java.sql.Timestamp lastLoginDatetime) {
    this.lastLoginDatetime = lastLoginDatetime;
  }

}
