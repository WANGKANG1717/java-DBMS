package com.dbms.view;

import com.dbms.jdbc.JDBCUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Login extends JFrame {

    public Login() {
        this.setTitle("连接到服务器");
        this.setIconImage(getImage("icon\\logo_Login.png"));   //设置窗口的logo
        //添加界面元素
        addLogin();
        //设置可见
        this.setVisible(true);
        //设置初始大小和初始位置
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.pack();
        this.setResizable(false);
        this.setLocation((scrSize.width - this.getWidth()) / 2, (scrSize.height - this.getHeight()) / 2);
    }

    private void addLogin() {
        //设置内容
        JPanel wel = new JPanel();
        JLabel welLabel = new JLabel("欢迎来到WK的DBMS，请登录!");
        welLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welLabel.setFont(new Font("楷体", Font.PLAIN, 25));
        wel.add(welLabel);
        this.add(wel);
        JLabel[] jl = new JLabel[]{
                new JLabel("数据库类型:"),
                new JLabel("登录名:"),
                new JLabel("密码:"),
        };
        JTextField userName=new JTextField(14);
        JTextField userPass=new JPasswordField(14);
        String[] dbType_ = {"FastDB","SQL Sever", "Mysql"};
        JComboBox dbType=new JComboBox(dbType_);
        dbType.setPreferredSize(new Dimension(145, 20));
        JButton[] jb = new JButton[]{
                new JButton("连接"),
                new JButton("取消"),
                new JButton("帮助"),
                new JButton("选项 >>"),
        };
        //设置布局
        JPanel[] jp = new JPanel[]{
                new JPanel(),
                new JPanel(),
                new JPanel(),
                new JPanel(),
        };
        jp[0].add(jl[0]);
        jp[0].add(dbType);
        jp[1].add(jl[1]);
        jp[1].add(userName);
        jp[2].add(jl[2]);
        jp[2].add(userPass);
        dbType.setEnabled(false);
        for (int i = 0; i < jl.length; i++) {
            jl[i].setPreferredSize(new Dimension(200, 20));
            jp[i].setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        }
        /**
         * 添加时间监听器
         */
        ActionListener ls = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == jb[0]) {
                    String username = userName.getText();
                    String password = userPass.getText();
                    if (username.equals("sa") && password.equals("123456")) {
                        Login.this.setVisible(false);
                        new DBMSWindow(username, password);
                    } else {
                        JOptionPane.showMessageDialog(null, "账号密码有误,请重新输入", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (e.getSource() == jb[1]) {
                    userName.setText("");
                    userPass.setText("");
                } else if (e.getSource() == jb[2]) {
                    JOptionPane.showMessageDialog(null, "一万个鄙视😒送给你");
                } else if (e.getSource() == jb[3]) {
                    Object[] options = {"喜欢", "非常喜欢"};  //自定义按钮上的文字
                    int m = JOptionPane.showOptionDialog(null, "你喜欢我做的软件吗(害羞😳)?\n         (假装这里有选项)", "Title", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if(m==0) {
                        JOptionPane.showMessageDialog(null, "嘿嘿,谢谢谢谢!");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "嘿嘿,很有眼光的小虎纸/小姑凉!");
                    }
                }
            }
        };
        for (int i = 0; i < 4; i++) {
            jp[3].add(jb[i]);
            jb[i].addActionListener(ls);
        }
        jp[3].setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 0));
        this.setLayout(new GridLayout(0, 1, 0, 0));
        for (int i = 0; i < jp.length; i++) {
            this.add(jp[i]);
        }
    }

    private ImageIcon getIcon(String path) {
        ImageIcon logo = new ImageIcon(getClass().getResource(path));
        //图片压缩，只需改动20与15自行设置
        Image image = logo.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    private Image getImage(String path) {
        ImageIcon logo = new ImageIcon(getClass().getResource(path));
        //图片压缩，只需改动20与15自行设置
        Image image = logo.getImage().getScaledInstance(logo.getIconWidth(), logo.getIconHeight(), Image.SCALE_SMOOTH);
        return image;
    }
}
