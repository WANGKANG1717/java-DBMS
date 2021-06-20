package com.dbms.sever;

import com.dbms.operator.Operator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DBSever {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(1717);
        Socket socket = serverSocket.accept();
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
        //
        Operator op=new Operator();
        //
        String s = br.readLine();
        String res;
        while (!s.equals("exit")) {
            System.out.println(s);
            op.setCommand(s);
            op.executeCommand();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(op.getDbFile()));
            while((res=bufferedReader.readLine())!=null) {
                pw.println(res);
                pw.flush();
            }
            s = br.readLine();
        }
        pw.close();
        br.close();
        sin.close();
        socket.close();
        serverSocket.close();
    }
}
