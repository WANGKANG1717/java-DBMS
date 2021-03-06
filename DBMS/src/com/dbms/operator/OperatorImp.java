package com.dbms.operator;

import com.dbms.data.Data;
import com.dbms.data.Pair;
import com.dbms.jdbc.JDBCUtils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class OperatorImp implements Operator {
    private static JDBCUtils jdbc;
    private static String username;
    private static String password;

    public OperatorImp(String username, String password) {
        this.username = username;
        this.password = password;
        jdbc = new JDBCUtils(username, password);
    }

    @Override
    public ArrayList<String[]> query(String sql) {
        conn = jdbc.getConnection();
        ArrayList<String[]> results = new ArrayList<>();
        try {
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = res.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            String[] s = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                s[i] = resultSetMetaData.getColumnName(i + 1);
            }
            results.add(s);
            while (res.next()) {
                s = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    s[i] = res.getString(i + 1);
                }
                results.add(s);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.close(conn, statement, res);
        }
        return results;
    }

    @Override
    public boolean execute(String sql) {
        conn = jdbc.getConnection();
        boolean f=false;
        try {
            statement = conn.createStatement();
            statement.execute(sql);
            f=true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.close(conn, statement, null);
        }
        return f;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * ????????????????????????
     * ???????????????????????????
     * ?????????????????????????????????
     */
    private Connection conn;
    private PreparedStatement ps;
    private Statement statement;
    private ResultSet res;
    private boolean flag;

    @Override
    public ArrayList<String> getDB() {
        conn = jdbc.getConnection();
        String sql = "Select name FROM master..SysDatabases WHERE dbid>4 ORDER BY Name";
        ArrayList<String> dbName = new ArrayList<>();
        String s;
        try {
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            while (res.next()) {
                s = new String();
                s = res.getString(1);
                dbName.add(s);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.close(conn, statement, res);
        }
        return dbName;
    }

    @Override
    public ArrayList<String> getTable(String dbName) {
        conn = jdbc.getConnection();
        String sql = "USE " + dbName + "; SELECT * from sysobjects WHERE xtype='U'";
        ArrayList<String> tableName = new ArrayList<>();
        String s;
        try {
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            while (res.next()) {
                s = new String();
                s = res.getString(1);
                tableName.add(s);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.close(conn, statement, res);
        }
        return tableName;
    }

    @Override
    public ArrayList<Pair> getData(String dbName, String tableName) {
        conn = jdbc.getConnection();
        String sql = "use " + dbName + "; select name from syscolumns where id = object_id('" + tableName + "');";
        ArrayList<Pair> data = new ArrayList<>();
//        System.out.println(sql);
        try {
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
//            System.out.println(res.toString());
            while (res.next()) {
                String colName = new String();
                String colType = new String();
                colName = res.getString("name");
                colType = getType(dbName, tableName, colName);
//                System.out.println(colName + "   "+colType);
                data.add(new Pair(colName, colType));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.close(conn, statement, res);
        }
        return data;
    }

    @Override
    public boolean createDB(String dbName) {
        conn = jdbc.getConnection();
        try {
            statement = conn.createStatement();
            statement.execute("CREATE DATABASE" + getString(dbName, '\"'));
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            JDBCUtils.close(conn, statement, null);
        }
        return flag;
    }

    @Override
    public boolean deleteDB(String dbName) {
        conn = jdbc.getConnection();
        try {
            statement = conn.createStatement();
            statement.execute("DROP DATABASE" + getString(dbName, '\"'));
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            JDBCUtils.close(conn, statement, null);
        }
        return flag;
    }

    @Override
    public boolean createTable(String dbName, String tableName, Data data) {
        conn = jdbc.getConnection();
        Pair pair;
        try {
            statement = conn.createStatement();
            statement.execute("USE " + dbName);
            String sql = "CREATE TABLE " + tableName + " (";
            for (int i = 0; i < data.getSize(); i++) {
                pair = data.getPair(i);
                sql += pair.getFirst();
                sql += " ";
                sql += pair.getSecond();
                sql += ",";
            }
            sql += ")";
//            System.out.println("sql=" + sql);
            statement.execute(sql);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            JDBCUtils.close(conn, statement, null);
        }
        return flag;
    }

    @Override
    public boolean deleteTable(String dbName, String tableName) {
        conn = jdbc.getConnection();
        try {
            statement = conn.createStatement();
            statement.execute("USE " + dbName);
            statement.execute("DROP TABLE " + tableName);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            JDBCUtils.close(conn, statement, null);
        }
        return flag;
    }

    /**
     * ????????????????????????????????????
     */
    @Override
    public boolean insert(String dbName, String tableName, Data data) {
        conn = jdbc.getConnection();
        //???????????????sql???????????????
        int i;
        String sql = "INSERT INTO " + tableName + " (";
        for (i = 0; i < data.getSize() - 1; i++) {
            sql += data.getPair(i).getFirst();
            sql += ", ";
        }
        sql += data.getPair(i).getFirst();
        sql += ") VALUES(";
        Pair pair;
        try {
            statement = conn.createStatement();
            statement.execute("USE " + dbName);
            for (i = 0; i < data.getSize(); i++) {
                pair = data.getPair(i);
                //????????????????????????????????????????????????????????????
                switch (getType(dbName, tableName, pair.getFirst())) {
                    case "int":
                        sql += pair.getSecond();
                        break;
                    case "varchar":
                    case "char":
                        sql += getString(pair.getSecond(), '\'');
                        break;
                    default:
                        sql += getString(pair.getSecond(), '\'');
                        break;
                }
                sql += ",";
            }
            StringBuffer sb = new StringBuffer(sql);
            sql = sb.replace(sb.length() - 1, sb.length(), ");").toString();
            statement.execute(sql);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            JDBCUtils.close(conn, statement, null);
        }
        return flag;
    }

    @Override
    /**
     * del.fir ???????????????
     * del.sec ?????????????????????????????????????????????????????????
     */
    public boolean delete(String dbName, String tableName, Pair del) {
        conn = jdbc.getConnection();
        try {
            statement = conn.createStatement();
            statement.execute("USE " + dbName);
            String sql = "DELETE FROM " + tableName + " WHERE " + del.getFirst() + " = ";
            switch (getType(dbName, tableName, del.getFirst())) {
                case "int":
                    sql += del.getSecond();
                    break;
                default:
                    sql += getString(del.getSecond(), '\'');
                    break;
            }
            statement.execute(sql);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            JDBCUtils.close(conn, statement, null);
        }
        return flag;
    }

    @Override
    public boolean update(String dbName, String tableName, Pair updateTo, Pair updateFrom) {
        conn = jdbc.getConnection();
        try {
            String sql = "USE dbName; UPDATE tableName SET First=? WHERE Second=?;";
            sql = sql.replaceFirst("dbName", dbName);
            sql = sql.replaceFirst("tableName", tableName);
            sql = sql.replaceFirst("First", updateTo.getFirst());
            if (getType(dbName, tableName, updateTo.getFirst()) == "int") {
                sql = sql.replaceFirst("\\?", updateTo.getSecond());
            } else {
                sql = sql.replaceFirst("\\?", getString(updateTo.getSecond(), '\''));
            }
            sql = sql.replaceFirst("Second", updateFrom.getFirst());
            if (getType(dbName, tableName, updateFrom.getFirst()) == "int") {
                sql = sql.replaceFirst("\\?", updateFrom.getSecond());
            } else {
                sql = sql.replaceFirst("\\?", getString(updateFrom.getSecond(), '\''));
            }
//            System.out.println(sql);
            //
            statement = conn.createStatement();
            statement.execute(sql);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            JDBCUtils.close(conn, ps, null);
        }
        return flag;
    }

    @Override
    public ArrayList<String[]> query(String dbName, String tableName) {
        conn = jdbc.getConnection();
        String sql = "USE " + dbName + "; SELECT * from " + tableName;
        ArrayList<String[]> results = new ArrayList<>();
        try {
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = res.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            String[] s = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                s[i] = resultSetMetaData.getColumnName(i + 1);
            }
            results.add(s);
            while (res.next()) {
                s = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    s[i] = res.getString(i + 1);
                }
                results.add(s);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.close(conn, statement, res);
        }
        return results;
    }

    @Override
    public ArrayList<String[]> query(String dbName, String tableName, Pair pair) {
        conn = jdbc.getConnection();
        String sql = "USE " + dbName + "; SELECT * from " + tableName + " Where " + pair.getFirst() + " = ";
        if (getType(dbName, tableName, pair.getFirst()) == "int") {
            sql += pair.getSecond();
        } else {
            sql += getString(pair.getSecond(), '\'');
        }
        ArrayList<String[]> results = new ArrayList<>();
        try {
            statement = conn.createStatement();
//            System.out.println(sql);
            res = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = res.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            String[] s = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                s[i] = resultSetMetaData.getColumnName(i + 1);
            }
            results.add(s);
            while (res.next()) {
                s = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    s[i] = res.getString(i + 1);
                }
                results.add(s);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.close(conn, statement, res);
        }
        return results;
    }

    @Override
    public String getString(String s, char ch) {
        return ch + s + ch;
    }

/*    @Override
    public String replace(int begin, int end, String s, String rep) {
        StringBuffer sb = new StringBuffer(s);
        return sb.replace(begin, end, rep).toString();
    }*/

    @Override
    /*
        select a.name column,b.name type
        from syscolumns a,systypes b
        where a.id=object_id('tableName') and a.xtype=b.xtype and a.name='columnName'
     */
    public String getType(String dbName, String tableName, String columnName) {
        conn = jdbc.getConnection();
        String result = null;
        String sql = "select name from systypes where\n" +
                "xtype in (select xtype  from syscolumns where name = ? and\n" +
                "          id in (select ID from sysobjects where name = ?));";
//        System.out.println(sql);
        try {
            statement = conn.createStatement();
            statement.execute("USE " + dbName);
            //
            ps = conn.prepareStatement(sql);
            ps.setString(1, columnName);
            ps.setString(2, tableName);
            ResultSet res = ps.executeQuery();
//            System.out.println(res.toString());
            //???????????????????????????next?????????????????????????????????
            //??????????????????????????????
            //?????????????????????????????????
            //????????????????????????????????????????????????
            if (res.next()) result = res.getString("name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void PrintRes(ResultSet res) {
        try {
            ResultSetMetaData resultSetMetaData = res.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSetMetaData.getColumnName(i) + "\t\t");
            }
            System.out.println();
            while (res.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(res.getString(i) + "\t\t");
                }
                System.out.println();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z ");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}
