package web.dataBasePacket.sql;

import sql.BasicSQLRunner;
import sql.SafeSQLInterface;
import web.dataBasePacket.DataBasePacketInterface;
import web.dataBasePacket.Recharge;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLRunnerRecharge extends BasicSQLRunner implements SafeSQLInterface {

    @Override
    public boolean set(DataBasePacketInterface dataBasePacketInterface) {
        Recharge recharge = (Recharge)dataBasePacketInterface;
        int id = recharge.id;
        String preSQL = "select * from recharge where id=?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setInt(1,id);
            ResultSet result = st.executeQuery();
            return recharge.setWithResultSet(result);
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean insert(DataBasePacketInterface dataBasePacketInterface) {
        Recharge recharge = (Recharge)dataBasePacketInterface;
        String preSQL = "insert  into recharge (user_name,amount,create_time,state) values (?,?,?,?)";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,recharge.user_name);
            st.setInt(2,recharge.amount);
            st.setLong(3,recharge.create_time);
            st.setShort(4,recharge.state);
            st.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(DataBasePacketInterface dataBasePacketInterface) {
        Recharge recharge = (Recharge)dataBasePacketInterface;
        String preSQL = "update recharge set user_name=?,amount=?,create_time=?,state=? where id = ?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,recharge.user_name);
            st.setInt(2,recharge.amount);
            st.setLong(3,recharge.create_time);
            st.setShort(4,recharge.state);
            st.setInt(5,recharge.id);
            st.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(DataBasePacketInterface dataBasePacketInterface) {
        Recharge recharge = (Recharge)dataBasePacketInterface;
        String preSQL = "delete  from recharge where id = ?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setInt(1,recharge.id);
            st.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
