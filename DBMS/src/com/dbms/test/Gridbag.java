package com.dbms.test;

import com.dbms.operator.OperatorImp;

import javax.swing.*;
import java.awt.*;

class testGridbag extends JFrame {
    public testGridbag() {
        //设置标题
        this.setTitle("DBMS Demo");
        /**
         * 设置布局，然后分别添加每个palel的组件
         */
        //
        this.setSize(450,300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.setBackground(Color.blue);
        /**
         * 对窗体进行布局
         */
        GridBagLayout gridBagLayout=new GridBagLayout(); //实例化布局对象
        this.setLayout(gridBagLayout);                     //jf窗体对象设置为GridBagLayout布局
        GridBagConstraints gridBagConstraints=new GridBagConstraints();//实例化这个对象用来对组件进行管理
        gridBagConstraints.fill=GridBagConstraints.BOTH;//该方法是为了设置如果组件所在的区域比组件本身要大时的显示情况
        //NONE：不调整组件大小。
        //HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
        //VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
        //BOTH：使组件完全填满其显示区域。
        //组件1(gridx,gridy)组件的左上角坐标，gridwidth，gridheight：组件占用的网格行数和列数
        JPanel jp1=new JPanel();
        /**
         * 设置每个面板的布局
         */
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=0;
        gridBagConstraints.gridwidth=200;
        gridBagConstraints.gridheight=100;
        gridBagConstraints.weightx=1;
        gridBagConstraints.weighty=1;

        gridBagLayout.setConstraints(jp1, gridBagConstraints);
        //
        this.add(jp1);
        jp1.setBackground(Color.orange);
        //
        //
        this.setVisible(true);
        //设置初始大小和初始位置
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((scrSize.width - this.getWidth()) / 2, (scrSize.height - this.getHeight()) / 2);
    }

//    private void addLayout(JPanel jPanel) {
//
//    }
}

public class Gridbag {
    public static void main(String[] args) {
        new testGridbag();
    }
}