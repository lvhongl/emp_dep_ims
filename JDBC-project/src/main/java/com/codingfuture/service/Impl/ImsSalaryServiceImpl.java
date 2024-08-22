package com.codingfuture.service.Impl;

import com.codingfuture.dao.ImsEmployeeDao;
import com.codingfuture.dao.ImsSalaryDao;
import com.codingfuture.entity.ImsEmployee;
import com.codingfuture.entity.ImsSalary;
import com.codingfuture.service.ImsSalaryService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

public class ImsSalaryServiceImpl implements ImsSalaryService {
    ImsSalaryDao imsSalaryDao = new ImsSalaryDao();
    @Override
    public boolean insert(ImsSalary imsSalary) {
        if (imsSalary == null) {
            return false;
        }
        if(imsSalary.getSaBase()<0 || imsSalary.getSaActual()<0){
            System.out.println("请输入正确的薪资");
            return false;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
        String updateTime = simpleDateFormat.format(new Date());

        boolean success = imsSalaryDao.queryByName(imsSalary);
        if (success){
            System.out.println("本月薪资已添加");
            return false;
        }
        imsSalary.setSaId(UUID.randomUUID().toString());
        imsSalary.setSaActual(imsSalary.getSaBase()+imsSalary.getSaPerformance()-imsSalary.getSaPerformance());
        imsSalary.setIsDeleted(0);
        imsSalary.setCreateTime(createTime);
        imsSalary.setUpdateTime(updateTime);
        imsSalary.setDptName(ImsSalaryDao.getDptName(imsSalary));
        return imsSalaryDao.insert(imsSalary);
    }
    @Override
    public ArrayList<ImsSalary> query() {
        ArrayList<ImsSalary> imsSalaries = new ImsSalaryDao().query();

        // 使用 Iterator 删除集合中 getIsDeleted 为 1 的对象
        Iterator<ImsSalary> iterator = imsSalaries.iterator();
        while (iterator.hasNext()) {
            ImsSalary imsSalary = iterator.next();
            if (imsSalary.getIsDeleted() == 1) {
                iterator.remove();
            }
        }
        return imsSalaries;
    }

    @Override
    public boolean delete(ImsSalary imsSalary) {
        if (imsSalary == null){
            return false;
        }
        boolean haveEmp = imsSalaryDao.queryEmp(imsSalary);
        if (haveEmp){
            System.out.println("此员工存在，无法删除薪资");
            return false;
        }
        imsSalary.setIsDeleted(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String saDate = simpleDateFormat.format(new Date());
        imsSalary.setSaData(saDate);
        return imsSalaryDao.delete(imsSalary);
    }

    @Override
    public boolean update(ImsSalary imsSalary) {
        if (imsSalary == null) {
            return false;
        }
        if(imsSalary.getSaBase()<0 || imsSalary.getSaActual()<0){
            System.out.println("请输入正确的薪资");
            return false;
        }
        imsSalary.setSaActual(imsSalary.getSaBase()+imsSalary.getSaPerformance()-imsSalary.getSaPerformance());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String updateTime = simpleDateFormat.format(new Date());
        imsSalary.setUpdateTime(updateTime);
        return imsSalaryDao.update(imsSalary);
    }

}
