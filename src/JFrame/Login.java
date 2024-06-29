package JFrame;

import dao.UserDao;
import model.User;

import untils.DbUtil;
import untils.toolUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Login extends JFrame {
    public static User currentUser;
    private JFrame jF;
    private JTextField userNameText;
    private JTextField passwordText;
    private JComboBox<String> comboBox;
    String userName;
    String password;
    int index;
    private DbUtil dbUtil=new DbUtil();
    private UserDao userDao=new UserDao();
    private  User user=new User();

    public  Login(){
        jF=new JFrame("网上商城iMALL");
        jF.getContentPane().setFont(new Font("幼圆",Font.BOLD,14));
        jF.setBounds(600,250,435,400);
        jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jF.getContentPane().setLayout(null);

        JLabel labeltitle = new JLabel("Imall商城管理系统");
        labeltitle.setFont(new Font("幼圆",Font.BOLD,16));
        labeltitle.setBounds(150,60,150,30);
        jF.getContentPane().add(labeltitle);

        JLabel label=new JLabel("用户名：");
        label.setFont(new Font("幼圆",Font.BOLD,14));
        label.setBounds(99,120,60,29);
        jF.getContentPane().add(label);

        userNameText=new JTextField();
        userNameText.setBounds(169,122,127,25);
        jF.getContentPane().add(userNameText);
        userNameText.setColumns(10);

        JLabel label1=new JLabel("密码");
        label1.setFont(new Font("幼圆",Font.BOLD,14));
        label1.setBounds(114,159,45,29);
        jF.getContentPane().add(label1);

        passwordText=new JPasswordField();
        passwordText.setColumns(10);
        passwordText.setBounds(169,161,127,25);
        jF.getContentPane().add(passwordText);

        JLabel label2=new JLabel("权限:");
        label2.setFont(new Font("幼圆",Font.BOLD,14));
        label2.setBounds(114,198,45,29);
        jF.getContentPane().add(label2);

        comboBox=new JComboBox<>();
        comboBox.setBounds(169,202,127,25);
        comboBox.addItem("用户");
        comboBox.addItem("管理员");
        jF.getContentPane().add(comboBox);

        JButton button=new JButton("登录");
        button.setBounds(190,247,65,29);
        jF.getContentPane().add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userName = userNameText.getText();
                password = passwordText.getText();
                index = comboBox.getSelectedIndex();
                if (toolUtil.isEmpty(userName) || toolUtil.isEmpty(password)) {
                    JOptionPane.showMessageDialog(null, "用户名和密码不能为空");
                    return;
                };
                user.setUsername(userName);
                user.setPassword(password);

                if (index==0){
                    user.setRole(1);
                }else {
                    user.setRole(2);
                }

                Connection con=null;

                try{
                    con=dbUtil.getConnection();
                    User login=userDao.login(con,user);
                    currentUser=login;

                    if (login==null){
                        JOptionPane.showMessageDialog(null,"登陆失败");
                    }
                    else {
                        //1 用户 2 管理员
                        if(index==0){
                            JOptionPane.showMessageDialog(null,"此为管理员后台系统，普通用户无法登录");
                        }else {
                            jF.dispose();
                            new AdminMenuFrm();
                        }
                    }
                } catch (Exception e21) {
                    e21.printStackTrace();
                    JOptionPane.showMessageDialog(null,"登陆异常");
                }finally {
                    try {
                        dbUtil.closeCon(con);
                    }catch (Exception e31){
                        e31.printStackTrace();
                    }
                }
            }
        });


        jF.setVisible(true);
        }

    public static void main(String[] args) {
        new Login();
    }
}


