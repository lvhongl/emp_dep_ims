package com.codingfuture.terminal;

import com.codingfuture.dao.ImsEmployeeDao;
import com.codingfuture.entity.ImsDepartment;
import com.codingfuture.entity.ImsEmployee;
import com.codingfuture.service.Impl.ImsDepartmentServiceImpl;
import com.codingfuture.service.Impl.ImsEmployeeServiceImpl;
import com.codingfuture.service.Impl.ImsSalaryServiceImpl;
import com.codingfuture.service.ImsDepartmentService;
import com.codingfuture.service.ImsEmployeeService;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;



public class ImsEmployeeTerminal extends ImsBaseTerminal {
    private static final Scanner INPUT = new Scanner(System.in);
    private static ImsEmployeeService imsEmployeeService =  new ImsEmployeeServiceImpl();
    public static ImsDepartmentService imsDepartmentService = new ImsDepartmentServiceImpl();

    public static void insert() {
        ImsDepartmentTerminal.query();
        ArrayList<ImsDepartment> departments = imsDepartmentService.query();
        if (departments == null || departments.isEmpty()) {
            System.out.println("没有部门信息可供添加员工。");
            return;
        }

        System.out.println("** 请输入添加员工的部门编号（序号）：");
        int index;
        try {
            index = Integer.parseInt(INPUT.nextLine()) - 1; // 转换为 0 基索引
            if (index < 0 || index >= departments.size()) {
                System.out.println("无效的编号。");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("请输入有效的数字。");
            return;
        }

        ImsDepartment imsDepartment = departments.get(index);
        ImsEmployee imsEmployee = new ImsEmployee();
        imsEmployee.setDptId(imsDepartment.getDptId());

        try {
            System.out.println("请输入员工姓名");
            String empName = INPUT.nextLine();
            if (!Pattern.matches("[\\u4e00-\\u9fa5]+", empName)) { // 检查是否是中文字符
                throw new IllegalArgumentException("姓名必须是中文字符");
            }
            imsEmployee.setEmpName(empName);

            System.out.println("请输入员工性别");
            System.out.println("*****1,男****2,女*****");
            int empSex = INPUT.nextInt();
            INPUT.nextLine(); // 清空输入缓冲区
            if (empSex == 1) {
                imsEmployee.setEmpSex("男");
            } else if (empSex == 2) {
                imsEmployee.setEmpSex("女");
            } else {
                throw new IllegalArgumentException("性别必须是1或2");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("无效输入：" + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("输入不合法");
            return;
        }

        boolean success = imsEmployeeService.insert(imsEmployee);
        System.out.println(success ? "员工添加成功" : "员工添加失败");
    }

    public static void query() {
        // 获取查询结果
        ArrayList<ImsEmployee> imsEmployees = imsEmployeeService.query();

        // 打印结果
        printTable(imsEmployees);
    }

    /**
     * 打印表格
     * @param employees
     */
    private static void printTable(ArrayList<ImsEmployee> employees) {
        if (employees == null || employees.isEmpty()) {
            System.out.println("没有查询到员工信息。");
            return;
        }
        // 定义列名
        String[] columnNames = {"序号", "员工性别", "员工工号", "部门名称", "姓名"};
        // 获取每列的最大宽度
        int[] columnWidths = new int[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            columnWidths[i] = columnNames[i].length();
        }
        // 计算最大列宽
        for (ImsEmployee employee : employees) {
            String[] data = {
                    employee.getEmpSex(),
                    employee.getEmpCode(),
                    employee.getDptName(),
                    employee.getEmpName()
            };
            for (int i = 0; i < data.length; i++) {
                if (data[i].length() > columnWidths[i]) {
                    columnWidths[i] = data[i].length();
                }
            }
        }
        // 打印表头分隔线
        printLine(columnWidths);
        // 打印表头
        printRow(columnNames, columnWidths);
        // 打印分隔线
        printLine(columnWidths);
        // 打印表格数据
        for (int i = 0; i < employees.size(); i++) {
            ImsEmployee employee = employees.get(i);
            String[] rowData = {
                    String.valueOf(i + 1),
                    employee.getEmpSex(),
                    employee.getEmpCode(),
                    employee.getDptName(),
                    employee.getEmpName()
            };
            printRow(rowData, columnWidths);
        }
        // 打印分隔线
        printLine(columnWidths);
    }

    /**
     * 打印行分割线
     * @param columnWidths
     */
    private static void printLine(int[] columnWidths) {
        for (int width : columnWidths) {
            System.out.print("+");
            for (int i = 0; i < width + 2; i++) {
                System.out.print("-");
            }
        }
        System.out.println("+");
    }

    /**
     * 打印列分割线
     * @param row
     * @param columnWidths
     */
    private static void printRow(String[] row, int[] columnWidths) {
        for (int i = 0; i < row.length; i++) {
            System.out.printf("| %-"+ columnWidths[i] + "s ", row[i]);
        }
        System.out.println("|");
    }

    private static void update() {
        ImsEmployeeTerminal.query();

        // 获取部门列表以便进行修改操作
        ArrayList<ImsEmployee> imsEmployees = imsEmployeeService.query();
        if (imsEmployees == null || imsEmployees.isEmpty()) {
            System.out.println("没有员工信息可供修改。");
            return;
        }

        // 让用户输入要修改的部门编号（索引）
        System.out.println("** 请输入修改的员工编号（序号）：");
        int index;
        try {
            index = Integer.parseInt(INPUT.nextLine()) - 1; // 转换为 0 基索引
            if (index < 0 || index >= imsEmployees.size()) {
                System.out.println("无效的编号。");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("请输入有效的数字。");
            return;
        }

        ImsEmployee imsEmployee = imsEmployees.get(index);
        System.out.println("*****已选择：[" + imsEmployee.getDptName() + "] 部门员工：[" + imsEmployee.getEmpName() + "]*******");

        try {
            System.out.println("*****员工原名字为：[" + imsEmployee.getEmpName() + "] 请输入新的名字：*******");
            String empName = INPUT.nextLine();
            if (!Pattern.matches("[\\u4e00-\\u9fa5]+", empName)) { // 检查是否是中文字符
                throw new IllegalArgumentException("姓名必须是中文字符");
            }
            imsEmployee.setEmpName(empName);

            System.out.println("*****员工原性别为：[" + imsEmployee.getEmpSex() + "] 请输入新的性别：********");
            System.out.println("*****1,男****2，女*****");
            int empSex = INPUT.nextInt();
            INPUT.nextLine(); // 清空输入缓冲区
            if (empSex == 1) {
                imsEmployee.setEmpSex("男");
            } else if (empSex == 2) {
                imsEmployee.setEmpSex("女");
            } else {
                throw new IllegalArgumentException("性别必须是1或2");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("无效输入：" + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("输入不合法");
            return;
        }

        // 执行更新操作
        boolean success = imsEmployeeService.update(imsEmployee);
        System.out.println(success ? "部门修改成功" : "部门修改失败");
    }

    private static void delete() {
        ImsEmployeeTerminal.query();
        // 获取部门列表以便进行删除操作
        ArrayList<ImsEmployee> imsEmployees = imsEmployeeService.query();
        if (imsEmployees == null || imsEmployees.isEmpty()) {
            System.out.println("没有员工信息可供删除。");
            return;
        }

        // 让用户输入要删除的部门编号（索引）
        System.out.println("** 请输入删除的员工编号（序号）：");
        int index;
        try {
            index = Integer.parseInt(INPUT.nextLine()) - 1; // 转换为 0 基索引
            if (index < 0 || index >= imsEmployees.size()) {
                System.out.println("无效的编号。");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("请输入有效的数字。");
            return;
        }

        // 根据索引获取要删除的部门
        ImsEmployee imsEmployee = imsEmployees.get(index);

        // 执行删除操作
        boolean success = imsEmployeeService.delete(imsEmployee);
        System.out.println(success ? "部门删除成功" : "部门删除失败");
    }

    @Override
    public int crud(String str) {
        switch (super.crud(str)) {
            case 1:
                insert();
                break;
            case 2:
                delete();
                break;
            case 3:
                update();
                break;
            case 4:
                query();
                break;
            case 5:
                super.console();
            default:
                System.out.println("输入错误，请重新输入！");
                break;
        }
        this.crud("员工");
        return 0;
    }
}
