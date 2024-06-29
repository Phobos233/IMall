package JFrame;

import dao.UserDao;
import model.User;
import untils.DbUtil;
import untils.toolUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

public class AdminUserInfo extends JFrame {
    private JFrame jf;
    private JTextField textField;
    private JComboBox comboBox;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;

    private UserDao userDao=new UserDao();
    private DbUtil dbUtil=new DbUtil();

    public AdminUserInfo(){
        //页面&导航栏定义
        jf=new JFrame("管理员界面");
        jf.setBounds(400,100,600,700);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().setLayout(null);

        JMenuBar menuBar=new JMenuBar();
        jf.setJMenuBar(menuBar);

        JMenu goodsNewMenu=new JMenu("商品选项");
        menuBar.add(goodsNewMenu);

        JMenu addNewMenu =new JMenu("添加商品");
        addNewMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jf.dispose();
                new AdminAddGoods();
            }
        });
        goodsNewMenu.add(addNewMenu);

        JMenu modifyNewMenu=new JMenu("已有商品信息管理");
        modifyNewMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jf.dispose();
                new AdminGoodsInfo();
            }
        });
        goodsNewMenu.add(modifyNewMenu);

        JMenu userNewMenu=new JMenu("用户操作");
        menuBar.add(userNewMenu);

        JMenu adduserNewMenu =new JMenu("添加用户");
        adduserNewMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jf.dispose();
                new AdminAddUser();
            }
        });
        userNewMenu.add(adduserNewMenu);

        JMenu modifyUserNewMenu=new JMenu("已有用户信息管理");
        modifyUserNewMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jf.dispose();
                new AdminUserInfo();
            }
        });
        userNewMenu.add(modifyUserNewMenu);

        JMenu exitNewMenu=new JMenu("退出系统");
        exitNewMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JOptionPane.showMessageDialog(null,"欢迎再次使用");
                jf.dispose();
            }
        });
        menuBar.add(exitNewMenu);

        //搜索栏板块
        JPanel Panel=new JPanel();
        Panel.setBorder(new TitledBorder(null,"用户查询",TitledBorder.LEADING,TitledBorder.TOP,null, Color.cyan));
        Panel.setBounds(20,10,541,78);
        Panel.setLayout(null);
        jf.getContentPane().setLayout(null);
        jf.getContentPane().add(Panel);

        //搜索选项下拉栏
        comboBox = new JComboBox();
        comboBox.setFont(new Font("幼圆",Font.BOLD,14));
        comboBox.setBounds(35,28,90,28);
        comboBox.addItem("用户名称");
        comboBox.addItem("用户ID");
        comboBox.addItem("用户类别");
        comboBox.addItem("用户手机号");
        Panel.add(comboBox);

        //搜索输入栏
        textField = new JTextField();
        textField.setBounds(160,28,230,28);
        Panel.add(textField);
        textField.setColumns(10);

        //查询按钮定义
        JButton sButton=new JButton("查询");
        sButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=comboBox.getSelectedIndex();
                switch (index){
                    case 0:{
                        String u_name=textField.getText();
                        User user=new User();
                        user.setUsername(u_name);
                        putDates(user);
                        break;
                    }
                    case 1:{
                        int u_id=Integer.parseInt(textField.getText());
                        User user=new User();
                        user.setUserID(u_id);
                        putDates(user);
                        break;

                    }
                    case 2:{
                        int u_role=Integer.parseInt(textField.getText());
                        User user=new User();
                        user.setRole(u_role);
                        putDates(user);
                        break;
                    }
                    case 3:{
                        String u_PNum=textField.getText();
                        User user=new User();
                        user.setP_num(u_PNum);
                        putDates(user);
                        break;
                    }
                }

            }
        });
        sButton.setFont(new Font("幼圆",Font.BOLD,14));
        sButton.setBounds(425,28,81,28);
        Panel.add(sButton);

        //用户展示面板
        JPanel panel1=new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(20,105,541,195);
        panel1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),"用户信息列表",TitledBorder.LEADING,TitledBorder.TOP,null,Color.cyan));

        //用户信息表格定义
        String[] Title ={"用户ID","用户名","密码","用户类型","性别","手机号码"};
        String[][] dates={};
        DefaultTableModel model = new DefaultTableModel(dates, Title);
        table1=new JTable(model);
        putDates(new User());
        panel1.setLayout(null);
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setBounds(20,22,496,154);
        jScrollPane.setViewportView(table1);
        panel1.add(jScrollPane);
        jf.getContentPane().add(panel1);

        //表格项目事件定义
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tableMousePressed();
            }
        });


        //详细信息界面板块定义
        JPanel Panel1=new JPanel();
        Panel1.setBorder(new TitledBorder(null,"用户详细信息",TitledBorder.LEADING,TitledBorder.TOP,null, Color.cyan));
        Panel1.setBounds(20,310,541,292);
        Panel1.setLayout(null);
        jf.getContentPane().setLayout(null);
        jf.getContentPane().add(Panel1);

        //用户ID文本 文本框
        JLabel label=new JLabel("用户ID");
        label.setFont(new Font("幼圆",Font.BOLD,14));
        label.setBounds(40,35,60,27);
        Panel1.add(label);

        textField1=new JTextField();
        textField1.setBounds(101,35,128,27);
        Panel1.add(textField1);
        textField1.setColumns(10);

        //用户名文本、文本框
        JLabel label_1=new JLabel("用户名：");
        label_1.setFont(new Font("幼圆",Font.BOLD,14));
        label_1.setBounds(275,35,60,27);
        Panel1.add(label_1);

        textField2=new JTextField();
        textField2.setColumns(10);
        textField2.setBounds(338,35,128,27);
        Panel1.add(textField2);


        //密码框
        JLabel label_2 = new JLabel("密码");
        label_2.setFont(new Font("幼圆",Font.BOLD,14));
        label_2.setBounds(50,70,100,27);
        Panel1.add(label_2);

        textField3=new JTextField();
        textField3.setColumns(10);
        textField3.setBounds(101,70,128,27);
        Panel1.add(textField3);

        JLabel label_3=new JLabel("用户类型");
        label_3.setFont(new Font("幼圆",Font.BOLD,14));
        label_3.setBounds(270,70,80,27);
        Panel1.add(label_3);

        textField4 = new JTextField();
        textField4.setColumns(10);
        textField4.setBounds(338,70,128,27);
        Panel1.add(textField4);

        JLabel label_4= new JLabel("性别");
        label_4.setFont(new Font("幼圆",Font.BOLD,14));
        label_4.setBounds(50,110,100,27);
        Panel1.add(label_4);

        textField5=new JTextField();
        textField5.setColumns(10);
        textField5.setBounds(101,110,128,27);
        Panel1.add(textField5);

        JLabel label_5= new JLabel("手机号码");
        label_5.setFont(new Font("幼圆",Font.BOLD,14));
        label_5.setBounds(270,110,100,27);
        Panel1.add(label_5);

        textField6=new JTextField();
        textField6.setColumns(10);
        textField6.setBounds(338,110,128,27);
        Panel1.add(textField6);

        JButton u_modifyButton =new JButton("修改");
        u_modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer id =Integer.parseInt(textField1.getText());
                String username = textField2.getText();
                String password = textField3.getText();
                Integer role = Integer.parseInt(textField4.getText());
                String sex = textField5.getText();
                String p_num = textField6.getText();

                if (toolUtil.isEmpty(username)|| toolUtil.isEmpty(sex)||toolUtil.isEmpty(password)
                        ||toolUtil.isEmpty(textField1.getText())||toolUtil.isEmpty(p_num)
                        ||toolUtil.isEmpty(textField4.getText())
                        ){
                    JOptionPane.showMessageDialog(null,"请输入相关内容！");
                    return;
                }

                User user=new User();
                user.setUserID(id);
                user.setUsername(username);
                user.setPassword(password);
                user.setRole(role);
                user.setSex(sex);
                user.setP_num(p_num);
                Connection con=null;
                try {
                    con=dbUtil.getConnection();
                    int i=UserDao.update(con,user);
                    if (i==1){
                        JOptionPane.showMessageDialog(null,"修改成功");
                        reset();
                    }else {
                        JOptionPane.showMessageDialog(null,"修改失败");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,"修改异常");
                }
                finally {
                    try{
                        DbUtil.closeCon(con);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
        u_modifyButton.setFont(new Font("幼圆",Font.BOLD,14));
        u_modifyButton.setBounds(125,200,81,28);
        Panel1.add(u_modifyButton);

        JButton u_deleteButton=new JButton("删除");
        u_deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer id =Integer.parseInt(textField1.getText());
                String username = textField2.getText();
                String password = textField3.getText();
                Integer role = Integer.parseInt(textField4.getText());
                String sex = textField5.getText();
                String p_num = textField6.getText();

                if (toolUtil.isEmpty(username)|| toolUtil.isEmpty(sex)||toolUtil.isEmpty(password)
                        ||toolUtil.isEmpty(textField1.getText())||toolUtil.isEmpty(p_num)
                        ||toolUtil.isEmpty(textField4.getText())
                ){
                    JOptionPane.showMessageDialog(null,"请输入相关内容！");
                    return;
                }

                User user=new User();
                user.setUserID(id);
                user.setUsername(username);
                user.setPassword(password);
                user.setRole(role);
                user.setSex(sex);
                user.setP_num(p_num);
                Connection con=null;
                try {
                    con=dbUtil.getConnection();
                    int i=userDao.delete(con,user);
                    if (i==1){
                        JOptionPane.showMessageDialog(null,"删除成功");
                        reset();
                    }else {
                        JOptionPane.showMessageDialog(null,"删除失败");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,"删除异常");
                }
                finally {
                    try{
                        DbUtil.closeCon(con);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        u_deleteButton.setFont(new Font("幼圆",Font.BOLD,14));
        u_deleteButton.setBounds(360,200,81,28);
        Panel1.add(u_deleteButton);

        jf.setVisible(true);
    }

    private void tableMousePressed(){
        int row=table1.getSelectedRow();
        Integer userId=(Integer) table1.getValueAt(row,0);

        User user=new User();
        user.setUserID(userId);

        Connection con=null;
        try {
            con= DbUtil.getConnection();
            ResultSet resultSet=userDao.list(con,user);
            if (resultSet.next()){
                textField1.setText(resultSet.getString("id"));
                textField2.setText(resultSet.getString("username"));
                textField3.setText(resultSet.getString("password"));
                textField4.setText(resultSet.getString("role"));
                textField5.setText(resultSet.getString("sex"));
                textField6.setText(resultSet.getString("p_num"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try{
                DbUtil.closeCon(con);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }



    private void putDates(User user) {
        DefaultTableModel model=(DefaultTableModel) table1.getModel();
        model.setRowCount(0);

        Connection con=null;
        try {
            con= DbUtil.getConnection();
            ResultSet resultSet=userDao.list(con,user);
            while (resultSet.next()){
                Vector rowData=new Vector();
                rowData.add(resultSet.getInt("id"));
                rowData.add(resultSet.getString("username"));
                rowData.add(resultSet.getString("password"));
                rowData.add(resultSet.getString("role"));
                rowData.add(resultSet.getString("sex"));
                rowData.add(resultSet.getString("p_num"));
                model.addRow(rowData);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try{
                DbUtil.closeCon(con);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void reset(){
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        textField6.setText("");
    }

    public static void main(String[] args) {
        new AdminUserInfo();
    }
}
