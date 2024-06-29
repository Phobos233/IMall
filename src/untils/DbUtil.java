package untils;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
public class DbUtil {
    private static String db_Driver="com.mysql.cj.jdbc.Driver";
    private static String db_username="root";
    private static String db_password="Phobos3@Guet";
    public static Connection getConnection() throws Exception{
        Class.forName(db_Driver);
        Connection con=(Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/imall",db_username,db_password) ;
        return con;
    }
    public static void closeCon(Connection con)throws Exception{
        if (con!=null) con.close();
    }
}
