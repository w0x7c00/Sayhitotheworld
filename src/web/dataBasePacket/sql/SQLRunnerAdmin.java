package web.dataBasePacket.sql;

import sql.BasicSQLRunner;
import sql.SafeSQLInterface;
import web.dataBasePacket.Admin;
import web.dataBasePacket.DataBasePacketInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLRunnerAdmin extends BasicSQLRunner implements SafeSQLInterface {
    @Override
    public boolean set(DataBasePacketInterface dataBasePacketInterface){
        Admin admin = (Admin)dataBasePacketInterface;
        String admin_name = admin.admin_name;
        String preSQL = "select * from admin where admin_name=?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,admin_name);
            ResultSet result = st.executeQuery();
            return admin.setWithResultSet(result);
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
        Admin admin = (Admin)dataBasePacketInterface;
        String preSQL = "update user set password=?,create_time=?,admin_name=?,type=? where user_name = ?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,admin.password);
            st.setLong(2,admin.create_time);
            st.setString(3,admin.admin_name);
            st.setShort(4,admin.type);
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
        Admin admin = (Admin)dataBasePacketInterface;
        String admin_name = admin.admin_name;
        String preSQL = "delete from admin where admin_name=?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,admin_name);
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
        Admin admin = (Admin)dataBasePacketInterface;
        String preSQL = "insert into admin (admin_name,password,type,create_time) values (?,?,?,?)";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,admin.admin_name);
            st.setString(2,admin.password);
            st.setShort(3,admin.type);
            st.setLong(4,admin.create_time);
            st.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}