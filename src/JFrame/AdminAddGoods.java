package JFrame;

import dao.GoodsDao;
import model.goods;
import untils.toolUtil;
import untils.DbUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;



public class AdminAddGoods extends JFrame{
    private JFrame AAG;
    private JTextField textField;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextArea textArea;


    public AdminAddGoods(){

        //页面总布局
        AAG=new JFrame("管理员界面");
        AAG.setBounds(400,100,600,429);
        AAG.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        AAG.getContentPane().setLayout(null);

        //操作界面框
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null,"货物添加",TitledBorder.LEADING,TitledBorder.TOP,null, Color.cyan));
        panel.setBounds(23,21,540,280);
        AAG.getContentPane().add(panel);
        panel.setLayout(null);

        //商品名功能
        JLabel label=new JLabel("商品名：");
        label.setFont(new Font("幼圆",Font.BOLD,14));
        label.setBounds(40,31,100,27);
        panel.add(label);

        textField=new JTextField();
        textField.setBounds(101,31,129,27);
        panel.add(textField);
        textField.setColumns(10);

        //商品类型设置
        JLabel label_1=new JLabel("商品类型");
        label_1.setFont(new Font("幼圆",Font.BOLD,14));
        label_1.setBounds(270,31,80,27);
        panel.add(label_1);

        textField1=new JTextField();
        textField1.setColumns(10);
        textField1.setBounds(338,31,128,27);
        panel.add(textField1);

        JLabel label_2 = new JLabel("品牌");
        label_2.setFont(new Font("幼圆",Font.BOLD,14));
        label_2.setBounds(47,65,100,27);
        panel.add(label_2);

        textField2=new JTextField();
        textField2.setColumns(10);
        textField2.setBounds(101,65,128,27);
        panel.add(textField2);

        JLabel label_3=new JLabel("价格");
        label_3.setFont(new Font("幼圆",Font.BOLD,14));
        label_3.setBounds(285,65,80,27);
        panel.add(label_3);

        textField3 = new JTextField();
        textField3.setColumns(10);
        textField3.setBounds(338,65,128,27);
        panel.add(textField3);

        JLabel label_4= new JLabel("商品库存");
        label_4.setFont(new Font("幼圆",Font.BOLD,14));
        label_4.setBounds(35,100,100,27);
        panel.add(label_4);

        textField4=new JTextField();
        textField4.setColumns(10);
        textField4.setBounds(101,100,128,27);
        panel.add(textField4);

        JLabel label_5= new JLabel("商品ID");
        label_5.setFont(new Font("幼圆",Font.BOLD,14));
        label_5.setBounds(275,100,100,27);
        panel.add(label_5);

        textField5=new JTextField();
        textField5.setColumns(10);
        textField5.setBounds(338,100,128,27);
        panel.add(textField5);

        JLabel label_6= new JLabel("商品描述");
        label_6.setFont(new Font("幼圆",Font.BOLD,14));
        label_6.setBounds(35,135,100,27);
        panel.add(label_6);

        textArea=new JTextArea();
        textArea.setBounds(101,135,220,120);
        textArea.setFont(new Font("幼圆",Font.BOLD,14));
        textArea.setColumns(20);
        textArea.setRows(18);
        panel.add(textArea);

        JLabel label_7= new JLabel("打折状态");
        label_7.setFont(new Font("幼圆",Font.BOLD,14));
        label_7.setBounds(374,135,100,27);
        panel.add(label_7);

        textField6=new JTextField();
        textField6.setColumns(10);
        textField6.setBounds(365,160,80,27);
        panel.add(textField6);

        JButton AddButton=new JButton("添加");
        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String goodName=textField.getText();
                String type=textField1.getText();
                String brand=textField2.getText();
                String priceStr=textField3.getText();
                Integer amount=Integer.parseInt(textField4.getText());
                Integer goodID=Integer.parseInt(textField5.getText());
                String Introduction=textArea.getText();
                String saleStatus=textField6.getText();

                if (toolUtil.isEmpty(goodName)|| toolUtil.isEmpty(brand)||toolUtil.isEmpty(priceStr)
                        ||toolUtil.isEmpty(textField4.getText())||toolUtil.isEmpty(type)||toolUtil.isEmpty(textField5.getText())
                        ||toolUtil.isEmpty(Introduction)||toolUtil.isEmpty(saleStatus)){
                    JOptionPane.showMessageDialog(null,"请输入相关内容！");
                    return;
                }
                double price;
                try{
                     price=new BigDecimal(priceStr).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(null,"参数错误");
                    return;
                }

                goods good=new goods();
                good.setGoodName(goodName);
                good.setGoodID(goodID);
                good.setType(type);
                good.setAmount(amount);
                good.setBrand(brand);
                good.setInfo(Introduction);
                good.setSalestatus(saleStatus);
                good.setPrice(price);
                Connection con=null;
                try {
                    con=DbUtil.getConnection();
                    int i= GoodsDao.add(con,good);
                    if (i==1){
                        JOptionPane.showMessageDialog(null,"添加成功");
                        reset();
                    }else {
                        JOptionPane.showMessageDialog(null,"添加失败");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,"添加异常");
                }finally {
                    try{
                        DbUtil.closeCon(con);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
        AddButton.setFont(new Font("幼圆",Font.BOLD,14));
        AddButton.setBounds(365,205,77,27);
        panel.add(AddButton);

        JButton resetButton =new JButton("重置");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        resetButton.setFont(new Font("幼圆",Font.BOLD,14));
        resetButton.setBounds(365,235,77,27);
        panel.add(resetButton);


        JMenuBar menuBar=new JMenuBar();
        AAG.setJMenuBar(menuBar);

        JMenu goodsNewMenu=new JMenu("商品选项");
        menuBar.add(goodsNewMenu);

        JMenu addNewMenu =new JMenu("添加商品");
        addNewMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                AAG.dispose();
                new AdminAddGoods();
            }
        });
        goodsNewMenu.add(addNewMenu);

        JMenu modifyNewMenu=new JMenu("已有商品信息管理");
        modifyNewMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                AAG.dispose();
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
                AAG.dispose();
                new AdminAddUser();
            }
        });
        userNewMenu.add(adduserNewMenu);

        JMenu modifyUserNewMenu=new JMenu("已有用户信息管理");
        modifyUserNewMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                AAG.dispose();
                new AdminUserInfo();
            }
        });
        userNewMenu.add(modifyUserNewMenu);

        JMenu exitNewMenu=new JMenu("退出系统");
        exitNewMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JOptionPane.showMessageDialog(null,"欢迎再次使用");
                AAG.dispose();
            }
        });
        menuBar.add(exitNewMenu);

        AAG.setVisible(true);
    }
    public void reset(){
        textField.setText("");
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        textField6.setText("");
        textArea.setText("");
    }

    public static void main(String args[]){
        new AdminAddGoods();
    }
}
