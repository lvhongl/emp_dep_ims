package com.codingfuture.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.*;

/**
 * JDBC 工具类，提供数据库连接及资源释放的工具方法。
 *
 * @Author KOBE
 */
public class JDBCUtil {

    private static String url;
    private static String user;
    private static String password;
    private static String driver;

    // 静态代码块，用于加载数据库配置文件和驱动类
    static DataSource ds;
    static {
        ds = new ComboPooledDataSource();
    }

    /**
     * 获取数据库连接。
     *
     * @return 数据库连接对象
     * @throws SQLException 如果获取连接失败
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    /**
     * 释放资源（Statement 和 Connection）。
     *
     * @param stmt 要释放的 Statement 对象
     * @param conn 要释放的 Connection 对象
     */
    public static void close(Statement stmt, Connection conn) {
        close(null, stmt, conn);
    }

    /**
     * 释放资源（ResultSet, Statement 和 Connection）。
     *
     * @param rs   要释放的 ResultSet 对象
     * @param stmt 要释放的 Statement 对象
     * @param conn 要释放的 Connection 对象
     */
    public static void close(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null ) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}