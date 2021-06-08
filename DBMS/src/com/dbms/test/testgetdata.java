package com.dbms.test;

import com.dbms.data.Pair;
import com.dbms.jdbc.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;

public class testgetdata {
    static JDBCUtils jdbc;
    static Statement statement;
    static ResultSet res, res2;
    static Connection conn;
    static PreparedStatement ps;
    public static void main(String[] asgs) {
        jdbc=new JDBCUtils("sa", "123456");
        conn = jdbc.getConnection();
        String sql = "use SC; select name from syscolumns where id = object_id('Stu');";
        ArrayList<Pair> data = new ArrayList<>();
        System.out.println(sql);
        try {
            statement = conn.createStatement();
            res2 = statement.executeQuery(sql);
            System.out.println(res2.toString());
            while (res2.next()) {
                String colName = new String();
                String colType = new String();
                colName = res2.getString(1);
                colType = getType("SC", "Stu", colName);
                System.out.println(colName+"   "+colType);
                data.add(new Pair(colName, colType));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.close(conn, statement, res2);
        }
    }
    public static String getType(String dbName, String tableName, String columnName) {
        conn = jdbc.getConnection();
        String result = null;
        String sql = "select name from systypes where\n" +
                "xtype in (select xtype  from syscolumns where name = ? and\n" +
                "          id in (select ID from sysobjects where name = ?));";
        try {
            statement = conn.createStatement();
            statement.execute("USE " + dbName);
            //
            ps = conn.prepareStatement(sql);
            ps.setString(1, columnName);
            ps.setString(2, tableName);
            res = ps.executeQuery();
            if (res.next()) result = res.getString("name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
