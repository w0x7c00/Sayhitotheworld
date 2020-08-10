//sql tool for class User
//last edit:2020-8-7
package web.dataBasePacket.sql;

import sql.BasicSQLRunner;
import sql.SafeSQLInterface;
import web.dataBasePacket.DataBasePacketInterface;
import web.dataBasePacket.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLRunnerUser extends BasicSQLRunner implements SafeSQLInterface {
    //通过user的user_name查询表
    //不存在时返回false
    @Override
    public boolean set(DataBasePacketInterface dataBasePacketInterface){
        User user = (User)dataBasePacketInterface;
        String user_name = user.user_name;
        String preSQL = "select * from user where user_name=?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,user_name);
            ResultSet result = st.executeQuery();
            return user.setWithResultSet(result);
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //用于UserSessionPacket的扩展功能
    public boolean setByEmailUser(DataBasePacketInterface dataBasePacketInterface){
        User user = (User)dataBasePacketInterface;
        String email = user.email;
        String preSQL = "select * from user where email=?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,email);
            ResultSet result = st.executeQuery();
            return user.setWithResultSet(result);
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //当更新不存在的列时是不会产生SQLException的
    //除非数据库出现问题否则只会返回true
    @Override
    public boolean update(DataBasePacketInterface dataBasePacketInterface){
        User user = (User)dataBasePacketInterface;
        String preSQL = "update user set password=?,email=?,name=?,balance=?,create_time=? where user_name = ?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,user.password);
            st.setString(2,user.email);
            st.setString(3,user.name);
            st.setInt(4,user.balance);
            st.setLong(5,user.create_time);
            st.setString(6,user.user_name);
            st.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //删除不存在的列时是不会产生SQLException的
    //除非数据库出现问题否则只会返回true
    @Override
    public boolean delete(DataBasePacketInterface dataBasePacketInterface){
        User user = (User)dataBasePacketInterface;
        String user_name = user.user_name;
        String preSQL = "delete from user where user_name=?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,user_name);
            st.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //不存在列时成功插入返回true
    //不成功插入返回false
    @Override
    public boolean insert(DataBasePacketInterface dataBasePacketInterface){
        User user = (User)dataBasePacketInterface;
        String preSQL = "insert into user (user_name,password,email,name,balance,create_time) values (?,?,?,?,?,?)";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,user.user_name);
            st.setString(2,user.password);
            st.setString(3,user.email);
            st.setString(4,user.name);
            st.setInt(5,user.balance);
            st.setLong(6,user.create_time);
            st.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}