package com.dbms.operator;

import com.dbms.data.Data;
import com.dbms.data.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public interface Operator {
    ArrayList<String[]> execute(String sql);

    String getUsername();

    String getPassword();

    /**
     * 返回当前用户所拥有的db名
     *
     * @return
     */
    ArrayList<String> getDB();

    /**
     * 返回当前数据库所拥有的表名
     *
     * @param dbName
     * @return
     */
    ArrayList<String> getTable(String dbName);

    /**
     * 返回当前表所拥有的数据名及类型
     *
     * @param dbName
     * @param tableName
     * @return
     */
    ArrayList<Pair> getData(String dbName, String tableName);

    /**
     * 创建数据库
     *
     * @param dbName 数据库名称
     * @return 成功 true 失败 false
     */
    boolean createDB(String dbName);

    /**
     * 删库
     *
     * @param dbName 数据库名称
     * @return
     */
    boolean deleteDB(String dbName);

    /**
     * 建表
     *
     * @param tableName 表名称
     * @return 成功 true 失败 false
     */
    boolean createTable(String dbName, String tableName, Data data);

    /**
     * 删表
     *
     * @param tableName 表名称
     * @return
     */
    boolean deleteTable(String dbName, String tableName);

    /**
     * 表中数据的增删改查
     */
    boolean insert(String dbName, String tableName, Data data);

    boolean delete(String dbName, String tableName, Pair pair);

    boolean update(String dbName, String tableName, Pair updateTo, Pair updateFrom);

    ArrayList<String[]> query(String dbName, String tableName);

    ArrayList<String[]> query(String dbName, String tableName, Pair pair);

    public String getDate();
}
