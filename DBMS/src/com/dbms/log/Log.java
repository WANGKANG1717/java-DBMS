package com.dbms.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
    private FileWriter fw = null;
    File f=null;
    public Log() {
        f = new File("D:\\JAVA\\DBMS\\src\\com\\dbms\\log\\log.txt");
        try {
            if(!f.exists()){
                f.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void append(String s) {
        try {
            fw = new FileWriter(f, true);
            fw.write(s+"\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
