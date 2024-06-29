package JFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminMenuFrm extends JFrame {
    private JFrame jf;
    private JTextField textField;
    private JButton btnNewButton;
    private JTextArea textArea;

    public AdminMenuFrm(){
        //初始化主界面对应窗体组件
        jf=new JFrame("管理员界面");
        jf.setBounds(400,100,600,400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().setLayout(null);

        JLabel label1 =new JLabel("请于上方菜单栏选择对应项目进行操作");
        label1.setFont(new Font("幼圆",Font.BOLD,16));
        label1.setBounds(150,120,300,40);
        jf.getContentPane().add(label1);

        //顶部菜单栏定义及对应事件定义
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

        jf.setVisible(true);

    }
    public static void main(String args[]){
       new  AdminMenuFrm();
    }
}
