package com.codingfuture.dao;

import com.codingfuture.entity.ImsEmployee;
import com.codingfuture.entity.ImsSalary;
import com.codingfuture.util.JDBCUtil;
import com.mysql.cj.jdbc.JdbcConnection;
import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ImsSalaryDao {
    public static boolean queryByName(ImsSalary imsSalary){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT emp_name from ims_employee where emp_id in (select emp_id from ims_salary where emp_id = ? and sa_date = ?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1,imsSalary.getEmpId());
            ps.setString(2,imsSalary.getSaData());
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtil.close(rs,ps,connection);
        }
    }
    public static String getDptName(ImsSalary imsSalary) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String dptName = null;

        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT dpt_name FROM ims_department WHERE dpt_id in (select dpt_id from ims_employee where emp_id = ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, imsSalary.getEmpId());
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

    public static ImsSalary getEmp(ImsSalary imsSalary){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select emp_name,emp_sex from ims_employee where emp_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,imsSalary.getEmpId());
            rs = ps.executeQuery();
            if (rs.next()){
                imsSalary.setEmpName(rs.getString("emp_name"));
                imsSalary.setEmpSex(rs.getString("emp_sex"));
            }
            return imsSalary;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtil.close(rs,ps,connection);
        }
    }

    public static boolean insert(ImsSalary imsSalary){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "INSERT INTO ims_salary values (?,?,?,?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1,imsSalary.getSaId());
            ps.setString(2,imsSalary.getEmpId());
            ps.setString(3,imsSalary.getSaData());
            ps.setDouble(4,imsSalary.getSaBase());
            ps.setDouble(5,imsSalary.getSaPerformance());
            ps.setDouble(6,imsSalary.getSaInsurance());
            ps.setDouble(7,imsSalary.getSaActual());
            ps.setInt(8,imsSalary.getIsDeleted());
            ps.setString(9,imsSalary.getCreateTime());
            ps.setString(10,imsSalary.getUpdateTime());
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtil.close(ps,connection);
        }
    }
    public static ArrayList<ImsSalary> query(){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs =null;
        ArrayList<ImsSalary> imsSalaries = new ArrayList<>();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT sa_id,emp_id,sa_date,sa_base,sa_performance,sa_insurance,sa_actual,is_deleted,create_time,update_time from ims_salary";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                ImsSalary imsSalary = new ImsSalary();
                imsSalary.setSaId(rs.getString("sa_id"));
                imsSalary.setEmpId(rs.getString("emp_id"));
                imsSalary.setSaData(rs.getString("sa_date"));
                imsSalary.setSaBase(rs.getDouble("sa_base"));
                imsSalary.setSaPerformance(rs.getDouble("sa_performance"));
                imsSalary.setSaInsurance(rs.getDouble("sa_insurance"));
                imsSalary.setSaActual(rs.getDouble("sa_actual"));
                imsSalary.setIsDeleted(rs.getInt("is_deleted"));
                imsSalary.setCreateTime(rs.getString("create_time"));
                imsSalary.setUpdateTime(rs.getString("update_time"));
                imsSalary.setDptName(getDptName(imsSalary));
                getEmp(imsSalary);
                imsSalaries.add(imsSalary);
            }
                return imsSalaries;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtil.close(rs,ps,connection);
        }
    }
    public static boolean update(ImsSalary imsSalary){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "UPDATE ims_salary set sa_base = ?,sa_performance = ?,sa_insurance = ?,sa_actual = ?,update_time = ?";
            ps = connection.prepareStatement(sql);
            ps.setDouble(1,imsSalary.getSaBase());
            ps.setDouble(2,imsSalary.getSaPerformance());
            ps.setDouble(3,imsSalary.getSaInsurance());
            ps.setDouble(4,imsSalary.getSaActual());
            ps.setString(5,imsSalary.getUpdateTime());
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtil.close(ps,connection);
        }
    }
    public static boolean delete(ImsSalary imsSalary){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "update ims_salary set is_deleted = ?,sa_date = ? where sa_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,imsSalary.getIsDeleted());
            ps.setString(2,imsSalary.getSaData());
            ps.setString(3,imsSalary.getSaId());
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtil.close(ps,connection);
        }
    }
    public static boolean queryEmp(ImsSalary imsSalary){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select emp_id from ims_employee where emp_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,imsSalary.getEmpId());
            rs =  ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtil.close(rs,ps,connection);
        }
    }
}