package JFrame;

import dao.UserDao;
import model.User;
import untils.DbUtil;
import untils.toolUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

public class AdminAddUser extends JFrame {
    private JFrame jf;
    private JTextField textField;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;

    private UserDao userDao=new UserDao();
    private DbUtil dbUtil=new DbUtil();

    public AdminAddUser(){
        //页面&导航栏定义
        jf=new JFrame("管理员界面");
        jf.setBounds(400,100,600,350);
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

        //操作界面框
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null,"货物添加",TitledBorder.LEADING,TitledBorder.TOP,null, Color.cyan));
        panel.setBounds(23,21,540,230);
        jf.getContentPane().add(panel);
        panel.setLayout(null);

        //商品名功能
        JLabel label=new JLabel("用户ID");
        label.setFont(new Font("幼圆",Font.BOLD,14));
        label.setBounds(35,31,100,27);
        panel.add(label);

        textField=new JTextField();
        textField.setBounds(101,31,129,27);
        panel.add(textField);
        textField.setColumns(10);

        //商品类型设置
        JLabel label_1=new JLabel("用户名");
        label_1.setFont(new Font("幼圆",Font.BOLD,14));
        label_1.setBounds(270,31,80,27);
        panel.add(label_1);

        textField1=new JTextField();
        textField1.setColumns(10);
        textField1.setBounds(338,31,128,27);
        panel.add(textField1);

        JLabel label_2 = new JLabel("用户密码");
        label_2.setFont(new Font("幼圆",Font.BOLD,14));
        label_2.setBounds(35,65,100,27);
        panel.add(label_2);

        textField2=new JTextField();
        textField2.setColumns(10);
        textField2.setBounds(101,65,128,27);
        panel.add(textField2);

        JLabel label_3=new JLabel("用户类型");
        label_3.setFont(new Font("幼圆",Font.BOLD,14));
        label_3.setBounds(270,65,80,27);
        panel.add(label_3);

        textField3 = new JTextField();
        textField3.setColumns(10);
        textField3.setBounds(338,65,128,27);
        panel.add(textField3);

        JLabel label_4= new JLabel("用户性别");
        label_4.setFont(new Font("幼圆",Font.BOLD,14));
        label_4.setBounds(35,100,100,27);
        panel.add(label_4);

        textField4=new JTextField();
        textField4.setColumns(10);
        textField4.setBounds(101,100,128,27);
        panel.add(textField4);

        JLabel label_5= new JLabel("手机号码");
        label_5.setFont(new Font("幼圆",Font.BOLD,14));
        label_5.setBounds(270,100,100,27);
        panel.add(label_5);

        textField5=new JTextField();
        textField5.setColumns(10);
        textField5.setBounds(338,100,128,27);
        panel.add(textField5);

        JButton AddButton=new JButton("添加");
        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Integer id =Integer.parseInt(textField.getText());
                String username = textField1.getText();
                String password = textField2.getText();
                Integer role = Integer.parseInt(textField3.getText());
                String sex = textField4.getText();
                String p_num = textField5.getText();

                if (toolUtil.isEmpty(username)|| toolUtil.isEmpty(sex)||toolUtil.isEmpty(password)
                        ||toolUtil.isEmpty(textField1.getText())||toolUtil.isEmpty(p_num)
                        ||toolUtil.isEmpty(textField3.getText())
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
                    int i=UserDao.add(con,user);
                    if (i==1){
                        JOptionPane.showMessageDialog(null,"添加成功");
                        reset();
                    }else {
                        JOptionPane.showMessageDialog(null,"添加失败");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,"添加异常");
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
        AddButton.setFont(new Font("幼圆",Font.BOLD,14));
        AddButton.setBounds(220,165,77,27);
        panel.add(AddButton);


        jf.setVisible(true);
    }
    public void reset(){
        textField.setText("");
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
    }
    public static void main(String[] args) {
        new AdminAddUser();
    }
}
