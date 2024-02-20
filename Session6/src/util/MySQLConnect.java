package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnect {
    public static Connection open(){
        try{
            //khai báo driver cho managerdriver cua mysql de jdbc có thể tương tác với mysql
            //com.mysql.cj.jdbc.Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/shop";
            String user = "root";
            String password = "ngoduy";
            Connection conn = DriverManager.getConnection(url,user,password);
            return conn;

        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void close(Connection conn){
        try{
            if(conn != null){
                conn.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
