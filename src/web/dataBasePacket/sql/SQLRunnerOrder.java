package web.dataBasePacket.sql;

import sql.BasicSQLRunner;
import sql.SafeSQLInterface;
import web.dataBasePacket.DataBasePacketInterface;
import web.dataBasePacket.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLRunnerOrder extends BasicSQLRunner implements SafeSQLInterface {
    @Override
    public boolean set(DataBasePacketInterface dataBasePacketInterface){
        Order order = (Order)dataBasePacketInterface;
        int order_id = order.order_id;
        String preSQL = "select * from order where order_id=?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setInt(1,order_id);
            ResultSet result = st.executeQuery();
            return order.setWithResultSet(result);
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
        Order order = (Order)dataBasePacketInterface;
        String preSQL = "update order set user_name=?,amount=?,state=?,teacher_name=?,create_time=?,email=? where order_id = ?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,order.user_name);
            st.setInt(2,order.amount);
            st.setShort(3,order.state);
            st.setString(4,order.teacher_name);
            st.setLong(5,order.create_time);
            st.setString(6,order.email);
            st.setInt(7,order.order_id);
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
        Order order = (Order)dataBasePacketInterface;
        int order_id = order.order_id;
        String preSQL = "delete from order where order_id=?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setInt(1,order_id);
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
        Order order = (Order)dataBasePacketInterface;
        String preSQL = "insert into order (user_name,amount,state,teacher_name,create_time,email) values (?,?,?,?,?,?) ";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,order.user_name);
            st.setInt(2,order.amount);
            st.setShort(3,order.state);
            st.setString(4,order.teacher_name);
            st.setLong(5,order.create_time);
            st.setString(6,order.email);
            st.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
