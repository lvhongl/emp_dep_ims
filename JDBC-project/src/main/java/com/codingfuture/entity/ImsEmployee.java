package com.codingfuture.entity;

public class ImsEmployee {
    private String empId;
    private String dptId;
    private String empName;
    private String empCode;
    private String empSex;
    private int isDeleted;
    private String createTime;
    private String updateTime;
    private String dptName;

    public String getDptName() {
        return dptName;
    }

    public void setDptName(String dptName) {
        this.dptName = dptName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getDptId() {
        return dptId;
    }

    public void setDptId(String dptId) {
        this.dptId = dptId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpSex() {
        return empSex;
    }

    public void setEmpSex(String empSex) {
        this.empSex = empSex;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ImsEmployee{" +
                "empId='" + empId + '\'' +
                ", dptId='" + dptId + '\'' +
                ", empName='" + empName + '\'' +
                ", empCode='" + empCode + '\'' +
                ", empSex='" + empSex + '\'' +
                ", isDeleted=" + isDeleted +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", dptName = '" + dptName + '\'' +
                '}';
    }
}
