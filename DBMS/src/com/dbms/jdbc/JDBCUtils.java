package com.dbms.jdbc;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    /**
     * 加载驱动
     * @param user
     * @param pass
     */
    public JDBCUtils(String user, String pass) {
        //       获得对象 类加载器          加载资源文件放到输入流中
        InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("db.properties");
        //加载property类的对象
        Properties p = new Properties();
        boolean flag = false;
        //加载类文件
        try {
            p.load(is);
            driver = p.getProperty("driver");
            url = p.getProperty("url");
            //遍历一遍db.properties文件
            /*int usernum = Integer.parseInt(p.getProperty("usernum"));
            for (int i = 1; i <= usernum; i++) {
                username = p.getProperty("username" + i);
                password = p.getProperty("password" + i);
                if (username.equals(user) && password.equals(pass)) {
                    flag = true;
                    break;
                }
            }
            //加载sql驱动
            if(flag!=true) password=null;*/
            username=user;
            password=pass;
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    获得连接对象的方法
    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
//            System.out.println("数据库连接成功");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("数据库连接失败");
            return null;
        }
    }

    public static void close(Connection conn, Statement statement, ResultSet resultSet) {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
            if (resultSet != null) {
                resultSet.close();
                resultSet = null;
            }
            if (statement != null) {
                statement.close();
                statement = null;
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }
}