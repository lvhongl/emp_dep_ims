package com.codingfuture.entity;

import com.hp.hpl.sparta.xpath.Step;

public class ImsSalary {
    private String saId;
    private String empId;
    private String saData;
    private double saBase;
    private double saPerformance;
    private double saInsurance;
    private double saActual;
    private int isDeleted;
    private String createTime;
    private String updateTime;
    private String empName;
    private String empSex;
    private String dptName;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpSex() {
        return empSex;
    }

    public void setEmpSex(String empSex) {
        this.empSex = empSex;
    }

    public String getDptName() {
        return dptName;
    }

    public void setDptName(String dptName) {
        this.dptName = dptName;
    }

    public String getSaId() {
        return saId;
    }

    public void setSaId(String saId) {
        this.saId = saId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getSaData() {
        return saData;
    }

    public void setSaData(String saData) {
        this.saData = saData;
    }

    public double getSaBase() {
        return saBase;
    }

    public void setSaBase(double saBase) {
        this.saBase = saBase;
    }

    public double getSaPerformance() {
        return saPerformance;
    }

    public void setSaPerformance(double saPerformance) {
        this.saPerformance = saPerformance;
    }

    public double getSaInsurance() {
        return saInsurance;
    }

    public void setSaInsurance(double saInsurance) {
        this.saInsurance = saInsurance;
    }

    public double getSaActual() {
        return saActual;
    }

    public void setSaActual(double saActual) {
        this.saActual = saActual;
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
        return "ImsSalary{" +
                "saId='" + saId + '\'' +
                ", empId='" + empId + '\'' +
                ", saData='" + saData + '\'' +
                ", saBase=" + saBase +
                ", saPerformance=" + saPerformance +
                ", saInsurance=" + saInsurance +
                ", saActual=" + saActual +
                ", isDeleted=" + isDeleted +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", empName='" + empName + '\'' +
                ", empSex='" + empSex + '\'' +
                ", dptName='" + dptName + '\'' +
                '}';
    }
}
