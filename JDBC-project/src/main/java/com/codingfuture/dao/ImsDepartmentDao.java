package com.codingfuture.dao;

import com.codingfuture.entity.ImsDepartment;
import com.codingfuture.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;

public class ImsDepartmentDao {
    public static boolean queryByName(ImsDepartment imsDepartment){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select dpt_name from ims_department where dpt_name = ? ";
            ps = connection.prepareStatement(sql);
            ps.setString(1,imsDepartment.getDptName());
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtil.close(rs,ps,connection);
        }
    }
    public static boolean queryEmp(ImsDepartment imsDepartment){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select dpt_id from ims_employee where dpt_id = ? and is_deleted = 0";
            ps = connection.prepareStatement(sql);
            ps.setString(1,imsDepartment.getDptId());
            rs =  ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtil.close(rs,ps,connection);
        }
    }
    public static boolean insert(ImsDepartment imsDepartment){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
             connection = JDBCUtil.getConnection();
            String sql = "insert into ims_department values (?,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1,imsDepartment.getDptId());
            ps.setString(2,imsDepartment.getDptName());
            ps.setInt(3,imsDepartment.getIsDeleted());
            ps.setString(4,imsDepartment.getCreateTime());
            ps.setString(5,imsDepartment.getUpdateTime());
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
           JDBCUtil.close(ps,connection);
        }
    }
    public static boolean delete(ImsDepartment imsDepartment){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "Update ims_department set is_deleted = ? where dpt_name = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,imsDepartment.getIsDeleted());
            ps.setString(2,imsDepartment.getDptName());
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtil.close(ps,connection);
        }
    }
    public static ArrayList<ImsDepartment> query() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<ImsDepartment> imsDepartments = new ArrayList<>();

        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT dpt_id,dpt_name,is_deleted,create_time,update_time FROM ims_department";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                // 从 ResultSet 中获取数据
                String dptName = rs.getString("dpt_name");
                String dptId = rs.getString("dpt_id");
                Integer isDeleted = rs.getInt("is_deleted");
                String createTime = rs.getString("create_time");
                String updateTime = rs.getString("update_time");

                // 创建 ImsDepartment 对象并设置属性
                ImsDepartment imsDepartment = new ImsDepartment();
                imsDepartment.setDptName(dptName);
                imsDepartment.setDptId(dptId);
                imsDepartment.setIsDeleted(isDeleted);
                imsDepartment.setCreateTime(createTime);
                imsDepartment.setUpdateTime(updateTime);

                // 将对象添加到 ArrayList 中
                imsDepartments.add(imsDepartment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            // 关闭资源
            JDBCUtil.close(rs, ps, connection);
        }

        return imsDepartments;
    }


    public static boolean updata(ImsDepartment imsDepartment){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            System.out.println(imsDepartment.getDptId());
            connection = JDBCUtil.getConnection();
            String sql = "UPDATE ims_department set dpt_name = ?,update_time = ? where dpt_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,imsDepartment.getDptName());
            ps.setString(2,imsDepartment.getUpdateTime());
            ps.setString(3,imsDepartment.getDptId());
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtil.close(ps,connection);
        }
    }
}

