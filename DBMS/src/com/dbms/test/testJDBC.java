package com.dbms.test;

import com.dbms.jdbc.JDBCUtils;

public class testJDBC {
    public  static void main(String[] args) {
        JDBCUtils jdbc=new JDBCUtils("sa", "123456");
        jdbc.getConnection();
    }
}
