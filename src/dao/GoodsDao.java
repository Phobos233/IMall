package dao;

import model.goods;
import untils.toolUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GoodsDao {

    //添加商品
    public static int add(Connection con, goods good)throws Exception{

        //数据库执行语句
        String sql="insert into goods(g_id,g_name,g_type,g_brand,g_price,g_amount,g_introduction,g_salestatus) values(?,?,?,?,?,?,?,?)";

        //将读取的数据一一带入
        PreparedStatement pstmt=(PreparedStatement) con.prepareStatement(sql);
        pstmt.setInt(1,good.getGoodID());
        pstmt.setString(2,good.getGoodName());
        pstmt.setString(3,good.getType());
        pstmt.setString(4,good.getBrand());
        pstmt.setDouble(5,good.getPrice());
        pstmt.setInt(6,good.getAmount());
        pstmt.setString(7,good.getInfo());
        pstmt.setString(8,good.getSalestatus());

        return pstmt.executeUpdate();
    }

    //删除商品
    public int delete(Connection con, goods good)throws Exception{
        String sql="delete from goods where g_id=?";
        PreparedStatement p=(PreparedStatement) con.prepareStatement(sql);
        p.setInt(1,good.getGoodID());
        return p.executeUpdate();
    }

    //修改商品信息
    public static int update(Connection con, goods good)throws Exception{

        //数据库执行语句
        String sql="update goods set g_name=?,g_type=?,g_brand=?,g_price=?,g_amount=?,g_introduction=?,g_salestatus=? where g_id=?";

        PreparedStatement preparedStatement=(PreparedStatement) con.prepareStatement(sql);
        preparedStatement.setString(1,good.getGoodName());
        preparedStatement.setString(2,good.getType());
        preparedStatement.setString(3,good.getBrand());
        preparedStatement.setDouble(4,good.getPrice());
        preparedStatement.setInt(5,good.getAmount());
        preparedStatement.setString(6,good.getInfo());
        preparedStatement.setString(7,good.getSalestatus());
        preparedStatement.setInt(8,good.getGoodID());
        return preparedStatement.executeUpdate();
    }

//查找商品条目
    public ResultSet list(Connection con,goods good)throws Exception{
        //数据库执行语句
        StringBuilder s=new StringBuilder("select g.* FROM goods g ");

        //分字段查找
        if (!toolUtil.isEmpty(good.getGoodName())){
            s.append("where g.g_name like '%"+good.getGoodName()+"%'");
        }
        if (!toolUtil.isEmpty(good.getType())){
            s.append("where g.g_type like '%"+good.getType()+"%'");
        }
        if (!toolUtil.isEmpty(good.getBrand())){
            s.append("where g.g_brand like '%"+good.getBrand()+"%'");
        }
        if (good.getGoodID()!=null){
            s.append("where g.g_id= "+good.getGoodID());
        }
        s.append(" order by g.g_id");
        PreparedStatement p=con.prepareStatement(s.toString());
        return p.executeQuery();
    }
}
