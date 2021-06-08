package com.dbms.view;

import com.dbms.jdbc.JDBCUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
                new JLabel("服务器类型:"),
                new JLabel("服务器名称:"),
                new JLabel("身份登录:"),
                new JLabel("登录名:"),
                new JLabel("密码:"),
        };
        JTextField[] jtf = new JTextField[]{
                new JTextField(14),
                new JTextField(14),
                new JPasswordField(14),
        };
        jtf[0].setText("SQL Sever");
        jtf[0].setEditable(false);
        String[] jcb_ = {"身份验证", "Analysis services", "Reporting Services", "Integration Services"};
        String[] jcb2_ = {"SQL Sever 身份验证", "Windows身份验证", "Azure Active Dirctory"};
        JComboBox jcb = new JComboBox(jcb_);
        JComboBox jcb2 = new JComboBox(jcb2_);
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
                new JPanel(),
                new JPanel(),
        };
        jp[0].add(jl[0]);
        jp[0].add(jcb);
        jp[1].add(jl[1]);
        jp[1].add(jtf[0]);
        jp[2].add(jl[2]);
        jp[2].add(jcb2);
        jp[3].add(jl[3]);
        jp[3].add(jtf[1]);
        jp[4].add(jl[4]);
        jp[4].add(jtf[2]);
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
                    String username = jtf[1].getText();
                    String password = jtf[2].getText();
                    if (new JDBCUtils(username, password).getConnection() != null) {
                        Login.this.setVisible(false);
                        new DBMSWindow(username, password);
                    } else {
                        JOptionPane.showMessageDialog(null, "账号密码有误,请重新输入", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (e.getSource() == jb[1]) {
                    jtf[1].setText("");
                    jtf[2].setText("");
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
            jp[5].add(jb[i]);
            /**
             * 添加时间监听器
             */
            jb[i].addActionListener(ls);
        }
        jp[5].setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 0));
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
