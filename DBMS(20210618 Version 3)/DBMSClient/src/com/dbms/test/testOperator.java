package com.dbms.test;

import com.dbms.operator.Operator;
import com.dbms.operator.OperatorImp;

public class testOperator {
    public static void main(String[] args) {
        Operator op=new OperatorImp("sa", "123456");
//        op.createDB("asdsada");
//        op.getTable("asdasd");
//        op.getData("dbName", "tableName");
        op.deleteDB("adad");
    }
}
