package com.codingfuture.terminal;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ImsBaseTerminal {
    private String str;

    public void console() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("************************************");
                System.out.println("********** 员工薪资管理系统 **********");
                System.out.println("** 1. 部门信息管理 *******************");
                System.out.println("** 2. 员工信息管理 *******************");
                System.out.println("** 3. 薪资信息管理 *******************");
                System.out.println("** 4. 退出 **************************");
                System.out.println("************************************");
                System.out.print("请输入要执行的功能序号:");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        new ImsDepartmentTerminal().crud("部门");
                        break;
                    case 2:
                        new ImsEmployeeTerminal().crud("员工");
                        break;
                    case 3:
                        new ImsSalaryTerminal().crud("薪资");
                        break;
                    case 4:
                        System.out.println("退出系统。");
                        scanner.close();
                        return;
                    default:
                        System.out.println("无效的选择，请重新输入。");
                }
            } catch (InputMismatchException e) {
                System.out.println("请输入正确的数字");
                scanner.next(); // 清除错误输入
            }
        }
    }

    public int crud(String str) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("************************************");
                System.out.println("********** " + str + "管理系统 **************");
                System.out.println("** 1. " + str + "信息添加 *******************");
                System.out.println("** 2. " + str + "信息删除 *******************");
                System.out.println("** 3. " + str + "信息修改 *******************");
                System.out.println("** 4. " + str + "信息查询 *******************");
                System.out.println("** 5. 返回 **************************");
                System.out.println("************************************");
                System.out.print("请输入要执行的功能序号:");

                int choice = scanner.nextInt();
                return choice; // 返回选择值
            } catch (InputMismatchException e) {
                System.out.println("请输入正确的数字");
                scanner.next(); // 清除错误输入
            }
        }
    }

    public static void main(String[] args) {
        new ImsBaseTerminal().console();
    }
}