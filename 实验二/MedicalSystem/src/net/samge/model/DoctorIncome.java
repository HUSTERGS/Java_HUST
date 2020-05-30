package net.samge.model;

public class DoctorIncome {
    /**
     * 部门名称
     */
    private String depName;
    /**
     * 医生编号
     */
    private String docId;
    /**
     * 医生名字
     */
    private String docName;
    /**
     * 号种类别: 专家号/普通号
     */
    private String regCat;
    /**
     * 挂号人次
     */
    private int regNum;
    /**
     * 收入合计
     */
    private int income;

    public DoctorIncome(String depName, String docId, String docName, String regCat, int regNum, int income) {
        this.depName = depName;
        this.docId = docId;
        this.docName = docName;
        this.regCat = regCat;
        this.regNum = regNum;
        this.income = income;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    /**
     * 只返回是专家号/普通号
     * @return
     */
    public String getRegCat() {
        return regCat.substring(regCat.length() - 3);
    }

    public void setRegCat(String regCat) {
        this.regCat = regCat;
    }

    public int getRegNum() {
        return regNum;
    }

    public void setRegNum(int regNum) {
        this.regNum = regNum;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }
}
