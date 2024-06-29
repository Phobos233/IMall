package dao;

import model.User;
import model.goods;
import untils.toolUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UserDao {

    //登陆检测
    public User login(Connection con,User user) throws Exception{
        User resultUser=null;
        String sql="select * from user where username=? and password=? and role=?";

        PreparedStatement pstmt=(PreparedStatement) con.prepareStatement(sql);
        pstmt.setString(1,user.getUsername());
        pstmt.setString(2,user.getPassword());
        pstmt.setInt(3,user.getRole());

        ResultSet rs=pstmt.executeQuery();

        if (rs.next()){
            resultUser=new User();
            resultUser.setUserID(rs.getInt("id"));
            resultUser.setUsername(rs.getString("username"));
            resultUser.setSex(rs.getString("sex"));
            resultUser.setP_num(rs.getString("p_num"));
        }

        return resultUser;
    }

    //添加用户
    public static int add(Connection con, User user)throws Exception{
        String sql="insert into user(id,username,password,role,sex,p_num) values(?,?,?,?,?,?)";
        PreparedStatement preparedStatement=(PreparedStatement) con.prepareStatement(sql);
        preparedStatement.setInt(1,user.getUserID());
        preparedStatement.setString(2,user.getUsername());
        preparedStatement.setString(3,user.getPassword());
        preparedStatement.setInt(4,user.getRole());
        preparedStatement.setString(5,user.getSex());
        preparedStatement.setString(6,user.getP_num());
        return preparedStatement.executeUpdate();
    }

    //修改用户
    public static int update(Connection con, User user)throws Exception{
        String sql="update user set username=?,password=?,role=?,sex=?,p_num=? where id=?";
        PreparedStatement preparedStatement=(PreparedStatement) con.prepareStatement(sql);
        preparedStatement.setString(1,user.getUsername());
        preparedStatement.setString(2,user.getPassword());
        preparedStatement.setInt(3,user.getRole());
        preparedStatement.setString(4,user.getSex());
        preparedStatement.setString(5,user.getP_num());
        preparedStatement.setInt(6,user.getUserID());
        return preparedStatement.executeUpdate();
    }

    //删除用户
    public int delete(Connection con, User user)throws Exception{
        String sql="delete from user where id=?";
        PreparedStatement p=(PreparedStatement) con.prepareStatement(sql);
        p.setInt(1,user.getUserID());
        return p.executeUpdate();
    }

    //查找用户
    public ResultSet list(Connection con, User user) throws Exception{
        StringBuilder s=new StringBuilder("select u.* FROM user u ");

        if (!toolUtil.isEmpty(user.getUsername())){
            s.append("where u.username like '%"+user.getUsername()+"%'");
        }
        if (user.getUserID()!=null){
            s.append("where u.id= "+user.getUserID());
        }
        if (user.getRole()!=null){
            s.append("where u.role= "+user.getRole());
        }
        if (!toolUtil.isEmpty(user.getP_num())){
            s.append("where u.p_num like '%"+user.getP_num()+"%'");
        }
        s.append(" order by u.id");
        PreparedStatement p=con.prepareStatement(s.toString());
        return p.executeQuery();
    }
}
