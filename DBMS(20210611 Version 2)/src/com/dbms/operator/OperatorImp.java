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
    /**
     * SQL Sever 1
     * Mysql 2
     */
    private static int dbtype;

    public OperatorImp(String username, String password, String dbtype) {
        this.username = username;
        this.password = password;
        this.dbtype = dbtype.equals("SQL Sever") ? 1 : 2;
        jdbc = new JDBCUtils(username, password, dbtype);
    }

    /**
     * 因为mysql这货比较特殊
     * 非得叫我一条语句一条语句的执行才说的过去
     * 那我就突发奇想，重新写了一个函数
     *
     * @param sql
     * @return $success表示执行成功 $fail表示执行失败 否则表示存在结果集
     */
    @Override
    public ArrayList<String[]> execute(String sql) {
        conn = jdbc.getConnection();
        ArrayList<String[]> results = new ArrayList<>();
        try {
            statement = conn.createStatement();
            boolean f = false;
            String SQL[] = sql.split(";");
            for (int j = 0; j < SQL.length; j++) {
                //说明存在结果集
                if (statement.execute(SQL[j])) {
                    f = true;
                    res = statement.executeQuery(SQL[j]);
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
                }
                else results.add(new String[]{"$success"});
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            results.add(new String[]{"$fail"});
        } finally {
            JDBCUtils.close(conn, statement, res);
        }
        return results;
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
     * 全局变量虽然好用
     * 但是容易产生副作用
     * 以后还是尽量局部变量吧
     */
    private Connection conn;
    private PreparedStatement ps;
    private Statement statement;
    private ResultSet res;
    private boolean flag;

    @Override
    public ArrayList<String> getDB() {
        conn = jdbc.getConnection();
        String sql = (dbtype == 1) ? "Select name FROM master..SysDatabases WHERE dbid>4 ORDER BY Name;" :
                "select schema_name from information_schema.schemata where schema_name not in('information_schema', 'performance_schema', 'sys', 'mysql')";
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
        String sql = "USE " + dbName + ";";
        String sql2 = ((dbtype == 1) ? "SELECT * from sysobjects WHERE xtype='U'" : "show tables;");
        ArrayList<String> tableName = new ArrayList<>();
        String s;
        try {
            statement = conn.createStatement();
            statement.execute(sql);
            res = statement.executeQuery(sql2);
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
        String sql, sql2;
        ArrayList<Pair> data = new ArrayList<>();
        try {
            sql = "use " + dbName + ";";
            statement = conn.createStatement();
            statement.execute(sql);
            if (dbtype == 1) {
                sql2 = "select name from syscolumns where id = object_id('" + tableName + "');";
                res = statement.executeQuery(sql2);
                while (res.next()) {
                    String colName = new String();
                    String colType = new String();
                    colName = res.getString("name");
                    colType = getType(dbName, tableName, colName);
                    data.add(new Pair(colName, colType));
                }
            } else {
                sql2 = "select column_name name,data_type type from information_schema.columns where table_name='" + tableName + "' and table_schema='" + dbName + "';";
                res = statement.executeQuery(sql2);
                while (res.next()) {
                    String colName = new String();
                    String colType = new String();
                    colName = res.getString("name");
                    colType = res.getString("type");
                    data.add(new Pair(colName, colType));
                }
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
            statement.execute("CREATE DATABASE " + dbName);
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
            statement.execute("DROP DATABASE " + dbName);
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
    public boolean createTable(String dbName, String tableName, String s) {
        conn = jdbc.getConnection();
        Pair pair;
        try {
            statement = conn.createStatement();
            statement.execute("USE " + dbName);
            String sql = "CREATE TABLE " + tableName + " (" + s + ")";
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
     * 下面全是对表中数据的操作
     */
    @Override
    public boolean insert(String dbName, String tableName, Data data) {
        conn = jdbc.getConnection();
        //得到完整的sql预处理语句
        int i;
        String sql = " INSERT INTO " + tableName + " (";
        for (i = 0; i < data.getSize() - 1; i++) {
            sql += data.getPair(i).getFirst();
            sql += ", ";
        }
        sql += data.getPair(i).getFirst();
        sql += ") VALUES(";
        Pair pair;
        try {
            statement = conn.createStatement();
            for (i = 0; i < data.getSize(); i++) {
                pair = data.getPair(i);
                //这里做的还不是很完善，知识大致的一个雏形
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
            //
            statement.execute("USE " + dbName + ";");
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
     * del.fir 你要删的表
     * del.sec 你要删的表中数据名，用来判断是不是要删
     */
    public boolean delete(String dbName, String tableName, Pair del) {
        conn = jdbc.getConnection();
        try {
            statement = conn.createStatement();
            String sql = "DELETE FROM " + tableName + " WHERE " + del.getFirst() + " = ";
            switch (getType(dbName, tableName, del.getFirst())) {
                case "int":
                    sql += del.getSecond();
                    break;
                default:
                    sql += getString(del.getSecond(), '\'');
                    break;
            }
            statement.execute("USE " + dbName);
//            System.out.println(sql);
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
            String sql = "UPDATE tableName SET First=? WHERE Second=?;";
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
            statement = conn.createStatement();
            statement.execute("use " + dbName);
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
        String sql = "SELECT * from " + tableName;
        ArrayList<String[]> results = new ArrayList<>();
        try {
            statement = conn.createStatement();
            statement.execute("USE " + dbName);
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
        String sql = "SELECT * from " + tableName + " Where " + pair.getFirst() + " = ";
        if (getType(dbName, tableName, pair.getFirst()) == "int") {
            sql += pair.getSecond();
        } else {
            sql += getString(pair.getSecond(), '\'');
        }
        ArrayList<String[]> results = new ArrayList<>();
        try {
            statement = conn.createStatement();
            statement.execute("use " + dbName);
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
        if (dbtype == 1) {
            String sql = "select name from systypes where\n" +
                    "xtype in (select xtype  from syscolumns where name = ? and\n" +
                    "          id in (select ID from sysobjects where name = ?));";
            try {
                statement = conn.createStatement();
                statement.execute("USE " + dbName);
                ps = conn.prepareStatement(sql);
                ps.setString(1, columnName);
                ps.setString(2, tableName);
                ResultSet res = ps.executeQuery();
                if (res.next()) result = res.getString("name");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String sql = "select data_type from information_schema.columns where table_name='" + tableName + "' and table_schema='" + dbName + "' and column_name='" + columnName + "';";
            try {
                statement = conn.createStatement();
                ResultSet res = statement.executeQuery(sql);
                if (res.next()) result = res.getString("data_type");
            } catch (Exception e) {
                e.printStackTrace();
            }
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
