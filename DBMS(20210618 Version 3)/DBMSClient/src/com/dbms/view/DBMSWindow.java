package com.dbms.view;

import com.dbms.data.Data;
import com.dbms.data.Pair;
import com.dbms.log.Log;
import com.dbms.operator.OperatorImp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Formatter;

public class DBMSWindow extends JFrame {
    private static OperatorImp op;
    private GridBagLayout gridBagLayout;
    private GridBagConstraints gridBagConstraints;
    private JTextArea 控制台 = null, 查询面板 = null;
    private long startTime, endTime;
    Log log = new Log();

    public DBMSWindow(String username, String password) {
        op = new OperatorImp(username, password);
        //设置标题
        this.setTitle("DBMS Demo");
        //设置logo
        this.setIconImage(getImage("icon\\logo.png"));   //设置窗口的logo
        /**
         * 设置布局，然后分别添加每个palel的组件
         */
        JPanel[] jPanels = new JPanel[]{
                new JPanel(),
                new JPanel(),
                new JPanel(),
                new JPanel() {
                    //1024*683
                    ImageIcon imageIcon = new ImageIcon(getClass().getResource("icon\\bg1.jpg"));
                    Image img = imageIcon.getImage().getScaledInstance(1300, 730, Image.SCALE_SMOOTH);
                    private static final long serialVersionUID = 1957203784117943458L;

                    {
                        this.setOpaque(false);
                    }

                    public void paintComponent(Graphics g) {
                        g.drawImage(img, 0, 0, this);
                        super.paintComponents(g);
                    }
                },
                new JPanel(),
        };
        InitLayout();
        addLayout(jPanels);
        //添加菜单
        addMenu();
        add工具栏(jPanels[0]);
        add视图(jPanels[1]);
        add按钮(jPanels[2]);
        add面板(jPanels[3]);
        addAction23(jPanels[2], jPanels[3]);
        add控制台(jPanels[4]);
        //设置可见
        this.setVisible(true);
        //设置初始大小和初始位置
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
//        this.pack();
        this.setMinimumSize(new Dimension(1000, 750));
        this.setLocation((scrSize.width - this.getWidth()) / 2, (scrSize.height - this.getHeight()) / 2);
    }

    private void InitLayout() {
        gridBagLayout = new GridBagLayout(); //实例化布局对象
        gridBagConstraints = new GridBagConstraints();//实例化这个对象用来对组件进行管理
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0;
    }

    private void addMenu() {
        JMenuBar jMenuBar = new JMenuBar();
        this.getRootPane().setJMenuBar(jMenuBar);
        //添加一级菜单
        JMenu[] jMenu = new JMenu[]{
                new JMenu("文件"),
                new JMenu("编辑"),
                new JMenu("视图"),
                new JMenu("查询"),
                new JMenu("项目"),
                new JMenu("Debug"),
                new JMenu("SQL Complete"),
                new JMenu("工具"),
                new JMenu("窗口"),
                new JMenu("帮助")
        };
        for (int i = 0; i < jMenu.length; i++) {
            jMenuBar.add(jMenu[i]);
        }
        //添加二级菜单
        add文件(jMenu[0]);
        add编辑(jMenu[1]);
        add视图(jMenu[2]);
        add查询(jMenu[3]);
        add项目(jMenu[4]);
        addDebug(jMenu[5]);
        addSQL_Complete(jMenu[6]);
        add工具(jMenu[7]);
        add窗口(jMenu[8]);
        add帮助(jMenu[9]);
    }

    private void add文件(JMenu parentMenu) {
        JMenuItem[] items = {
                new JMenuItem("连接对象资源管理器"),
                new JMenuItem("断开与对象资源管理器的连接"),
                new JMenuItem("关闭"),
                new JMenuItem("关闭解决方案"),
                new JMenuItem("保存"),
                new JMenuItem("全部保存"),
                new JMenuItem("在浏览器中查看"),
                new JMenuItem("使用以下工具浏览"),
                new JMenuItem("页面设置"),
                new JMenuItem("打印"),
                new JMenuItem("退出")
        };
        JMenu[] menus = {
                new JMenu("新建"),
                new JMenu("打开"),
                new JMenu("添加"),
                new JMenu("最近使用过的文件"),
                new JMenu("最近使用过的项目和解决方案")
        };
        //
        items[0].setIcon(getIcon("icon\\三明治.png"));
        items[4].setIcon(getIcon("icon\\匹萨.png"));
        items[5].setIcon(getIcon("icon\\吐司.png"));
        items[7].setIcon(getIcon("icon\\咖啡.png"));
        items[9].setIcon(getIcon("icon\\啤酒.png"));
        items[10].setIcon(getIcon("icon\\圣女果.png"));
        for (int i = 0; i < items.length; i++) {
            //wenjian[i].setEnabled(false);               //变灰 刚开始全部不能用
            if (i == 2) {
                parentMenu.addSeparator();
                for (int j = 0; j < 3; j++) {
                    if (j == 2) parentMenu.addSeparator();
                    parentMenu.add(menus[j]);
                }
                parentMenu.addSeparator();
            }
            if (i == 9) {
                parentMenu.addSeparator();
                for (int j = 3; j < menus.length; j++) {
                    parentMenu.add(menus[j]);
                }
                parentMenu.addSeparator();
            }
            if (i == 4 || i == 6) {
                parentMenu.addSeparator();
            }
            parentMenu.add(items[i]);
        }
        /**
         * 新建
         */
        JMenuItem[] xinjian = new JMenuItem[]{
                new JMenuItem("项目"),
                new JMenuItem("文件"),
                new JMenuItem("使用当前连接的查询"),
                new JMenuItem("数据库引擎查询"),
                new JMenuItem("Analysis Services MDX查询"),
                new JMenuItem("Analysis Services DMX查询"),
                new JMenuItem("Analysis Services XMLA查询"),
                new JMenuItem("Analysis Services DAX查询"),
                new JMenuItem("策略")
        };
        xinjian[0].setIcon(getIcon("icon\\奇异果.png"));
        xinjian[1].setIcon(getIcon("icon\\奇异果.png"));
        xinjian[2].setIcon(getIcon("icon\\布丁.png"));
        xinjian[3].setIcon(getIcon("icon\\布丁.png"));
        xinjian[4].setIcon(getIcon("icon\\开心果.png"));
        xinjian[5].setIcon(getIcon("icon\\开心果.png"));
        xinjian[6].setIcon(getIcon("icon\\开心果.png"));
        xinjian[7].setIcon(getIcon("icon\\开心果.png"));
        for (int i = 0; i < xinjian.length; i++) {
            if (i == 2 || i == 8)
                menus[0].addSeparator();
            menus[0].add(xinjian[i]);
        }
        /**
         * 打开
         */
        JMenuItem[] dakai = new JMenuItem[]{
                new JMenuItem("项目/解决方案"),
                new JMenuItem("文件夹"),
                new JMenuItem("合并拓展事件文件"),
                new JMenuItem("合并审核文件"),
                new JMenuItem("Vulnerability Assessment"),
                new JMenuItem("文件"),
                new JMenuItem("Analysis Services 数据库"),
                new JMenuItem("使用新建连接的文件"),
                new JMenuItem("断开连接的文件"),
                new JMenuItem("策略")
        };
        dakai[0].setIcon(getIcon("icon\\意面.png"));
        dakai[1].setIcon(getIcon("icon\\杯子蛋糕.png"));
        dakai[5].setIcon(getIcon("icon\\果汁.png"));
        dakai[6].setIcon(getIcon("icon\\柠檬.png"));
        dakai[7].setIcon(getIcon("icon\\棒棒糖.png"));
        dakai[8].setIcon(getIcon("icon\\棒棒糖.png"));
        for (int i = 0; i < dakai.length; i++) {
            if (i == 5 || i == 6 || i == 7 || i == 9)
                menus[1].addSeparator();
            menus[1].add(dakai[i]);
        }
        /**
         * 添加
         */
        JMenuItem[] tianjia = new JMenuItem[]{
                new JMenuItem("   新建项目   "),
                new JMenuItem("   现有项目   "),
        };
        tianjia[0].setIcon(getIcon("icon\\爱心.png"));
        tianjia[1].setIcon(getIcon("icon\\爱心.png"));
        for (int i = 0; i < tianjia.length; i++) {
            if (i == 1) menus[2].addSeparator();
            menus[2].add(tianjia[i]);
        }
        /**
         * 后两个
         */
        JMenuItem zuijin1 = new JMenuItem("   没有最近使用过的文件   ");
        zuijin1.setIcon(getIcon("icon\\爱心.png"));
        zuijin1.setEnabled(false);
        menus[3].add(zuijin1);
        //
        JMenuItem zuijin2 = new JMenuItem("   没有最近的项目或解决方案   ");
        zuijin2.setIcon(getIcon("icon\\爱心.png"));
        zuijin2.setEnabled(false);
        menus[4].add(zuijin2);
    }

    private void add编辑(JMenu parentMenu) {
        JMenuItem[] items = {
                new JMenuItem("撤销"),
                new JMenuItem("重做"),
                new JMenuItem("剪切"),
                new JMenuItem("复制"),
                new JMenuItem("粘贴"),
                new JMenuItem("删除"),
                new JMenuItem("全选")
        };
        JMenu[] menus = {
                new JMenu("转到"),
                new JMenu("查找和替换"),
                new JMenu("书签")
        };
        items[0].setIcon(getIcon("icon\\撤销.png"));
        items[1].setIcon(getIcon("icon\\重做.png"));
        items[2].setIcon(getIcon("icon\\剪切.png"));
        items[3].setIcon(getIcon("icon\\复制.png"));
        items[4].setIcon(getIcon("icon\\粘贴.png"));
        items[5].setIcon(getIcon("icon\\删除.png"));
        items[6].setIcon(getIcon("icon\\全选.png"));
        //
        parentMenu.add(menus[0]);
        parentMenu.add(menus[1]);
        parentMenu.addSeparator();
        for (int i = 0; i < items.length; i++) {
            if (i == 2 || i == 6)
                parentMenu.addSeparator();
            parentMenu.add(items[i]);
        }
        parentMenu.addSeparator();
        parentMenu.add(menus[2]);
        /**
         * 转到
         */
        JMenuItem[] zhuandao = new JMenuItem[]{
                new JMenuItem("转到"),
                new JMenuItem("转到所有"),
                new JMenuItem("转到文件"),
                new JMenuItem("转到最近的文件"),
                new JMenuItem("转到类型"),
                new JMenuItem("转至成员"),
                new JMenuItem("转至符号"),
                new JMenuItem("转到上一个编辑的位置")
        };
        for (int i = 0; i < zhuandao.length; i++) {
            zhuandao[i].setIcon(getIcon("icon\\white.png"));
            if (i == 7)
                menus[0].addSeparator();
            menus[0].add(zhuandao[i]);
        }
        /**
         * 查找和替换
         */
        JMenuItem[] chazhao = new JMenuItem[]{
                new JMenuItem("快速查找"),
                new JMenuItem("快速替换"),
                new JMenuItem("在文件中查找"),
                new JMenuItem("在文件中替换")
        };
        chazhao[0].setIcon(getIcon("icon\\樱桃.png"));
        chazhao[1].setIcon(getIcon("icon\\汉堡.png"));
        chazhao[2].setIcon(getIcon("icon\\热狗.png"));
        chazhao[3].setIcon(getIcon("icon\\爆米花.png"));
        for (int i = 0; i < chazhao.length; i++) {
            menus[1].add(chazhao[i]);
        }
        /**
         * 书签
         */
        JMenuItem[] shuqian = new JMenuItem[]{
                new JMenuItem("切换书签"),
                new JMenuItem("启用所有书签"),
                new JMenuItem("启用书签"),
                new JMenuItem("上一书签"),
                new JMenuItem("下一书签"),
                new JMenuItem("清楚书签"),
                new JMenuItem("文件夹的上一书签"),
                new JMenuItem("文件夹的下一书签"),
                new JMenuItem("文档中额上一个书签"),
                new JMenuItem("文档中的下一个书签")
        };
        shuqian[0].setIcon(getIcon("icon\\T恤.png"));
        shuqian[1].setIcon(getIcon("icon\\休闲裤.png"));
        shuqian[2].setIcon(getIcon("icon\\内衣裤.png"));
        shuqian[3].setIcon(getIcon("icon\\半裙.png"));
        shuqian[4].setIcon(getIcon("icon\\卫衣.png"));
        shuqian[5].setIcon(getIcon("icon\\围巾.png"));
        shuqian[6].setIcon(getIcon("icon\\开衫.png"));
        shuqian[7].setIcon(getIcon("icon\\手套.png"));
        shuqian[8].setIcon(getIcon("icon\\挎包.png"));
        shuqian[9].setIcon(getIcon("icon\\旅行箱.png"));

        for (int i = 0; i < shuqian.length; i++) {
            menus[2].add(shuqian[i]);
        }
    }

    private void add视图(JMenu parentMenu) {
        JMenuItem[] items = {
                new JMenuItem("打开"),
                new JMenuItem("打开方式"),
                new JMenuItem("对象资源管理器"),
                new JMenuItem("对象资源管理器详细信息"),
                new JMenuItem("已注册的服务器"),
                new JMenuItem("模板资源管理器"),
                new JMenuItem("解决方案资源管理器"),
                new JMenuItem("书签窗口"),
                new JMenuItem("错误列表"),
                new JMenuItem("输出"),
                new JMenuItem("工具箱"),
                new JMenuItem("全屏幕"),
                new JMenuItem("所有窗口"),
                new JMenuItem("向后导航"),
                new JMenuItem("向前导航"),
                new JMenuItem("下一个导航"),
                new JMenuItem("上一个导航"),
                new JMenuItem("属性窗口"),
                new JMenuItem("属性页"),
        };
        JMenu[] menus = {
                new JMenu("查找结果"),
                new JMenu("其他窗口"),
                new JMenu("工具栏"),
        };
        items[0].setIcon(getIcon("icon\\板鞋.png"));
        items[2].setIcon(getIcon("icon\\棉袜.png"));
        items[3].setIcon(getIcon("icon\\毛衣.png"));
        items[4].setIcon(getIcon("icon\\热裤.png"));
        items[5].setIcon(getIcon("icon\\衬衣.png"));
        items[6].setIcon(getIcon("icon\\西装外套.png"));
        items[7].setIcon(getIcon("icon\\连衣裙.png"));
        items[8].setIcon(getIcon("icon\\高跟鞋.png"));
        items[9].setIcon(getIcon("icon\\鸭舌帽.png"));
        items[10].setIcon(getIcon("icon\\眼镜.png"));
        items[11].setIcon(getIcon("icon\\牛排.png"));
        items[12].setIcon(getIcon("icon\\章鱼小丸子.png"));
        items[13].setIcon(getIcon("icon\\茶.png"));
        items[14].setIcon(getIcon("icon\\蓝莓.png"));
        items[17].setIcon(getIcon("icon\\薯条.png"));

        for (int i = 0; i < items.length; i++) {
            if (i == 2 || i == 6 || i == 7 || i == 8 || i == 13 || i == 17)
                parentMenu.addSeparator();
            if (i == 11) {
                for (int j = 0; j < menus.length; j++) {
                    if (j == 2) parentMenu.addSeparator();
                    parentMenu.add(menus[j]);
                }
            }
            parentMenu.add(items[i]);
        }
        /**
         * 查找结果
         */
        JMenuItem[] 查找结果 = new JMenuItem[]{
                new JMenuItem("查找结果1"),
                new JMenuItem("查找结果2"),
                new JMenuItem("查找符号结果"),
        };
        查找结果[0].setIcon(getIcon("icon\\查找.png"));
        查找结果[1].setIcon(getIcon("icon\\查找1.png"));
        查找结果[2].setIcon(getIcon("icon\\查找2.png"));
        for (int i = 0; i < 3; i++) {
            menus[0].add(查找结果[i]);
        }
        /**
         * 其他窗口
         */
        JMenuItem[] 其他窗口 = new JMenuItem[]{
                new JMenuItem("命令窗口"),
                new JMenuItem("web浏览器"),
                new JMenuItem("文档大纲"),
                new JMenuItem("资源视图"),
        };
        其他窗口[0].setIcon(getIcon("icon\\蛋糕.png"));
        其他窗口[1].setIcon(getIcon("icon\\鸡腿.png"));
        其他窗口[2].setIcon(getIcon("icon\\beach.png"));
        其他窗口[3].setIcon(getIcon("icon\\bridge.png"));
        for (int i = 0; i < 4; i++) {
            if (i == 2) menus[1].addSeparator();
            menus[1].add(其他窗口[i]);
        }
        /**
         * 工具栏
         */
        JMenuItem[] 工具栏 = new JMenuItem[]{
                new JMenuItem("Compare Files"),
                new JMenuItem("Debug"),
                new JMenuItem("SQL Sever Analysis Services 编辑器"),
                new JMenuItem("SQL 编辑器"),
                new JMenuItem("Web编辑器"),
                new JMenuItem("XML编辑器"),
                new JMenuItem("布局"),
                new JMenuItem("拓展事件"),
                new JMenuItem("数据库关系图"),
                new JMenuItem("文本编辑器"),
                new JMenuItem("查询设计器"),
                new JMenuItem("标准"),
                new JMenuItem("生成"),
                new JMenuItem("表设计器"),
                new JMenuItem("视图设计器"),
                new JMenuItem("自定义"),
        };
        for (int i = 0; i < 工具栏.length; i++) {
            工具栏[i].setIcon(getIcon("icon\\white.png"));
        }
        for (int i = 0; i < 工具栏.length; i++) {
            if (i == 工具栏.length - 1) menus[2].addSeparator();
            menus[2].add(工具栏[i]);
        }
    }

    private void add查询(JMenu parentMenu) {
        JMenuItem[] items = {
                new JMenuItem("在对象资源管理器中打开服务器"),
                new JMenuItem("指定模板参数的值"),
                new JMenuItem("执行"),
                new JMenuItem("取消执行查询"),
                new JMenuItem("分析"),
                new JMenuItem("显示估计的执行计划"),
                new JMenuItem("IntelliSense已启用"),
                new JMenuItem("在SQL Sever Profiler 中跟踪查询"),
                new JMenuItem("在数据库引擎优化顾问中分析查询"),
                new JMenuItem("在编辑器中设计查询"),
                new JMenuItem("包括实际的执行计划"),
                new JMenuItem("包括实时查询统计信息"),
                new JMenuItem("包括客户端统计信息"),
                new JMenuItem("重置客户端统计信息"),
                new JMenuItem("SQL CMD模式"),
                new JMenuItem("查询选项"),
        };
        JMenu[] menus = {
                new JMenu("连接"),
                new JMenu("将结果保存到"),
        };
        parentMenu.add(menus[0]);
        parentMenu.addSeparator();
        for (int i = 0; i < items.length; i++) {
            if (i == 2 || i == 7 || i == 9 || i == 10 || i == 14 || i == 15)
                parentMenu.addSeparator();
            parentMenu.add(items[i]);
        }
        parentMenu.addSeparator();
        parentMenu.add(menus[1]);
        /**
         * 添加连接
         */
        JMenuItem[] 连接 = new JMenuItem[]{
                new JMenuItem("连接"),
                new JMenuItem("断开连接"),
                new JMenuItem("断开所有查询"),
                new JMenuItem("更改连接"),
        };
        for (int i = 0; i < 连接.length; i++) {
            menus[0].add(连接[i]);
        }
        /**
         * 添加将结果保存为
         */
        JMenuItem[] 结果 = new JMenuItem[]{
                new JMenuItem("以文本显示结果"),
                new JMenuItem("以网格显示结果"),
                new JMenuItem("将结果保存到文件"),
        };
        for (int i = 0; i < 结果.length; i++) {
            menus[1].add(结果[i]);
        }
        /**
         * 添加图标
         */
        menus[1].setIcon(getIcon("icon\\cape.png"));
        items[1].setIcon(getIcon("icon\\castle.png"));
        items[2].setIcon(getIcon("icon\\cityscape.png"));
        items[3].setIcon(getIcon("icon\\desert-1.png"));
        items[4].setIcon(getIcon("icon\\fields.png"));
        items[5].setIcon(getIcon("icon\\fields-1.png"));
        items[6].setIcon(getIcon("icon\\forest.png"));
        items[7].setIcon(getIcon("icon\\hills.png"));
        items[8].setIcon(getIcon("icon\\home.png"));
        items[9].setIcon(getIcon("icon\\home-1.png"));
        items[10].setIcon(getIcon("icon\\iceberg.png"));
        items[11].setIcon(getIcon("icon\\island.png"));
        items[12].setIcon(getIcon("icon\\mill.png"));
        items[14].setIcon(getIcon("icon\\mountains.png"));
        连接[0].setIcon(getIcon("icon\\mountains-1.png"));
        连接[1].setIcon(getIcon("icon\\nuclear-plant.png"));
        连接[3].setIcon(getIcon("icon\\river.png"));
        结果[0].setIcon(getIcon("icon\\ruins.png"));
        结果[1].setIcon(getIcon("icon\\sea.png"));
        结果[2].setIcon(getIcon("icon\\spruce.png"));
    }

    private void add项目(JMenu parentMenu) {
        JMenuItem[] items = {
                new JMenuItem("从项目中排除"),
                new JMenuItem("显示所有文件"),
                new JMenuItem("杂项文件 属性"),
        };
        for (int i = 0; i < items.length; i++) {
            if (i == 2) parentMenu.addSeparator();
            parentMenu.add(items[i]);
        }
        /**
         * 添加图标
         */
        items[1].setIcon(getIcon("icon\\trees.png"));
        items[2].setIcon(getIcon("icon\\village.png"));
    }

    private void addDebug(JMenu parentMenu) {
        JMenuItem[] items = {
                new JMenuItem("Start"),
                new JMenuItem("Step Into"),
                new JMenuItem("Step Over"),
                new JMenuItem("Toggle Breakpoint"),
                new JMenuItem("Delete All Breakpoint"),
        };
        JMenu menus = new JMenu("Windows");
        parentMenu.add(menus);
        for (int i = 0; i < items.length; i++) {
            if (i == 1 || i == 3) parentMenu.addSeparator();
            parentMenu.add(items[i]);
        }
        JMenuItem[] Windows = new JMenuItem[]{
                new JMenuItem("Breakpoints"),
                new JMenuItem("Output"),
        };
        menus.add(Windows[0]);
        menus.add(Windows[1]);
        /**
         * 添加图标
         */
        items[1].setIcon(getIcon("icon\\trees.png"));
        items[2].setIcon(getIcon("icon\\village.png"));
        items[4].setIcon(getIcon("icon\\waterfall.png"));
        Windows[0].setIcon(getIcon("icon\\waterfall-1.png"));
        Windows[1].setIcon(getIcon("icon\\windmills.png"));
    }

    /**
     * 为了简单起见,省却图标
     * 并且不进行二级菜单的设置,因为重复性的工作真的累啊!!!!!!!
     *
     * @param parentMenu
     */
    private void addSQL_Complete(JMenu parentMenu) {
        JMenuItem[] items = {
                new JMenuItem("Disable Code Completion"),
                new JMenuItem("刷新本地缓存"),
                new JMenuItem("Reset Suggestoins Cache"),
                new JMenuItem("Format Document"),
                new JMenuItem("Format Selection"),
                new JMenuItem("SQL Formatter"),
                new JMenuItem("Insert Semicolons"),
                new JMenuItem("Rename"),
                new JMenuItem("Go To Definition"),
                new JMenuItem("Jump Between Syntax Pairs"),
                new JMenuItem("Execute Current Statement"),
                new JMenuItem("Execute To Cursor"),
                new JMenuItem("Execution History"),
                new JMenuItem("Show Data Viewer"),
                new JMenuItem("Documents Sessions"),
                new JMenuItem("Document Outline"),
                new JMenuItem("Restore Last Closed Document"),
                new JMenuItem("Options"),
                new JMenuItem("Import and Export Settings"),
                new JMenuItem("Snippets Manager..."),
                new JMenuItem("Find Invalid Objects"),
        };
        JMenu[] menus = new JMenu[]{
                new JMenu("Recently Closed Documents"),
                new JMenu("Trace"),
                new JMenu("Help"),
        };
        for (int i = 0; i < items.length; i++) {
            if (i == 18) parentMenu.add(menus[0]);
            if (i == 1 || i == 3 || i == 12 || i == 16 || i == 18 || i == 20 || i == 21 || i == 22)
                parentMenu.addSeparator();
            parentMenu.add(items[i]);
        }
        parentMenu.add(menus[1]);
        parentMenu.add(menus[2]);
        /**
         * 添加图标
         */
        for (int i = 0; i < items.length; i++) {
            items[i].setIcon(getIcon("icon\\white.png"));
        }
    }

    private void add工具(JMenu parentMenu) {
        JMenuItem[] items = {
                new JMenuItem("SQL Sever Profiler"),
                new JMenuItem("数据库引擎优化顾问"),
                new JMenuItem("Azure Data Studio"),
                new JMenuItem("代码片段管理器"),
                new JMenuItem("外部工具"),
                new JMenuItem("检查更新"),
                new JMenuItem("导入和导出设置"),
                new JMenuItem("自定义"),
                new JMenuItem("选项"),
        };
        JMenu menus = new JMenu("迁移到Azure");
        for (int i = 0; i < items.length; i++) {
            if (i == 3) parentMenu.add(menus);
            if (i >= 3 && i <= 5)
                parentMenu.addSeparator();
            parentMenu.add(items[i]);
        }
        /**
         * 添加图标
         */
        for (int i = 0; i < items.length - 1; i++) {
            items[i].setIcon(getIcon("icon\\white.png"));
        }
        items[items.length - 1].setIcon(getIcon("icon\\设置.png"));
    }

    private void add窗口(JMenu parentMenu) {
        JMenuItem[] items = {
                new JMenuItem("下一个窗格"),
                new JMenuItem("上一个窗格"),
                new JMenuItem("显示结果窗格"),
                new JMenuItem("新建窗口"),
                new JMenuItem("拆分"),
                new JMenuItem("浮动"),
                new JMenuItem("停靠"),
                new JMenuItem("自动隐藏"),
                new JMenuItem("固定选项卡"),
                new JMenuItem("保存窗口布局"),
                new JMenuItem("应用窗口布局"),
                new JMenuItem("重置窗口布局"),
                new JMenuItem("显示边栏选项卡"),
                new JMenuItem("关闭所有文档"),
                new JMenuItem("关闭除工具窗口外的所有窗口"),
        };
        JMenu menus = new JMenu("管理窗口布局");
        for (int i = 0; i < items.length; i++) {
            if (i == 12) parentMenu.add(menus);
            if (i == 3 || i == 5 || i == 8 || i == 9 || i == 12)
                parentMenu.addSeparator();
            parentMenu.add(items[i]);
        }
        /**
         * 添加图标
         */
        for (int i = 0; i < items.length - 1; i++) {
            items[i].setIcon(getIcon("icon\\white.png"));
        }
    }

    private void add帮助(JMenu parentMenu) {
        JMenuItem[] items = {
                new JMenuItem("添加和删除帮助内容"),
                new JMenuItem("技术支持"),
                new JMenuItem("联机隐私声明"),
                new JMenuItem("查看帮助"),
                new JMenuItem("社区项目和示例"),
                new JMenuItem("资源中心"),
                new JMenuItem("关于"),
        };
        JMenu menus = new JMenu("设置帮助首选项");
        for (int i = 0; i < items.length; i++) {
            if (i == 1) parentMenu.add(menus);
            if (i == 1 || i == 3 || i == 6)
                parentMenu.addSeparator();
            parentMenu.add(items[i]);
        }
        /**
         * 添加图标
         */
        for (int i = 0; i < items.length - 1; i++) {
            if (i == 1 || i == 3) items[i].setIcon(getIcon("icon\\帮助.png"));
            else items[i].setIcon(getIcon("icon\\white.png"));
        }
    }

    private void addLayout(JPanel[] jPanels) {
        GridBagLayout gridBagLayout = new GridBagLayout(); //实例化布局对象
        this.setLayout(gridBagLayout);                     //jf窗体对象设置为GridBagLayout布局
        GridBagConstraints gridBagConstraints = new GridBagConstraints();//实例化这个对象用来对组件进行管理
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        //NONE：不调整组件大小。
        //HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
        //VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
        //BOTH：使组件完全填满其显示区域。
        //组件1(gridx,gridy)组件的左上角坐标，gridwidth，gridheight：组件占用的网格行数和列数
        /**
         * 设置每个面板的布局
         */
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0;
        gridBagLayout.setConstraints(jPanels[0], gridBagConstraints);
        //
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 0;
        gridBagConstraints.weighty = 1;
        gridBagLayout.setConstraints(jPanels[1], gridBagConstraints);
        //
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0;
        gridBagLayout.setConstraints(jPanels[2], gridBagConstraints);
        //
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagLayout.setConstraints(jPanels[3], gridBagConstraints);
        //
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0;
        gridBagLayout.setConstraints(jPanels[4], gridBagConstraints);
        /**
         * 添加组件
         */
        for (int i = 0; i < jPanels.length; i++) {
            this.add(jPanels[i]);
        }
//        jPanels[0].setBackground(Color.BLACK);
//        jPanels[1].setBackground(Color.BLUE);
//        jPanels[2].setBackground(Color.GRAY);
//        jPanels[3].setBackground(Color.orange);
//        jPanels[4].setBackground(Color.pink);
    }

    private void add工具栏(JPanel jPanel) {
        jPanel.setLayout(new GridLayout(1, 1, 0, 0));
        JToolBar jToolBar = new JToolBar();
        jToolBar.setMargin(new Insets(0, 0, 0, 10));
        jToolBar.setBorderPainted(false);
        jPanel.add(jToolBar);
        JButton[] jButtons = new JButton[]{
                new JButton(),
                new JButton(),
                new JButton(),
                new JButton(),
                new JButton(),
                new JButton(),
                new JButton(),
                new JButton(),
                new JButton(),
                new JButton(),
        };
        jButtons[0].setIcon(getIcon("icon\\打开.png"));
        jButtons[0].setToolTipText("打开");
        jButtons[1].setIcon(getIcon("icon\\保存.png"));
        jButtons[1].setToolTipText("保存");
        jButtons[2].setIcon(getIcon("icon\\重新加载.png"));
        jButtons[2].setToolTipText("重新加载");
        jButtons[3].setIcon(getIcon("icon\\后退.png"));
        jButtons[3].setToolTipText("后退");
        jButtons[4].setIcon(getIcon("icon\\前进.png"));
        jButtons[4].setToolTipText("前进");
        jButtons[5].setIcon(getIcon("icon\\执行.png"));
        jButtons[5].setToolTipText("执行");
        jButtons[6].setIcon(getIcon("icon\\停止.png"));
        jButtons[6].setToolTipText("停止");
        jButtons[7].setIcon(getIcon("icon\\清空.png"));
        jButtons[7].setToolTipText("清空");
        jButtons[8].setIcon(getIcon("icon\\设置.png"));
        jButtons[8].setToolTipText("设置");
        jButtons[9].setIcon(getIcon("icon\\搜索.png"));
        jButtons[9].setToolTipText("搜索");
        //
        for (int i = 0; i < jButtons.length; i++) {
            if (i == 3 || i == 5 || i == 8) {
                jToolBar.addSeparator();
            }
            jToolBar.add(jButtons[i]);
            jButtons[i].setFocusPainted(false);
            jButtons[i].setBorderPainted(false);
        }
        //添加事件监听
        ActionListener ls = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == jButtons[0]) {
                } else if (e.getSource() == jButtons[1]) {
                } else if (e.getSource() == jButtons[2]) {
                } else if (e.getSource() == jButtons[3]) {
                } else if (e.getSource() == jButtons[4]) {
                } else if (e.getSource() == jButtons[5]) {
                    startTime = System.nanoTime();
                    if (查询面板 == null || 查询面板.getText() == null) return;
                    String sql = 查询面板.getSelectedText();
                    if (sql == null) sql = 查询面板.getText();
                    sql = sql.toLowerCase();
                    ArrayList<String[]> data = op.execute(sql);
                    控制台.setText("");
                    boolean flag = true;
                    if (data.size() != 0) {
                        for (int j = 0; j < data.size(); j++) {
                            boolean f = false;
                            for (int i = 0; i < data.get(j).length; i++) {
                                if (data.get(j)[i] == "$fail") {
                                    flag = false;
                                    continue;
                                } else if (data.get(j)[i] == "$success") continue;
                                Formatter formatter = new Formatter();
                                formatter.format("%-25s", data.get(j)[i]);
                                控制台.append(formatter.toString());
                                f = true;
                            }
                            if (f) 控制台.append("\n");
                        }
                    }
                    endTime = System.nanoTime();
                    String message = op.getDate() + " 消息(查询面板): " + (flag == true ? "执行成功" : "执行失败") + " runtime:" + getRuntime() + "s";
                    控制台.append(message);
                    log.append(message);
                } else if (e.getSource() == jButtons[6]) {
                } else if (e.getSource() == jButtons[7]) {
                    if (查询面板 != null) {
                        查询面板.setText("");
                        查询面板.validate();
                        查询面板.repaint();
                    }
                } else if (e.getSource() == jButtons[8]) {
                } else if (e.getSource() == jButtons[9]) {
                }
            }
        };
        for (int i = 0; i < jButtons.length; i++) {
            jButtons[i].addActionListener(ls);
        }
    }

    private void add视图(JPanel jPanel) {
        //设置布局
        jPanel.setLayout(new GridLayout(0, 1, 0, 0));
        //添加工具栏
        JToolBar jToolBar = new JToolBar();
        jToolBar.setMargin(new Insets(0, 0, 0, 0));
        jToolBar.setBorderPainted(false);
        jToolBar.setOrientation(SwingConstants.VERTICAL);
        jToolBar.setLayout(gridBagLayout);                     //jf窗体对象设置为GridBagLayout布局
//        jToolBar.setBackground(Color.CYAN);
        jPanel.add(jToolBar);
        //欢迎Label
        JLabel welLabel = new JLabel("欢迎使用WK的DBMS");
        welLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welLabel.setFont(new Font("楷体", Font.PLAIN, 15));
        //主视图
        JButton User = new JButton("WANGKANG(DBMS1.0)");
        User.setBorderPainted(false);
        User.setIcon(getIcon("icon\\加.png"));
        User.setFocusPainted(false);
        //
        gridBagLayout.setConstraints(welLabel, gridBagConstraints);
        jToolBar.add(welLabel);
        //
        gridBagLayout.setConstraints(User, gridBagConstraints);
        jToolBar.add(User);
        //
        gridBagConstraints.weighty = 1;
        JScrollPane jsp = new JScrollPane();
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        gridBagLayout.setConstraints(jsp, gridBagConstraints);
        jToolBar.add(jsp);
        /**
         * 我好聪明，竟然想到使用空白标签来搞这个，哈哈哈哈哈哈啊哈哈
         */
        //数据库名称面板
        JPanel dbNamePanel = new JPanel();
        dbNamePanel.setVisible(false);
//        dbNamePanel.setBackground(Color.CYAN);
        dbNamePanel.setLayout(gridBagLayout);
        jsp.setViewportView(dbNamePanel);
        //定义事件监听器
        ActionListener ls1 = new ActionListener() {
            boolean flag1 = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (flag1) {
                    User.setIcon(getIcon("icon\\减.png"));
                    flag1 = false;
                    DBMSWindow.this.addDB(dbNamePanel);
                    dbNamePanel.setVisible(true);
                } else {
                    User.setIcon(getIcon("icon\\加.png"));
                    flag1 = true;
                    delete(dbNamePanel);
                    dbNamePanel.setVisible(false);
                }
            }
        };
        User.addActionListener(ls1);
    }

    private void addDB(JPanel jPanel) {
        ArrayList<String> dbName = op.getDB();
        JButton DB;
        JPanel jp;
        //
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0;
        //
        for (int i = 0; i < dbName.size(); i++) {
            DB = new JButton(dbName.get(i));
            gridBagLayout.setConstraints(DB, gridBagConstraints);
            jPanel.add(DB);
            DB.setFocusPainted(false);
            DB.setIcon(getIcon("icon\\数据库.png"));
            jp = new JPanel();
            jp.setVisible(false);
            gridBagLayout.setConstraints(jp, gridBagConstraints);
            jPanel.add(jp);
            /**
             * 添加事件监听器
             */
            JButton finalDB = DB;
            JPanel finalJp = jp;
            DB.addActionListener(new ActionListener() {
                boolean flag = true;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (flag) {
//                        finalDB.setIcon(getIcon("icon\\减.png"));
                        flag = false;
                        DBMSWindow.this.addTable(jPanel, finalJp, finalDB.getText());
                        finalJp.setVisible(true);
                    } else {
//                        finalDB.setIcon(getIcon("icon\\加.png"));
                        flag = true;
                        delete(finalJp);
                        finalJp.setVisible(false);
                    }
                    jPanel.validate();
                    jPanel.repaint();
                }
            });
        }
        /**
         * 添加空白标签
         */JLabel empty = new JLabel();
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagLayout.setConstraints(empty, gridBagConstraints);
        jPanel.add(empty);
        //
        jPanel.validate();
        jPanel.repaint();
    }

    private void addTable(JPanel faPanel, JPanel jPanel, String dbName) {
        ArrayList<String> tableName = op.getTable(dbName);
        JButton TB;
        JPanel jp;
        //
        jPanel.setLayout(gridBagLayout);
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 4;
        gridBagConstraints.weighty = 0;
        //
        for (int i = 0; i < tableName.size(); i++) {
            TB = new JButton(tableName.get(i));
            gridBagLayout.setConstraints(TB, gridBagConstraints);
            jPanel.add(TB);
            TB.setFocusPainted(false);
            TB.setIcon(getIcon("icon\\表格.png"));
            //
            jp = new JPanel();
            jp.setVisible(false);
            gridBagLayout.setConstraints(jp, gridBagConstraints);
            jPanel.add(jp);
            //
            JButton finalTB = TB;
            JPanel finalJp = jp;
            TB.addActionListener(new ActionListener() {
                boolean flag1 = true;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (flag1) {
//                        finalTB.setIcon(getIcon("icon\\减.png"));
                        flag1 = false;
                        DBMSWindow.this.addData(faPanel, jPanel, finalJp, dbName, finalTB.getText());
                        finalJp.setVisible(true);
                    } else {
//                        finalTB.setIcon(getIcon("icon\\加.png"));
                        flag1 = true;
                        delete(finalJp);
                        finalJp.setVisible(false);
                    }
                    jPanel.validate();
                    jPanel.repaint();
                    faPanel.validate();
                    faPanel.repaint();
                }
            });
        }
        JLabel empty = new JLabel();
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagLayout.setConstraints(empty, gridBagConstraints);
        jPanel.add(empty);
        //
        jPanel.validate();
        jPanel.repaint();
    }

    private void addData(JPanel fafaPanel, JPanel faPanel, JPanel jPanel, String dbName, String tableName) {
        ArrayList<Pair> Data = op.getData(dbName, tableName);
        JLabel jLabel;
        Pair pair;
        //
        jPanel.setLayout(gridBagLayout);
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0;
        //
        for (int i = 0; i < Data.size(); i++) {
            pair = Data.get(i);
            jLabel = new JLabel(pair.getFirst() + "(" + pair.getSecond() + ")", JLabel.CENTER);
            gridBagLayout.setConstraints(jLabel, gridBagConstraints);
            jPanel.add(jLabel);
        }
        JLabel empty = new JLabel();
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagLayout.setConstraints(empty, gridBagConstraints);
        jPanel.add(empty);
        //
        jPanel.validate();
        jPanel.repaint();
        faPanel.validate();
        faPanel.repaint();
        fafaPanel.validate();
        fafaPanel.repaint();
    }

    private void add按钮(JPanel jPanel) {
        JButton 可视化 = new JButton("可视化管理");
        JButton 查询 = new JButton("查询");
        可视化.setIcon(getIcon("icon\\可视化管理.png"));
        可视化.setFocusPainted(false);
        查询.setIcon(getIcon("icon\\查询.png"));
        查询.setFocusPainted(false);
        jPanel.add(可视化);
        jPanel.add(查询);
        //添加边框
        jPanel.setBorder(BorderFactory.createEtchedBorder());
    }

    private void add面板(JPanel jPanel) {
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.gridheight = 0;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        jPanel.setLayout(gridBagLayout);
        JTextField jtf = new JTextField();
        jtf.setEditable(false);
        jtf.setText("开始你的WK的DBMS1.0的使用吧!");
        jtf.setHorizontalAlignment(SwingConstants.CENTER);
        jtf.setFont(new Font("楷体", Font.PLAIN, 25));
        gridBagLayout.setConstraints(jtf, gridBagConstraints);
        jPanel.add(jtf);
    }

    private void addAction23(JPanel jPanel1, JPanel jPanel2) {
        JButton 可视化管理 = (JButton) jPanel1.getComponent(0);
        JButton 查询 = (JButton) jPanel1.getComponent(1);
        ActionListener ls = new ActionListener() {
            int flag = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == 可视化管理) {
                    if (flag != 1) {
                        add操作按钮(jPanel2);
                        jPanel2.validate();
                        jPanel2.repaint();
                        flag = 1;
                    }
                } else if (e.getSource() == 查询) {
                    if (flag != 2) {
                        DBMSWindow.this.add查询面板(jPanel2);
                        jPanel2.validate();
                        jPanel2.repaint();
                        flag = 2;
                    }
                }
            }
        };
        可视化管理.addActionListener(ls);
        查询.addActionListener(ls);
    }

    private void add控制台(JPanel jPanel) {
        //设置布局
        jPanel.setLayout(new GridLayout(0, 1, 0, 0));
        //
        控制台 = new JTextArea();
//        控制台.setVisible(false);
        控制台.setBorder(BorderFactory.createEtchedBorder());
        控制台.setEditable(false);
        jPanel.add(控制台);
    }

    private void add操作按钮(JPanel jPanel) {
        jPanel.removeAll();
        jPanel.setLayout(new GridLayout(0, 2, 200, 25));
        JButton[] jButtons = new JButton[]{
                new JButton("建库"),
                new JButton("删库"),
                new JButton("建表"),
                new JButton("删表"),
                new JButton("插入数据"),
                new JButton("删除数据"),
                new JButton("更新数据"),
                new JButton("查询整表"),
                new JButton("简单条件查询"),
        };
        ActionListener ls = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == jButtons[0]) {
                    String dbName = "";
                    boolean f = false;
                    if ((dbName = JOptionPane.showInputDialog("请输入要建立的数据库名称:")) != null && dbName != "") {
                        startTime = System.nanoTime();
                        f = op.createDB(dbName);
                        endTime = System.nanoTime();
                    }
                    String message = op.getDate() + "消息0:" + dbName + ((f == true) ? "建库成功" : "建库失败") + "  runtime:" + getRuntime() + "s";
                    控制台.setText(message);
                    log.append(message);
                } else if (e.getSource() == jButtons[1]) {
                    String dbName = "";
                    boolean f = false;
                    if ((dbName = JOptionPane.showInputDialog("请输入要删除的数据库名称:")) != null && dbName != "") {
                        startTime = System.nanoTime();
                        f = op.deleteDB(dbName);
                        endTime = System.nanoTime();
                    }
                    String message = op.getDate() + "消息1:" + dbName + ((f == true) ? "删库成功" : "删库失败") + "  runtime:" + getRuntime() + "s";
                    控制台.setText(message);
                    log.append(message);
                } else if (e.getSource() == jButtons[2]) {
                    String dbName = "";
                    String tableName = "";
                    String s;
                    String pKey;
                    boolean f = false;
                    if ((dbName = JOptionPane.showInputDialog("请输入数据库名称:")) != null && dbName != "" &&
                            (tableName = JOptionPane.showInputDialog("请输入要新建表名称:")) != null && tableName != "" &&
                            (s = JOptionPane.showInputDialog("请输入列名及数据类型(如uid, int, uName char(20)):")) != null && s != "") {
                        startTime = System.nanoTime();
                        String[] s1 = s.split(",");
                        Data data = new Data();
                        for (int i = 0; i < s1.length; i += 2) {
                            data.addData(s1[i], s1[i + 1]);
                        }
                        f = op.createTable(dbName, tableName, data);
                        endTime=System.nanoTime();
                    }
                    String message = op.getDate() + "消息2:" + tableName + ((f == true) ? "建表成功" : "建表失败") + "  runtime:" + getRuntime() + "s";
                    控制台.setText(message);
                    log.append(message);
                } else if (e.getSource() == jButtons[3]) {
                    String dbName;
                    String tableName = "";
                    String s;
                    boolean f = false;
                    if ((dbName = JOptionPane.showInputDialog("请输入数据库名称:")) != null && dbName != "" &&
                            (tableName = JOptionPane.showInputDialog("请输入要删除的表名称:")) != null && tableName != "") {
                        startTime = System.nanoTime();
                        f = op.deleteTable(dbName, tableName);
                        endTime = System.nanoTime();
                    }
                    String message = op.getDate() + "消息3:" + tableName + ((f == true) ? "删表成功" : "删表失败") + "  runtime:" + getRuntime() + "s";
                    控制台.setText(message);
                    log.append(message);
                } else if (e.getSource() == jButtons[4]) {
                    String dbName = "";
                    String tableName = "";
                    String s;
                    boolean f = false;
                    if ((dbName = JOptionPane.showInputDialog("请输入数据库名称:")) != null && dbName != "" &&
                            (tableName = JOptionPane.showInputDialog("请输入表名称:")) != null && tableName != "") {
                        startTime = System.nanoTime();
                        ArrayList<Pair> column = op.getData(dbName, tableName);
                        String tip = "请按照顺序输入数据(";
                        for (int i = 0; i < column.size() - 1; i++) {
                            tip += column.get(i).getFirst();
                            tip += "(";
                            tip += column.get(i).getSecond();
                            tip += "), ";
                        }
                        tip += column.get(column.size() - 1).getFirst();
                        tip += "(";
                        tip += column.get(column.size() - 1).getSecond();
                        tip += ")): ";
                        //
                        if ((s = JOptionPane.showInputDialog(tip)) != null && s != "") {
                            String[] s1 = s.split(",");
                            for (int i = 0; i < s1.length; i++) {
                                s1[i] = s1[i].replaceAll(" ", "");
                            }
                            if (s1.length == column.size()) {
                                Data data = new Data();
                                for (int i = 0; i < s1.length; i++) {
                                    data.addData(column.get(i).getFirst(), s1[i]);
                                }
                                f = op.insert(dbName, tableName, data);
                                endTime = System.nanoTime();
                            }
                        }
                    }
                    String message = op.getDate() + "消息4:" + dbName + " " + tableName + ((f == true) ? "插入数据成功" : "插入数据失败") + "  runtime:" + getRuntime() + "s";
                    控制台.setText(message);
                    log.append(message);
                } else if (e.getSource() == jButtons[5]) {
                    String dbName = "";
                    String tableName = "";
                    String s;
                    Pair pair = new Pair("", "");
                    boolean f = false;
                    if ((dbName = JOptionPane.showInputDialog("请输入数据库名称:")) != null && dbName != "" &&
                            (tableName = JOptionPane.showInputDialog("请输入表名称:")) != null && tableName != "" &&
                            (s = JOptionPane.showInputDialog("请输入你要删除的列名及数据(如uName, wangkang):")) != null && s != "") {
                        startTime = System.nanoTime();
                        String[] s1 = s.split(",");
                        for (int i = 0; i < s1.length; i++) {
                            s1[i] = s1[i].replaceAll(" ", "");
                        }
                        if (s1.length >= 2) {
                            pair = new Pair(s1[0], s1[1]);
                            f = op.delete(dbName, tableName, pair);
                            endTime = System.nanoTime();
                        }
                    }
                    String message = op.getDate() + "消息5:" + dbName + " " + tableName + " "
                            + pair.getFirst() + " " + pair.getSecond() +
                            ((f == true) ? "删除数据成功" : "删除数据失败") + "  runtime:" + getRuntime() + "s";
                    控制台.setText(message);
                    log.append(message);
                } else if (e.getSource() == jButtons[6]) {
                    String dbName = "";
                    String tableName = "";
                    String s;
                    Pair upTo = new Pair("", "");
                    Pair upFrom = new Pair("", "");
                    boolean f = false;
                    if ((dbName = JOptionPane.showInputDialog("请输入数据库名称:")) != null && dbName != "" &&
                            (tableName = JOptionPane.showInputDialog("请输入表名称:")) != null && tableName != "" &&
                            (s = JOptionPane.showInputDialog("请输入列名及数据:\n如你要将uName=wangkang的行中的uid设为1，你需要输入(uid, 1, uNanme, wangkang)")) != null && s != "") {
                        startTime = System.nanoTime();
                        String[] s1 = s.split(",");
                        for (int i = 0; i < s1.length; i++) {
                            s1[i] = s1[i].replaceAll(" ", "");
                        }
                        if (s1.length >= 4) {
                            upTo = new Pair(s1[0], s1[1]);
                            upFrom = new Pair(s1[2], s1[3]);
                            f = op.update(dbName, tableName, upTo, upFrom);
                            endTime = System.nanoTime();
                        }
                    }
                    String message = op.getDate() + "消息6:" + dbName + " " + tableName + " "
                            + upTo.getFirst() + " " + upTo.getSecond() + " "
                            + upFrom.getFirst() + " " + upFrom.getSecond()
                            + ((f == true) ? "更新成功" : "更新失败") + "  runtime:" + getRuntime() + "s";
                    控制台.setText(message);
                    log.append(message);
                } else if (e.getSource() == jButtons[7]) {
                    String dbName = "";
                    String tableName = "";
                    boolean f = false;
                    if ((dbName = JOptionPane.showInputDialog("请输入数据库名称:")) != null && dbName != "" &&
                            (tableName = JOptionPane.showInputDialog("请输入表名称:")) != null && tableName != "") {
                        startTime = System.nanoTime();
                        ArrayList<String[]> data = op.query(dbName, tableName);
                        if (data.size() != 0) {
                            控制台.setText("");
                            for (int j = 0; j < data.size(); j++) {
                                for (int i = 0; i < data.get(j).length; i++) {
                                    Formatter formatter = new Formatter();
                                    formatter.format("%-25s", data.get(j)[i]);
                                    控制台.append(formatter.toString());
                                }
                                控制台.append("\n");
                            }
                            f = true;
                        }
                    }
                    endTime = System.nanoTime();
                    String message = op.getDate() + ((f == true) ? " 查询成功" : " 查询失败") + "  runtime:" + getRuntime() + "s";
                    if (f == true) 控制台.append(message);
                    else 控制台.setText(message);
                    log.append(message);
                } else if (e.getSource() == jButtons[8]) {
                    String dbName;
                    String tableName;
                    String s;
                    Pair pair;
                    boolean f = false;
                    if ((dbName = JOptionPane.showInputDialog("请输入数据库名称:")) != null && dbName != "" &&
                            (tableName = JOptionPane.showInputDialog("请输入表名称:")) != null && tableName != "" &&
                            (s = JOptionPane.showInputDialog("请输入你要查询的列名及数据:\n如uName, wangkang")) != null && s != "") {
                        startTime = System.nanoTime();
                        String[] s1 = s.split(",");
                        for (int i = 0; i < s1.length; i++) {
                            s1[i] = s1[i].replaceAll(" ", "");
                        }
                        if (s1.length >= 2) {
                            pair = new Pair(s1[0], s1[1]);
                            ArrayList<String[]> data = op.query(dbName, tableName, pair);
                            if (data.size() != 0) {
                                控制台.setText("");
                                for (int j = 0; j < data.size(); j++) {
                                    for (int i = 0; i < data.get(j).length; i++) {
                                        Formatter formatter = new Formatter();
                                        formatter.format("%-25s", data.get(j)[i]);
                                        控制台.append(formatter.toString());
                                    }
                                    控制台.append("\n");
                                }
                                f = true;
                            }
                        }
                    }
                    endTime = System.nanoTime();
                    String message = op.getDate() + ((f == true) ? " 查询成功" : " 查询失败") + "  runtime:" + getRuntime() + "s";
                    if (f == true) 控制台.append(message);
                    else 控制台.setText(message);
                    log.append(message);
                }
            }
        };
        for (int i = 0; i < jButtons.length; i++) {
            jButtons[i].setFocusPainted(false);
            jButtons[i].addActionListener(ls);
            jPanel.add(jButtons[i]);
        }
        jPanel.setBorder(BorderFactory.createEtchedBorder());
    }

    private void add查询面板(JPanel jPanel) {
        jPanel.removeAll();
        jPanel.setLayout(gridBagLayout);
        //
        查询面板 = new JTextArea();
        查询面板.setOpaque(false);
        查询面板.setFont(new Font("隶书", Font.PLAIN, 17));
        JScrollPane jsp = new JScrollPane(查询面板);
        jsp.setOpaque(false);
        jsp.getViewport().setOpaque(false);
        //
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.gridheight = 0;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagLayout.setConstraints(jsp, gridBagConstraints);
        //
        jPanel.add(jsp);
    }

    private void delete(JPanel jPanel) {
        jPanel.removeAll();
        jPanel.validate();
        jPanel.repaint();
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

    private double getRuntime() {
        return (endTime - startTime) / 1000000000.0;
    }
}
