package com.codingfuture.service.Impl;

import com.codingfuture.dao.ImsDepartmentDao;
import com.codingfuture.entity.ImsDepartment;
import com.codingfuture.service.ImsDepartmentService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

public class ImsDepartmentServiceImpl implements ImsDepartmentService {

    @Override
    public boolean queryByName(ImsDepartment imsDepartment) {
        if (imsDepartment.getDptName() == null) {
            return false;
        }
        ImsDepartmentDao imsDepartmentDao = new ImsDepartmentDao();
        return imsDepartmentDao.queryByName(imsDepartment);
    }
    ImsDepartmentDao imsDepartmentDao = new ImsDepartmentDao();


    @Override
    public boolean insert(ImsDepartment imsDepartment) {
        if (imsDepartment.getDptName() == null) {
            return false;
        }

        // 添加中文字符验证逻辑
        if (!isValidChineseName(imsDepartment.getDptName())) {
            System.out.println("请输入中文部门名称");
            return false;
        }

        boolean success = imsDepartmentDao.queryByName(imsDepartment);
        if (success) {
            System.err.println("输入的部门名称已经存在，请更换其他部门");
            return false;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
        String updataTime = simpleDateFormat.format(new Date());
        imsDepartment.setDptId(UUID.randomUUID().toString());
        imsDepartment.setIsDeleted(0);
        imsDepartment.setCreateTime(createTime);
        imsDepartment.setUpdateTime(updataTime);
        return imsDepartmentDao.insert(imsDepartment);
    }

    // 验证中文字符的方法
    private boolean isValidChineseName(String name) {
        // 正则表达式检查是否全为中文字符且不包含空格、字母或数字
        return name.matches("^[\u4e00-\u9fa5]+$");
    }

    @Override
    public boolean delete(ImsDepartment imsDepartment) {
        if (imsDepartment.getDptName() == null){
            return false;
        }
        boolean success = imsDepartmentDao.queryByName(imsDepartment);
        if (!success){
            return false;
        }
        boolean haveEmp = imsDepartmentDao.queryEmp(imsDepartment);
        if (haveEmp){
            System.out.println("此部门存在员工");
            return false;
        }
        imsDepartment.setIsDeleted(1);
        return imsDepartmentDao.delete(imsDepartment);
    }

    @Override
    public boolean updata(ImsDepartment imsDepartment) {
        if (imsDepartment == null){
            return false;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String update = simpleDateFormat.format(new Date());
        imsDepartment.setUpdateTime(update);
        return imsDepartmentDao.updata(imsDepartment);
    }

    @Override
    public ArrayList<ImsDepartment> query() {
        ArrayList<ImsDepartment> imsDepartments = new ImsDepartmentDao().query();

        // 使用 Iterator 删除集合中 getIsDeleted 为 1 的对象
        Iterator<ImsDepartment> iterator = imsDepartments.iterator();
        while (iterator.hasNext()) {
            ImsDepartment imsDepartment = iterator.next();
            if (imsDepartment.getIsDeleted() == 1) {
                iterator.remove();
            }
        }

        return imsDepartments;
    }
}
