package com.dbms.test;

import com.dbms.data.Data;
import com.dbms.data.Pair;
import com.dbms.jdbc.JDBCUtils;
import com.dbms.operator.Operator;
import com.dbms.operator.OperatorImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class testOperator {
    public static void main(String[] args) {
//        Connection conn = JDBCUtils.getConnection();
        OperatorImp op = new OperatorImp("sa", "123456");
//        ArrayList<String> dbName=op.getDB();
//        for(int i=0; i<dbName.size(); i++) {
//            System.out.println(dbName.get(i));
//        }
//        ArrayList<String> tableName = op.getTable("data");
//        for (int i = 0; i < tableName.size(); i++) {
//            System.out.println(tableName.get(i));
//        }
        ArrayList<Pair> data = op.getData("SC", "SC");
        for (int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i).getFirst() + " "+data.get(i).getSecond());
        }
//        System.out.println((op.createDB("Myd55")==true)? "创建成功": "创建失败");
//        System.out.println((op.deleteDB("Myd111123")==true)? "删除成功": "删除失败");
//        System.out.println((op.deleteTable("Mydb", "SStu")==true)? "删表成功": "删表失败");
/*        Data data=new Data();
        data.addData("id", "int");
        data.addData("uname", "varchar(10)");
        data.addData("upass", "varchar(10)");
        System.out.println((op.createTable("Mydb", "SStu", data, "id")==true)? "建表成功": "建表失败");*/
//        System.out.println(op.getType("Mydb", "Stu", "id"));

/*        Pair del=new Pair("upass", "123123");
        System.out.println((op.delete("Mydb", "Stu", del)==true)? "删除成功": "删除失败");*/
/*        Pair updateTo=new Pair("upass", "aaab11");
        Pair updateFrom=new Pair("uname", "wadad");
        System.out.println((op.update("Mydb", "Stu", updateTo, updateFrom)==true)? "更新成功": "更新失败");*/
/*        ArrayList<String[]> results = op.query("Mydb", "Stu", new Pair("id", "2"));
        if(results.size()>0) {
            for(int i=0; i<results.size(); i++) {
                String[] s= results.get(i);
                for(int j=0; j<s.length; j++) {
                    System.out.print(s[j]+"\t\t\t");
                }
                System.out.println();
            }
        }
        else {
            System.out.println("查询失败");
        }*/
    }
}
