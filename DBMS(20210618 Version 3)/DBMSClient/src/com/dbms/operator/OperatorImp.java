package com.dbms.operator;

import com.dbms.data.Data;
import com.dbms.data.Pair;
import com.dbms.jdbc.JDBCUtils;
//import com.dbms.jdbc.JDBCUtils;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class OperatorImp implements Operator {
    private static JDBCUtils jdbc;
    private static String username;
    private static String password;
    File dbFile;

    public OperatorImp(String username, String password) {
        this.username = username;
        this.password = password;
        jdbc = new JDBCUtils(username, password);
        dbFile = new File("src\\dbFile");
    }

    /**
     * @param sql
     * @return $success表示执行成功 $fail表示执行失败 否则表示存在结果集
     */
    @Override
    public ArrayList<String[]> execute(String sql) {
        int flag;
        ArrayList<String[]> res = new ArrayList<>();
        String[] s;
        String msg;
        try {
            jdbc.sendCommand(sql);
            BufferedReader br = new BufferedReader(new FileReader(dbFile));
            while ((msg = br.readLine()) != null) {
                String[] value = msg.split("\t");
                res.add(value);
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
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
        ArrayList<String> res = new ArrayList<>();
        String dbName;
        int flag;
        try {
            jdbc.sendCommand("getDB");
            BufferedReader br = new BufferedReader(new FileReader(dbFile));
            while ((dbName = br.readLine()) != null) {
                res.add(dbName);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public ArrayList<String> getTable(String dbName) {
        ArrayList<String> res = new ArrayList<>();
        String tableName;
        int flag;
        try {
            jdbc.sendCommand("getTable\t" + dbName);
            BufferedReader br = new BufferedReader(new FileReader(dbFile));
            while ((tableName = br.readLine()) != null) {
                res.add(tableName);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public ArrayList<Pair> getData(String dbName, String tableName) {
        ArrayList<Pair> res = new ArrayList<>();
        Pair pair;
        int flag;
        try {
            jdbc.sendCommand("getData\t" + dbName + "\t" + tableName);
            BufferedReader br = new BufferedReader(new FileReader(dbFile));
            String s = br.readLine();
            String[] columnName = s.split("\t");
            s = br.readLine();
            String[] columnType = s.split("\t");
            for (int i = 0; i < columnName.length; i++) {
                res.add(new Pair(columnName[i], columnType[i]));
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean createDB(String dbName) {
        boolean flag = false;
        try {
            jdbc.sendCommand("createDB\t" + dbName);
            BufferedReader br = new BufferedReader(new FileReader(dbFile));
            String s = br.readLine();
            if (s.equals("true")) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean deleteDB(String dbName) {
        boolean flag = false;
        try {
            jdbc.sendCommand("deleteDB\t" + dbName);
            BufferedReader br = new BufferedReader(new FileReader(dbFile));
            String s = br.readLine();
            if (s.equals("true")) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean createTable(String dbName, String tableName, Data data) {
        boolean flag = false;
        try {
            String command = "createTable\t" + dbName + "\t" + tableName+"\t";
            for (int i = 0; i < data.getSize(); i++) {
                Pair pair = data.getPair(i);
                command += (pair.getFirst() + "\t" + pair.getSecond() + "\t");
            }
            jdbc.sendCommand(command);
            BufferedReader br = new BufferedReader(new FileReader(dbFile));
            String s = br.readLine();
            if (s.equals("true")) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean deleteTable(String dbName, String tableName) {
        boolean flag = false;
        try {
            jdbc.sendCommand("deleteTable\t" + dbName + "\t" + tableName);
            BufferedReader br = new BufferedReader(new FileReader(dbFile));
            String s = br.readLine();
            if (s.equals("true")) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 下面全是对表中数据的操作
     */
    @Override
    public boolean insert(String dbName, String tableName, Data data) {
        boolean flag = false;
        int flag_;
        String sql = null;
        try {
            String command = "insert\t" + dbName + "\t" + tableName + "\t";
            for (int i = 0; i < data.getSize(); i++) {
                Pair pair = data.getPair(i);
                command += (pair.getFirst() + "\t" + pair.getSecond() + "\t");
            }
            jdbc.sendCommand(command);
            BufferedReader br = new BufferedReader(new FileReader(dbFile));
            String s = br.readLine();
            if (s.equals("true")) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    /**
     * del.fir 你要删的表
     * del.sec 你要删的表中数据名，用来判断是不是要删
     */
    public boolean delete(String dbName, String tableName, Pair del) {
        boolean flag = false;
        int flag_;
        try {
            String command = "delete\t" + dbName + "\t" + tableName
                    + "\t" + del.getFirst() + "\t" + del.getSecond();
            jdbc.sendCommand(command);
            BufferedReader br = new BufferedReader(new FileReader(dbFile));
            String s = br.readLine();
            if (s.equals("true")) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean update(String dbName, String tableName, Pair updateTo, Pair updateFrom) {
        boolean flag = false;
        int flag_;
        try {
            String command = "update\t" + dbName + "\t" + tableName
                    + "\t" + updateFrom.getFirst() + "\t" + updateFrom.getSecond()
                    + "\t" + updateTo.getFirst() + "\t" + updateTo.getSecond();
            jdbc.sendCommand(command);
            BufferedReader br = new BufferedReader(new FileReader(dbFile));
            String s = br.readLine();
            if (s.equals("true")) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public ArrayList<String[]> query(String dbName, String tableName) {
        int flag;
        ArrayList<String[]> res = new ArrayList<>();
        String[] s;
        String msg;
        try {
            String command = "queryAll\t" + dbName + "\t" + tableName;
            jdbc.sendCommand(command);
            BufferedReader br = new BufferedReader(new FileReader(dbFile));
            while ((msg = br.readLine()) != null) {
                String[] value = msg.split("\t");
                res.add(value);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public ArrayList<String[]> query(String dbName, String tableName, Pair pair) {
        int flag;
        ArrayList<String[]> res = new ArrayList<>();
        String[] s;
        String msg;
        try {
            String command = "query\t" + dbName + "\t" + tableName + "\t" + pair.getFirst() + "\t" + pair.getSecond();
            jdbc.sendCommand(command);
            BufferedReader br = new BufferedReader(new FileReader(dbFile));
            while ((msg = br.readLine()) != null) {
                String[] value = msg.split("\t");
                res.add(value);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z ");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}
