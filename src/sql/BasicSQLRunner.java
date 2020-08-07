//SQL工具基类
//last edit:2020-8-7
package sql;

import java.sql.*;

public class BasicSQLRunner {
    public static String sqlConnectStr = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&user=root&password=deng13508108659";
    public Connection con =null;
    //初始化mysql driver
    public static void initSQL(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public BasicSQLRunner(){
        try{
            con = DriverManager.getConnection(sqlConnectStr);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public ResultSet query(String sql_str){
        System.out.println("SQL do query:");
        System.out.println(sql_str);
        try{
            Statement st = con.createStatement();
            return st.executeQuery(sql_str);
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public boolean update(String sql_str){     //update delete insert均可以使用
        System.out.println("SQL do update:");
        System.out.println(sql_str);
        try{
            Statement st = con.createStatement();
            st.executeUpdate(sql_str);
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void close(){
        try{
            con.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    //LAST_INSERT_ID是基于Connection的，只要每个线程都使用独立的Connection对象，LAST_INSERT_ID函数将返回该Connection对AUTO_INCREMENT列最新的insert or update*作生成的第一个record的ID。这个值不能被其它客户端（Connection）影响，保证了你能够找回自己的 ID 而不用担心其它客户端的活动，而且不需要加锁。使用单INSERT语句插入多条记录, LAST_INSERT_ID返回一个列表。
    public int getLastInsertId(){    //使用事务时不能使用这个函数
        ResultSet rs = query("select LAST_INSERT_ID()");
        if (rs==null){
            return -1;
        }
        try{
            if(rs.next()){
                return rs.getInt(1);
            }
            else {
                return -1;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }
}
