package com.codingfuture.terminal;

import com.codingfuture.entity.ImsDepartment;
import com.codingfuture.entity.ImsEmployee;
import com.codingfuture.entity.ImsSalary;
import com.codingfuture.service.Impl.ImsDepartmentServiceImpl;
import com.codingfuture.service.Impl.ImsEmployeeServiceImpl;
import com.codingfuture.service.Impl.ImsSalaryServiceImpl;
import com.codingfuture.service.ImsDepartmentService;
import com.codingfuture.service.ImsEmployeeService;
import com.codingfuture.service.ImsSalaryService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ImsSalaryTerminal extends ImsBaseTerminal {
    private static final Scanner INPUT = new Scanner(System.in);
    private static ImsEmployeeService imsEmployeeService = new ImsEmployeeServiceImpl();
    public static ImsSalaryService imsSalaryService = new ImsSalaryServiceImpl();
    public static ImsDepartmentService imsDepartmentService = new ImsDepartmentServiceImpl();

    @Override
    public int crud(String str) {
        int option;
        do {
            option = super.crud(str);
            switch (option) {
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
        } while (option != 5);
        return 0;
    }

    public static void query() {
        System.out.println("****请输入查询方式：");
        System.out.println("****1,根据员工姓名查询");
        System.out.println("****2,根据日期查询");
        System.out.println("****3,退出查询");
        switch (INPUT.nextInt()) {
            case 1:
                queryByEmpName();
                break;
            case 2:
                queryByDate();
                break;
            case 3:
                return;
            default:
                System.out.println("输入有误");
                break;
        }
    }

    public static void insert() {
        ImsEmployeeTerminal.query();
        ArrayList<ImsEmployee> imsEmployees = imsEmployeeService.query();
        if (imsEmployees == null || imsEmployees.isEmpty()) {
            System.out.println("没有员工信息可供添加薪资。");
            return;
        }
        System.out.println("** 请输入添加薪资的员工编号（序号）：");
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
        ImsSalary imsSalary = new ImsSalary();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM");
        String saData = simpleDateFormat1.format(new Date());
        System.out.println("**当前操作员工：[" + imsEmployee.getEmpName() + "]，[" + saData + "] 的薪资信息");
        imsSalary.setSaData(saData);
        imsSalary.setEmpId(imsEmployee.getEmpId());
        imsSalary.setEmpName(imsEmployee.getEmpName());
        imsSalary.setEmpSex(imsEmployee.getEmpSex());

        try {
            System.out.println("请输入基础薪资");
            imsSalary.setSaBase(INPUT.nextDouble());
            System.out.println("请输入绩效薪资");
            imsSalary.setSaPerformance(INPUT.nextDouble());
            System.out.println("请输入扣除保险");
            imsSalary.setSaInsurance(INPUT.nextDouble());
        } catch (Exception e) {
            System.out.println("输入不合法");
            INPUT.nextLine(); // 清空输入缓冲区，避免无限循环
            return;
        }

        boolean success = imsSalaryService.insert(imsSalary);
        System.out.println(success ? "员工薪资添加成功" : "员工薪资添加失败");
    }

    private static void queryByEmpName() {
        ImsEmployeeTerminal.query();
        ArrayList<ImsEmployee> imsEmployees = imsEmployeeService.query();
        if (imsEmployees == null || imsEmployees.isEmpty()) {
            System.out.println("没有员工信息。");
            return;
        }
        System.out.println("** 请输入查询薪资的员工编号（序号）：");
        int index;
        try {
            index = Integer.parseInt(INPUT.next()) - 1; // 转换为 0 基索引
            if (index < 0 || index >= imsEmployees.size()) {
                System.out.println("无效的编号。");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("请输入有效的数字。");
            return;
        }
        ImsEmployee imsEmployee = imsEmployees.get(index);
        ArrayList<ImsSalary> imsSalaries = imsSalaryService.query();
        ArrayList<ImsSalary> filteredResults = new ArrayList<>();
        for (ImsSalary salary : imsSalaries) {
            if (salary.getEmpId().contains(imsEmployee.getEmpId())) {
                filteredResults.add(salary);
            }
        }
        printTable(filteredResults);
    }

    private static void queryByDate() {
        System.out.println("**请输入日期 示例（2024-08）");
        String saDate = INPUT.next();
        ArrayList<ImsSalary> imsSalaries = imsSalaryService.query();
        System.out.println(imsSalaries);
        ArrayList<ImsSalary> filteredResults = new ArrayList<>();
        for (ImsSalary salary : imsSalaries) {
            if (salary.getSaData().equals(saDate)) {
                filteredResults.add(salary);
            }
        }
        printTable(filteredResults);
    }

    private static void printTable(ArrayList<ImsSalary> salaries) {
        if (salaries == null || salaries.isEmpty()) {
            System.err.println("没有查询到薪资信息。");
            return;
        }
        String[] columnNames = {"序号", "保险扣除", "实发薪资", "员工姓名", "绩效月份", "绩效工资", "基础工资"};
        int[] columnWidths = calculateColumnWidths(columnNames, salaries);

        printLine(columnWidths);
        printRow(columnNames, columnWidths);
        printLine(columnWidths);
        for (ImsSalary salary : salaries) {
            String[] rowData = {
                    String.valueOf(salaries.indexOf(salary) + 1),
                    String.valueOf(salary.getSaInsurance()),
                    String.valueOf(salary.getSaActual()),
                    salary.getEmpName(),
                    salary.getSaData(),
                    String.valueOf(salary.getSaPerformance()),
                    String.valueOf(salary.getSaBase())
            };
            printRow(rowData, columnWidths);
        }
        printLine(columnWidths);
    }

    private static int[] calculateColumnWidths(String[] columnNames, ArrayList<ImsSalary> salaries) {
        int[] columnWidths = new int[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            columnWidths[i] = columnNames[i].length();
        }
        for (ImsSalary salary : salaries) {
            String[] rowData = {
                    String.valueOf(salary.getSaInsurance()),
                    String.valueOf(salary.getSaActual()),
                    salary.getEmpName(),
                    salary.getSaData(),
                    String.valueOf(salary.getSaPerformance()),
                    String.valueOf(salary.getSaBase())
            };
            for (int i = 0; i < rowData.length; i++) {
                if (rowData[i].length() > columnWidths[i]) {
                    columnWidths[i] = rowData[i].length();
                }
            }
        }
        return columnWidths;
    }

    private static void printLine(int[] columnWidths) {
        for (int width : columnWidths) {
            System.out.print("+");
            for (int i = 0; i < width + 2; i++) {
                System.out.print("-");
            }
        }
        System.out.println("+");
    }

    private static void printRow(String[] row, int[] columnWidths) {
        for (int i = 0; i < row.length; i++) {
            System.out.printf("| %-" + columnWidths[i] + "s ", row[i]);
        }
        System.out.println("|");
    }

    public static void delete() {
        ArrayList<ImsSalary> salaries = imsSalaryService.query();
        printTable(salaries);
        if (salaries == null || salaries.isEmpty()) {
            System.err.println("没有可供删除的薪资信息。");
            return;
        }
        System.out.println("请输入要删除的薪资记录编号（序号）：");
        int index;
        try {
            index = Integer.parseInt(INPUT.next()) - 1; // 转换为 0 基索引
            if (index < 0 || index >= salaries.size()) {
                System.out.println("无效的编号。");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("请输入有效的数字。");
            return;
        }
        ImsSalary salary = salaries.get(index);
        boolean success = imsSalaryService.delete(salary);
        System.out.println(success ? "删除成功" : "删除失败");
    }
    public static void update() {
        ArrayList<ImsSalary> salaries = imsSalaryService.query();
        if (salaries == null || salaries.isEmpty()) {
            System.err.println("没有可供修改的薪资信息。");
            return;
        }

        // 获取当前日期并格式化为yyyy-MM
        LocalDate currentDate = LocalDate.now();
        String currentMonth = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // 筛选出saData为本月的数据
        ArrayList<ImsSalary> filteredSalaries = new ArrayList<>();
        for (ImsSalary salary : salaries) {
            if (salary.getSaData().startsWith(currentMonth)) {
                filteredSalaries.add(salary);
            }
        }

        if (filteredSalaries.isEmpty()) {
            System.err.println("没有本月的薪资信息。");
            return;
        }

        printTable(filteredSalaries);
        System.out.println("请输入要修改的薪资记录编号（序号）：");
        int index;
        try {
            index = Integer.parseInt(INPUT.next()) - 1; // 转换为 0 基索引
            if (index < 0 || index >= filteredSalaries.size()) {
                System.out.println("无效的编号。");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("请输入有效的数字。");
            return;
        }

        ImsSalary salary = filteredSalaries.get(index);
        System.out.println("当前员工薪资信息：" + salary);
        try {
            System.out.println("请输入新的基础薪资");
            salary.setSaBase(INPUT.nextDouble());
            System.out.println("请输入新的绩效薪资");
            salary.setSaPerformance(INPUT.nextDouble());
            System.out.println("请输入新的保险扣除");
            salary.setSaInsurance(INPUT.nextDouble());
        } catch (Exception e) {
            System.out.println("输入不合法");
            INPUT.nextLine(); // 清空输入缓冲区，避免无限循环
            return;
        }

        boolean success = imsSalaryService.update(salary);
        System.out.println(success ? "更新成功" : "更新失败");
    }

}