package net.samge.model;


public class Patient {

  private String pid;
  private String name;
  private String password;
  private double balance;
  private java.sql.Timestamp lastLoginDatetime;


  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }


  public java.sql.Timestamp getLastLoginDatetime() {
    return lastLoginDatetime;
  }

  public void setLastLoginDatetime(java.sql.Timestamp lastLoginDatetime) {
    this.lastLoginDatetime = lastLoginDatetime;
  }

}
