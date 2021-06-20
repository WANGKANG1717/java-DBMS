package com.dbms.operator;

import com.dbms.db.DB;
import com.dbms.db.Table;
import com.dbms.pair.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Operator {
    private String command;
    private String[] s;
    private File dbFile;
    private DB db;
    private Table table;
    private HashMap<String, String> data;
    private Pair pair, from, to;
    private ArrayList<String> querylist;

    public Operator() throws Exception {
        dbFile = new File("src\\dbFile");
        if (dbFile.exists() == false) dbFile.createNewFile();
    }

    public File getDbFile() {
        return dbFile;
    }

    public void setCommand(String command) {
        this.command = command;
        s = command.split("\t");
    }

    public void executeCommand() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(dbFile));
        String[] list = null;
        switch (s[0]) {
            case "getDB":
                list = DB.getDBList();
                break;
            case "getTable":
                list = new DB(s[1]).getTable();
                break;
            case "getData":
                list = new Table(s[1], s[2]).getData();
                break;
            case "createDB":
                if (new DB(s[1]).createDB() == true) {
                    list = new String[]{"true"};
                } else {
                    list = new String[]{"false"};
                }
                break;
            case "deleteDB":
                if (new DB(s[1]).deleteDB() == true) {
                    list = new String[]{"true"};
                } else {
                    list = new String[]{"false"};
                }
                break;
            case "createTable":
                table = new Table(s[1], s[2]);
                data = new HashMap<>();
                for (int i = 3; i < s.length; i += 2) {
                    data.put(s[i], s[i + 1]);
                }
                if (table.createTable(data) == true) {
                    list = new String[]{"true"};
                } else {
                    list = new String[]{"false"};
                }
                break;
            case "deleteTable":
                if (new Table(s[1], s[2]).deleteTable() == true) {
                    list = new String[]{"true"};
                } else {
                    list = new String[]{"false"};
                }
                break;
            case "insert":
                table = new Table(s[1], s[2]);
                data = new HashMap<>();
                for (int i = 3; i < s.length; i += 2) {
                    data.put(s[i], s[i + 1]);
                }
                if (table.insert(data) == true) {
                    list = new String[]{"true"};
                } else {
                    list = new String[]{"false"};
                }
                break;
            case "delete":
                table = new Table(s[1], s[2]);
                pair = new Pair(s[3], s[4]);
                if (table.delete(pair) == true) {
                    list = new String[]{"true"};
                } else {
                    list = new String[]{"false"};
                }
                break;
            case "update":
                table = new Table(s[1], s[2]);
                from = new Pair(s[3], s[4]);
                to=new Pair(s[5], s[6]);
                if (table.update(from, to) == true) {
                    list = new String[]{"true"};
                } else {
                    list = new String[]{"false"};
                }
                break;
            case "queryAll":
                table = new Table(s[1], s[2]);
                querylist=table.queryAll();
                list=new String[querylist.size()];
                for(int i=0; i<querylist.size(); i++) {
                    list[i]=querylist.get(i);
                }
                break;
            case "query":
                table = new Table(s[1], s[2]);
                pair=new Pair(s[3], s[4]);
                querylist=table.query(pair);
                list=new String[querylist.size()];
                for(int i=0; i<querylist.size(); i++) {
                    list[i]=querylist.get(i);
                }
                break;
            default:
                list = new String[]{"false"};
        }
        for (int i = 0; i < list.length; i++) {
            bw.write(list[i]);
            bw.newLine();
        }
        bw.write("over");
        bw.newLine();
        bw.close();
    }
}
