package JFrame;

import dao.GoodsDao;
import model.goods;
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
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

public class AdminGoodsInfo extends JFrame {
    private JFrame jf;
    private JTextField textField;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextArea textArea;
    private JComboBox comboBox;
    private GoodsDao goodsDao=new GoodsDao();
    private DbUtil dbUtil=new DbUtil();

    public AdminGoodsInfo(){

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

        //查询界面面板
        JPanel Panel=new JPanel();
        Panel.setBorder(new TitledBorder(null,"商品查询",TitledBorder.LEADING,TitledBorder.TOP,null, Color.cyan));
        Panel.setBounds(20,10,541,78);
        Panel.setLayout(null);
        jf.getContentPane().setLayout(null);
        jf.getContentPane().add(Panel);

        //搜索选项下拉栏
        comboBox = new JComboBox();
        comboBox.setFont(new Font("幼圆",Font.BOLD,14));
        comboBox.setBounds(35,28,90,28);
        comboBox.addItem("商品名称");
        comboBox.addItem("商品类别");
        comboBox.addItem("品牌");
        comboBox.addItem("商品ID");
        Panel.add(comboBox);

        //搜索输入栏
        textField = new JTextField();
        textField.setBounds(160,28,230,28);
        Panel.add(textField);
        textField.setColumns(10);

        //查询按钮
        JButton sButton=new JButton("查询");
        sButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=comboBox.getSelectedIndex();
                switch (index){
                    case 0:{
                        String g_name=textField.getText();
                        goods good=new goods();
                        good.setGoodName(g_name);
                        putDates(good);
                        break;
                    }
                    case 1:{
                        String g_type=textField.getText();
                        goods good=new goods();
                        good.setType(g_type);
                        putDates(good);
                        break;
                    }
                    case 2:{
                        String g_brand=textField.getText();
                        goods good=new goods();
                        good.setBrand(g_brand);
                        putDates(good);
                        break;
                    }
                    case 3:{
                        int g_id=Integer.parseInt(textField.getText());
                        goods good=new goods();
                        good.setGoodID(g_id);
                        putDates(good);
                        break;
                    }
                }

            }
        });
        sButton.setFont(new Font("幼圆",Font.BOLD,14));
        sButton.setBounds(425,28,81,28);
        Panel.add(sButton);

        //商品展示面板
        JPanel panel1=new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(20,105,541,195);
        panel1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),"商品信息列表",TitledBorder.LEADING,TitledBorder.TOP,null,Color.cyan));

        //商品信息表格定义
        String[] Title ={"商品ID","商品名","类别","品牌","价格","库存","介绍","打折状态"};
        String[][] dates={};
        DefaultTableModel model = new DefaultTableModel(dates, Title);
        table1=new JTable(model);

        putDates(new goods());

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

        //信息面板
        JPanel Panel1=new JPanel();
        Panel1.setBorder(new TitledBorder(null,"商品详细信息",TitledBorder.LEADING,TitledBorder.TOP,null, Color.cyan));
        Panel1.setBounds(20,310,541,292);
        Panel1.setLayout(null);
        jf.getContentPane().setLayout(null);
        jf.getContentPane().add(Panel1);

        //各个文本项目及文本输入框定义
        JLabel label=new JLabel("商品ID");
        label.setFont(new Font("幼圆",Font.BOLD,14));
        label.setBounds(40,20,60,27);
        Panel1.add(label);

        textField1=new JTextField();
        textField1.setBounds(101,20,128,27);
        Panel1.add(textField1);
        textField1.setColumns(10);

        JLabel label_1=new JLabel("商品名：");
        label_1.setFont(new Font("幼圆",Font.BOLD,14));
        label_1.setBounds(275,20,60,27);
        Panel1.add(label_1);

        textField2=new JTextField();
        textField2.setColumns(10);
        textField2.setBounds(338,20,128,27);
        Panel1.add(textField2);

        JLabel label_2 = new JLabel("商品类型");
        label_2.setFont(new Font("幼圆",Font.BOLD,14));
        label_2.setBounds(35,55,100,27);
        Panel1.add(label_2);

        textField3=new JTextField();
        textField3.setColumns(10);
        textField3.setBounds(101,55,128,27);
        Panel1.add(textField3);

        JLabel label_3=new JLabel("品牌");
        label_3.setFont(new Font("幼圆",Font.BOLD,14));
        label_3.setBounds(285,55,80,27);
        Panel1.add(label_3);

        textField4 = new JTextField();
        textField4.setColumns(10);
        textField4.setBounds(338,55,128,27);
        Panel1.add(textField4);

        JLabel label_4= new JLabel("价格");
        label_4.setFont(new Font("幼圆",Font.BOLD,14));
        label_4.setBounds(50,95,100,27);
        Panel1.add(label_4);

        textField5=new JTextField();
        textField5.setColumns(10);
        textField5.setBounds(101,95,128,27);
        Panel1.add(textField5);

        JLabel label_5= new JLabel("商品库存");
        label_5.setFont(new Font("幼圆",Font.BOLD,14));
        label_5.setBounds(270,95,100,27);
        Panel1.add(label_5);

        textField6=new JTextField();
        textField6.setColumns(10);
        textField6.setBounds(338,95,128,27);
        Panel1.add(textField6);

        JLabel label_6= new JLabel("商品描述");
        label_6.setFont(new Font("幼圆",Font.BOLD,14));
        label_6.setBounds(35,135,100,27);
        Panel1.add(label_6);

        textArea=new JTextArea();
        textArea.setBounds(101,135,220,120);
        textArea.setFont(new Font("幼圆",Font.BOLD,14));
        textArea.setColumns(20);
        textArea.setRows(18);
        Panel1.add(textArea);

        JLabel label_7= new JLabel("打折状态");
        label_7.setFont(new Font("幼圆",Font.BOLD,14));
        label_7.setBounds(374,135,100,27);
        Panel1.add(label_7);

        textField7=new JTextField();
        textField7.setColumns(10);
        textField7.setBounds(365,160,80,27);
        Panel1.add(textField7);

        JButton modifyButton=new JButton("修改");
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer goodID=Integer.parseInt(textField1.getText());
                String goodName=textField2.getText();
                String type=textField3.getText();
                String brand=textField4.getText();
                String priceStr=textField5.getText();
                Integer amount=Integer.parseInt(textField6.getText());
                String Introduction=textArea.getText();
                String saleStatus=textField7.getText();

                if (toolUtil.isEmpty(goodName)|| toolUtil.isEmpty(brand)||toolUtil.isEmpty(priceStr)
                        ||toolUtil.isEmpty(textField1.getText())||toolUtil.isEmpty(type)||toolUtil.isEmpty(textField6.getText())
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
                    con= DbUtil.getConnection();
                    int i= GoodsDao.update(con,good);
                    if (i==1){
                        JOptionPane.showMessageDialog(null,"修改成功");
                        reset();
                    }else {
                        JOptionPane.showMessageDialog(null,"修改失败");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,"修改异常");
                }finally {
                    try{
                        DbUtil.closeCon(con);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });


        modifyButton.setFont(new Font("幼圆",Font.BOLD,14));
        modifyButton.setBounds(365,205,77,27);
        Panel1.add(modifyButton);

        JButton deleteButton=new JButton("删除");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer goodID=Integer.parseInt(textField1.getText());
                String goodName=textField2.getText();
                String type=textField3.getText();
                String brand=textField4.getText();
                String priceStr=textField5.getText();
                Integer amount=Integer.parseInt(textField6.getText());
                String Introduction=textArea.getText();
                String saleStatus=textField7.getText();

                if (toolUtil.isEmpty(goodName)|| toolUtil.isEmpty(brand)||toolUtil.isEmpty(priceStr)
                        ||toolUtil.isEmpty(textField1.getText())||toolUtil.isEmpty(type)||toolUtil.isEmpty(textField6.getText())
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
                    con= DbUtil.getConnection();
                    int i= goodsDao.delete(con,good);
                    if (i==1){
                        JOptionPane.showMessageDialog(null,"删除成功");
                        reset();
                    }else {
                        JOptionPane.showMessageDialog(null,"删除失败");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,"删除异常");
                }finally {
                    try{
                        DbUtil.closeCon(con);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });


        deleteButton.setFont(new Font("幼圆",Font.BOLD,14));
        deleteButton.setBounds(365,240,77,27);
        Panel1.add(deleteButton);

        jf.setVisible(true);
    }

    //表格点击事件，在下方文本框显示各项信息
    private void tableMousePressed(){
        int row=table1.getSelectedRow();
        Integer goodId=(Integer) table1.getValueAt(row,0);

        goods good=new goods();
        good.setGoodID(goodId);

        Connection con=null;
        try {
            con= DbUtil.getConnection();
            ResultSet resultSet=goodsDao.list(con,good);
            if (resultSet.next()){
                textField1.setText(resultSet.getString("g_id"));
                textField2.setText(resultSet.getString("g_name"));
                textField3.setText(resultSet.getString("g_type"));
                textField4.setText(resultSet.getString("g_brand"));
                textField5.setText(resultSet.getString("g_price"));
                textField6.setText(resultSet.getString("g_amount"));
                textArea.setText(resultSet.getString("g_introduction"));
                textField7.setText(resultSet.getString("g_salestatus"));
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


    private void putDates(goods goods) {
        DefaultTableModel model=(DefaultTableModel) table1.getModel();
        model.setRowCount(0);

        Connection con=null;
        try {
            con= DbUtil.getConnection();
            ResultSet resultSet=goodsDao.list(con,goods);
            while (resultSet.next()){
                Vector rowData=new Vector();
                rowData.add(resultSet.getInt("g_id"));
                rowData.add(resultSet.getString("g_name"));
                rowData.add(resultSet.getString("g_type"));
                rowData.add(resultSet.getString("g_brand"));
                rowData.add(resultSet.getDouble("g_price"));
                rowData.add(resultSet.getInt("g_amount"));
                rowData.add(resultSet.getString("g_introduction"));
                rowData.add(resultSet.getString("g_salestatus"));
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
        textField7.setText("");
        textArea.setText("");
    }
    public static void main(String[] args) {
        new AdminGoodsInfo();
    }
}
