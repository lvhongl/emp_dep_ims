package com.codingfuture.service.Impl;
import net.sourceforge.pinyin4j.PinyinHelper;
import com.codingfuture.dao.ImsEmployeeDao;
import com.codingfuture.entity.ImsEmployee;
import com.codingfuture.service.ImsEmployeeService;
import java.text.SimpleDateFormat;
import java.util.*;

public class ImsEmployeeServiceImpl implements ImsEmployeeService {
    ImsEmployeeDao imsEmployeeDao = new ImsEmployeeDao();
    private static  String DEPARTMENT_NAME_PREFIX ; // 示例前缀

    public boolean insert(ImsEmployee imsEmployee) {
        imsEmployee.setDptName(imsEmployeeDao.getDptName(imsEmployee.getDptId()));
        if (imsEmployee.getEmpName() == null || imsEmployee.getEmpSex() == null || imsEmployee.getDptName() == null) {
            System.out.println("员工信息不完整！");
            return false;
        }

        boolean exists = queryEmpByName(imsEmployee);
        if (exists) {
            System.out.println("此员工已经存在！");
            return false;
        }

        // 生成员工编码
        String empCode = generateEmpCode(imsEmployee.getDptName());
        imsEmployee.setEmpCode(empCode);

        // 设置其他属性
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
        String updateTime = simpleDateFormat.format(new Date());
        imsEmployee.setEmpId(UUID.randomUUID().toString());
        imsEmployee.setIsDeleted(0);
        imsEmployee.setCreateTime(createTime);
        imsEmployee.setUpdateTime(updateTime);

        // 执行保存员工信息的操作
        return imsEmployeeDao.insert(imsEmployee);
    }

    public String generateEmpCode(String dptName) {
        // 获取部门名称的首字母
        String departmentPrefix = getDepartmentPrefix(dptName);

        // 获取当前日期的后六位
        String dateSuffix = new SimpleDateFormat("yyMMdd").format(new Date());

        // 生成序列号
        String sequence = generateSequenceNumber(departmentPrefix, dateSuffix);

        return departmentPrefix + dateSuffix + sequence;
    }

    private String getDepartmentPrefix(String dptName) {
        if (dptName == null || dptName.isEmpty()) {
            throw new IllegalArgumentException("部门名称不能为空");
        }
        // 获取部门名称的拼音首字母并转换为大写
        StringBuilder prefixBuilder = new StringBuilder();
        for (char c : dptName.toCharArray()) {
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
            if (pinyinArray != null) {
                // 取拼音的首字母并转换为大写
                prefixBuilder.append(Character.toUpperCase(pinyinArray[0].charAt(0)));
            } else {
                // 如果字符没有对应的拼音，保持原样
                prefixBuilder.append(c);
            }
        }
        // 获取部门名称的前五个字符的首字母
        String departmentPrefix = prefixBuilder.toString();
        return departmentPrefix.substring(0, Math.min(5, departmentPrefix.length()));
    }

    private String generateSequenceNumber(String departmentPrefix, String dateSuffix) {
        // 查询所有已存在的编码
        List<String> existingEmpCodes = imsEmployeeDao.queryAllEmpCodes();

        // 获取当前日期的前六位 (例如：220804)
        String datePrefix = dateSuffix;
        int sequenceNumber = 1;

        // 查找最大序列号
        for (String empCode : existingEmpCodes) {
            if (empCode.startsWith(departmentPrefix + datePrefix)) {
                // 提取序列号并更新最大序列号
                String sequencePart = empCode.substring(departmentPrefix.length() + datePrefix.length());
                int currentSequence = Integer.parseInt(sequencePart);
                if (currentSequence >= sequenceNumber) {
                    sequenceNumber = currentSequence + 1;
                }
            }
        }

        // 格式化序列号为两位数
        return String.format("%02d", sequenceNumber);
    }

    // 这个方法假设存在，用于判断员工是否已经存在
    private boolean queryEmpByName(ImsEmployee imsEmployee) {
        // 实现你的逻辑来判断员工是否存在
        return imsEmployeeDao.queryEmpByName(imsEmployee);
    }

    // 这个方法假设存在，用于生成新的员工ID

    @Override
    public boolean delete(ImsEmployee imsEmployee) {
        if (imsEmployee == null){
            return false;
        }
        boolean success = imsEmployeeDao.queryEmpByName(imsEmployee);
        if (!success){
            return false;
        }
        imsEmployee.setIsDeleted(1);
        return imsEmployeeDao.delete(imsEmployee);
    }

    @Override
    public boolean update(ImsEmployee imsEmployee) {
        if (imsEmployee == null){
            return false;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String update = simpleDateFormat.format(new Date());
        imsEmployee.setUpdateTime(update);
        return imsEmployeeDao.update(imsEmployee);
    }

    @Override
    public ArrayList<ImsEmployee> query() {
        ArrayList<ImsEmployee> imsEmployees = new ImsEmployeeDao().query();

        // 使用 Iterator 删除集合中 getIsDeleted 为 1 的对象
        Iterator<ImsEmployee> iterator = imsEmployees.iterator();
        while (iterator.hasNext()) {
            ImsEmployee imsEmployee = iterator.next();
            if (imsEmployee.getIsDeleted() == 1) {
                iterator.remove();
            }
        }
        return imsEmployees;
    }
}