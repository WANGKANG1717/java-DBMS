package com.dbms.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket=new Socket("localhost", 1717);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
        //先从键盘输入一串指令
        String s= sin.readLine();
        String ss;
        while (true) {
            System.out.println(s);
            pw.println(s);
            pw.flush();
            ss=br.readLine();
            while(!ss.equals("over")) {
                System.out.println(ss);
                ss=br.readLine();
            }
            s= sin.readLine();
        }
    }
}
