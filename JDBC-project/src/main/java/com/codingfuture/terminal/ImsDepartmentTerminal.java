package com.codingfuture.terminal;

import com.codingfuture.dao.ImsDepartmentDao;
import com.codingfuture.entity.ImsDepartment;
import com.codingfuture.service.Impl.ImsDepartmentServiceImpl;
import com.codingfuture.service.ImsDepartmentService;

import java.util.ArrayList;
import java.util.Scanner;

public class ImsDepartmentTerminal extends ImsBaseTerminal{

    private static final Scanner INPUT = new Scanner(System.in);
    private static ImsDepartmentService imsDepartmentService = new ImsDepartmentServiceImpl();
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
                return 0;
            default:
                System.out.println("输入有误！！");
                break;
        }
        this.crud("部门");
        return 0;
    }


    /**
     * 部门添加功能
     */
    public static void insert(){
        System.out.println("** 请输入部门名称:");
        String deptName = INPUT.nextLine();
        ImsDepartment imsDepartment = new ImsDepartment();
        imsDepartment.setDptName(deptName);
        boolean success = imsDepartmentService.insert(imsDepartment);
        System.out.println(success ? "部门添加成功" : "部门添加失败");
    };


    /**
     *部门删除功能
     * 根据输出表格的内容进行删除
     */
    private static void delete() {
        ImsDepartmentTerminal.query();
        // 获取部门列表以便进行删除操作
        ArrayList<ImsDepartment> departments = imsDepartmentService.query();
        if (departments == null || departments.isEmpty()) {
            System.out.println("没有部门信息可供删除。");
            return;
        }

        // 让用户输入要删除的部门编号（索引）
        System.out.println("** 请输入删除的部门编号（序号）：");
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

        // 根据索引获取要删除的部门
        ImsDepartment imsDepartment = departments.get(index);

        // 执行删除操作
        boolean success = imsDepartmentService.delete(imsDepartment);
        System.out.println(success ? "部门删除成功" : "部门删除失败");
    }


    /**
     * 部门查询功能
     */
    public static void query() {
        // 获取查询结果
        ArrayList<ImsDepartment> imsDepartments = imsDepartmentService.query();

        // 打印结果
        printTable(imsDepartments);
    }

    /**
     *打印表格
     * @param departments
     */
    private static void printTable(ArrayList<ImsDepartment> departments) {
        if (departments == null || departments.isEmpty()) {
            System.out.println("没有查询到部门信息。");
            return;
        }
        // 定义列名
        String[] columnNames = {"序号", "部门名称"};
        // 获取每列的最大宽度
        int[] columnWidths = new int[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            columnWidths[i] = columnNames[i].length();
        }
        // 计算最大列宽
        for (int i = 0; i < departments.size(); i++) {
            String dptName = departments.get(i).getDptName();
            if (dptName.length() > columnWidths[1]) {
                columnWidths[1] = dptName.length();
            }
        }
        // 打印表头分隔线
        printLine(columnWidths);
        // 打印表头
        printRow(columnNames, columnWidths);
        // 打印分隔线
        printLine(columnWidths);
        // 打印表格数据
        for (int i = 0; i < departments.size(); i++) {
            ImsDepartment department = departments.get(i);
            String[] rowData = {(i + 1) + "", department.getDptName()};
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

    /**
     * 部门更新功能
     */
    private static void update() {
        // 打印部门列表
        ImsDepartmentTerminal.query();

        // 获取部门列表以便进行修改操作
        ImsDepartmentServiceImpl imsDepartmentServiceImpl = new ImsDepartmentServiceImpl();
        ArrayList<ImsDepartment> departments = imsDepartmentServiceImpl.query();
        if (departments == null || departments.isEmpty()) {
            System.out.println("没有部门信息可供修改。");
            return;
        }

        // 让用户输入要修改的部门编号（索引）
        System.out.println("** 请输入修改的部门编号（序号）：");
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

        // 根据索引获取要修改的部门
        ImsDepartment imsDepartment = departments.get(index);

        // 让用户输入新的部门名称
        System.out.println("** 请输入修改的部门名称:");
        String newDeptName = INPUT.nextLine();
        imsDepartment.setDptName(newDeptName);

        // 执行更新操作
        boolean success = imsDepartmentService.updata(imsDepartment);
        System.out.println(success ? "部门修改成功" : "部门修改失败");
    }
}
