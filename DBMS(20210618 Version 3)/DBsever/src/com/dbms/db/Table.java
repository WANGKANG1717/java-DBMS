package com.dbms.db;

import com.dbms.pair.Pair;

import java.io.*;
import java.util.*;

public class Table extends DB {
    protected File tableFile;
    protected String tableName;
    protected HashMap<String, String> column;

    public Table(String dbName, String tableName) throws Exception {
        super(dbName);
        if (super.dbExists() == false) {
            throw new Exception("数据库" + dbName + "不存在");
        }
        if (nameValid(tableName) == false) {
            throw new Exception("表名" + tableName + "无效");
        }
        this.tableName = tableName;
        tableFile = new File(super.dbFile, tableName);
        if (tableExists()) getColumn();
    }

    public String[] getTable() {
        return dbFile.list();
    }

    public String[] getData()throws Exception {
        if (tableExists() == false) {
            throw new Exception("表" + tableName + "不存在");
        }
        BufferedReader br = new BufferedReader(new FileReader(tableFile));
        String[] head=new String[2];
        head[0]=br.readLine();
        head[1]=br.readLine();
        br.close();
        return head;
    }

    public boolean tableExists() {
        return tableFile.exists();
    }

    /**
     * 为了简单起见，且我选择使用String和继承自number的类
     * 在输入数据的时候做一个简单的判断就好了
     * 在进行处理的时候，全部使用String进行处理就够了
     * <p>
     * <p>
     * 罢了罢了
     * 现阶段进行这个数据的校验过于困难
     * <p>
     * 我就先做一个demo就够了
     * <p>
     * 关于数据的校验，等机会再好好商量吧！
     */
    public boolean createTable(HashMap<String, String> data) throws Exception {
        if (tableExists() == true) {
            throw new Exception("表" + tableName + "已存在");
        }
        tableFile.createNewFile();
        column = (HashMap<String, String>) data.clone();
        BufferedWriter bw = new BufferedWriter(new FileWriter(tableFile));
        for (String key : column.keySet()) {
            bw.write(key + "\t");
        }
        bw.newLine();
        for (String value : column.values()) {
            bw.write(value + "\t");
        }
        bw.newLine();
        bw.close();
        return true;
    }

    public boolean deleteTable() throws Exception {
        if (tableExists() == false) {
            throw new Exception("表" + tableName + "不存在");
        }
        return deleteFile(tableFile);
    }

    private void getColumn() throws Exception {
        column = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(tableFile));
        String s = br.readLine();
        String[] columnName = s.split("\t");
        s = br.readLine();
        String[] columnType = s.split("\t");
        for (int i = 0; i < columnName.length; i++) {
//            System.out.println(columnName[i] + " " + columnType[i]);
            column.put(columnName[i], columnType[i]);
        }
        br.close();
    }

    /**
     * 一次只能插入一条
     * 使用hashmap，可以保证插入的数据是正确的
     * 还有利于校验数据
     * 虽然我现在完全不想做这个！！！！
     */
    //增
    public boolean insert(HashMap<String, String> data) throws Exception {
        if (tableExists() == false) {
            throw new Exception("表" + tableName + "不存在");
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(tableFile, true));
        for (String key : column.keySet()) {
            bw.write(data.get(key) + "\t");
        }
        bw.newLine();
        bw.close();
        return true;
    }

    //删
    public boolean delete(Pair data) throws Exception {
        if (tableExists() == false) {
            throw new Exception("表" + tableName + "不存在");
        }
        BufferedReader br = new BufferedReader(new FileReader(tableFile));
        File temp = new File(dbFile, "temp");
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
        //
        String[] key = column.keySet().toArray(new String[0]);
        int keyIndex = -1;
        for (int i = 0; i < key.length; i++) {
            if (key[i].equals(data.getFirst())) {
                keyIndex = i;
                break;
            }
        }
        if (keyIndex == -1) {
            throw new Exception("未找到列名" + data.getFirst());
        }
        //先将头输入到temp中
        String s = br.readLine();
        bw.write(s);
        bw.newLine();
        s = br.readLine();
        bw.write(s);
        bw.newLine();
        //
        boolean flag = false;
        while ((s = br.readLine()) != null) {
            String[] value = s.split("\t");
            if (value[keyIndex].equals(data.getSecond())) {
                flag = true;
                continue;
            }
            bw.write(s);
            bw.newLine();
        }
        br.close();
        bw.close();
        tableFile.delete();
        temp.renameTo(tableFile);
        return flag;
    }

    //改
    public boolean update(Pair from, Pair to) throws Exception {
        if (tableExists() == false) {
            throw new Exception("表" + tableName + "不存在");
        }
        BufferedReader br = new BufferedReader(new FileReader(tableFile));
        File temp = new File(dbFile, "temp");
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
        //
        String[] key = column.keySet().toArray(new String[0]);
        int fromKeyIndex = -1;
        int toKeyIndex = -1;
        for (int i = 0; i < key.length; i++) {
            if (key[i].equals(from.getFirst())) {
                fromKeyIndex = i;
            }
            if (key[i].equals(to.getFirst())) {
                toKeyIndex = i;
            }
        }
        if (fromKeyIndex == -1) {
            throw new Exception("未找到列名" + from.getFirst());
        }
        if (toKeyIndex == -1) {
            throw new Exception("未找到列名" + to.getFirst());
        }
        //先将头输入到temp中
        String s = br.readLine();
        bw.write(s);
        bw.newLine();
        s = br.readLine();
        bw.write(s);
        bw.newLine();
        //
        boolean flag = false;
        while ((s = br.readLine()) != null) {
            String[] value = s.split("\t");
            if (value[fromKeyIndex].equals(from.getSecond())) {
                value[toKeyIndex] = to.getSecond();
                for (int i = 0; i < value.length; i++) {
                    bw.write(value[i] + "\t");
                }
                bw.newLine();
                flag = true;
                continue;
            }
            bw.write(s);
            bw.newLine();
        }
        br.close();
        bw.close();
        tableFile.delete();
        temp.renameTo(tableFile);
        return flag;
    }

    //查
    //成功将返回String数组
    //失败返回null
    public ArrayList<String> query(Pair data) throws Exception {
        if (tableExists() == false) {
            throw new Exception("表" + tableName + "不存在");
        }
        BufferedReader br = new BufferedReader(new FileReader(tableFile));
        //
        String[] key = column.keySet().toArray(new String[0]);
        int keyIndex = -1;
        for (int i = 0; i < key.length; i++) {
            if (key[i].equals(data.getFirst())) {
                keyIndex = i;
                break;
            }
        }
        if (keyIndex == -1) {
            throw new Exception("未找到列名" + data.getFirst());
        }
        ArrayList<String> res=new ArrayList<>();
        //先将头读出来
        String s = null;
        s = br.readLine();
        res.add(s);
        s = br.readLine();;
        while ((s = br.readLine()) != null) {
            String[] value = s.split("\t");
            if (value[keyIndex].equals(data.getSecond())) {
                res.add(s);
                continue;
            }
        }
        br.close();
        return res;
    }
    //查询整表
    public ArrayList<String> queryAll() throws Exception {
        if (tableExists() == false) {
            throw new Exception("表" + tableName + "不存在");
        }
        BufferedReader br = new BufferedReader(new FileReader(tableFile));
        ArrayList<String> res=new ArrayList<>();
        //先将头读出来
        String s = null;
        s = br.readLine();
        res.add(s);
        s = br.readLine();
        //
        while ((s = br.readLine()) != null) {
            res.add(s);
        }
        br.close();
        return res;
    }
}
