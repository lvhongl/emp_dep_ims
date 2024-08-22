package com.codingfuture.dao;

import com.codingfuture.entity.ImsDepartment;
import com.codingfuture.entity.ImsEmployee;
import com.codingfuture.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImsEmployeeDao {
    public List<String> queryAllEmpCodes() {
        List<String> empCodes = new ArrayList<>();
        String sql = "SELECT emp_code FROM ims_employee";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String empCode = rs.getString("emp_code");
                empCodes.add(empCode);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 记录异常日志或进行其他异常处理
            throw new RuntimeException("查询员工编码时出错", e);
        }
        return empCodes;
    }
    public static boolean queryEmpByName(ImsEmployee imsEmployee){

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select emp_name from ims_employee where emp_name = ? ";
            ps = connection.prepareStatement(sql);
            ps.setString(1,imsEmployee.getEmpName());
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtil.close(rs,ps,connection);
        }
    }
    public static int getMaxEmployeeId() {
        int maxId = 0;
        String sql = "SELECT MAX(CAST(emp_id AS UNSIGNED)) FROM ims_employee";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 处理异常
        }
        return maxId;
    }
    public static boolean insert(ImsEmployee imsEmployee){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "insert into ims_employee values (?,?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1,imsEmployee.getEmpId());
            ps.setString(2,imsEmployee.getDptId());
            ps.setString(3,imsEmployee.getEmpName());
            ps.setString(4,imsEmployee.getEmpCode());
            ps.setString(5,imsEmployee.getEmpSex());
            ps.setInt(6,imsEmployee.getIsDeleted());
            ps.setString(7,imsEmployee.getCreateTime());
            ps.setString(8,imsEmployee.getUpdateTime());
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtil.close(ps,connection);
        }
    }
    // 查询所有员工的方法
    public ArrayList<ImsEmployee> query() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<ImsEmployee> imsEmployees = new ArrayList<>();

        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT emp_id, dpt_id, emp_name, emp_code, emp_sex, create_time, update_time, is_deleted FROM ims_employee";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ImsEmployee imsEmployee = new ImsEmployee();
                imsEmployee.setEmpId(rs.getString("emp_id")); // 确保列名与数据库一致
                imsEmployee.setDptId(rs.getString("dpt_id"));
                imsEmployee.setEmpName(rs.getString("emp_name"));
                imsEmployee.setEmpCode(rs.getString("emp_code"));
                imsEmployee.setEmpSex(rs.getString("emp_sex"));
                imsEmployee.setCreateTime(rs.getString("create_time"));
                imsEmployee.setUpdateTime(rs.getString("update_time"));
                imsEmployee.setIsDeleted(rs.getInt("is_deleted"));

                // 获取部门名称并设置
                String dptName = getDptName(imsEmployee.getDptId());
                imsEmployee.setDptName(dptName); // 确保 ImEmployee 类中有 setDptName 方法

                imsEmployees.add(imsEmployee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, ps, connection);
        }

        return imsEmployees;
    }

    // 根据部门 ID 获取部门名称的方法
    public static String getDptName(String dptID) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String dptName = null;

        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT dpt_name FROM ims_department WHERE dpt_id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, dptID);
            rs = ps.executeQuery();

            if (rs.next()) {
                dptName = rs.getString("dpt_name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs, ps, con);
        }

        return dptName;
    }

    public static boolean delete(ImsEmployee imsEmployee){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "Update ims_employee set is_deleted = ? where emp_name = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,imsEmployee.getIsDeleted());
            ps.setString(2,imsEmployee.getEmpName());
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtil.close(ps,connection);
        }
    }
    public static boolean update(ImsEmployee imsEmployee){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "UPDATE ims_employee set emp_name = ?,emp_sex = ?,update_time = ? where emp_code = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1,imsEmployee.getEmpName());
            ps.setString(2,imsEmployee.getEmpSex());
            ps.setString(3,imsEmployee.getUpdateTime());
            ps.setString(4,imsEmployee.getDptId());
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtil.close(ps,con);
        }
    }
}
