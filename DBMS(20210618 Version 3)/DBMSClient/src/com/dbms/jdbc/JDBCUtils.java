package com.dbms.jdbc;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class JDBCUtils {
    private static String username;
    private static String password;
    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;
    private File dbFile;
    public JDBCUtils(String user, String pass){
        try {
            socket = new Socket("localhost", 1717);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream());
            dbFile=new File("src\\dbFile");
            if(dbFile.exists()==false) dbFile.createNewFile();
        } catch (Exception e) {
            ;
        }
    }

    //向服务器发送消息

    /**
     * true 执行成功        返回0
     * false 执行失败       返回1
     * resultset 存在结果集 返回2
     */
    public void sendCommand(String sMsg) throws Exception {
        //发送消息
        pw.println(sMsg);
        pw.flush();
        String s;
        BufferedWriter bw=new BufferedWriter(new FileWriter(dbFile));
        s = br.readLine();
        while(!s.equals("over")) {
            System.out.println(s);
            bw.write(s);
            bw.newLine();
            s = br.readLine();
        }
        bw.close();
    }
    public void close() throws Exception {
        pw.close();
        br.close();
        socket.close();
    }
}