package web.dataBasePacket.sql;

import sql.BasicSQLRunner;
import sql.SafeSQLInterface;
import web.dataBasePacket.DataBasePacketInterface;
import web.dataBasePacket.Redeem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLRunnerRedeem extends BasicSQLRunner implements SafeSQLInterface {
    @Override
    public boolean insert(DataBasePacketInterface dataBasePacketInterface) {
        Redeem redeem = (Redeem)dataBasePacketInterface;
        String preSQL = "insert into redeem (code,state,amount,create_time,used_time,type,append_inf) values (?,?,?,?,?,?,?)";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,redeem.code);
            st.setShort(2,redeem.state);
            st.setInt(3,redeem.amount);
            st.setLong(4,redeem.create_time);
            st.setLong(5,redeem.used_time);
            st.setShort(6,redeem.type);
            st.setString(7,redeem.append_inf);
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
        Redeem redeem = (Redeem)dataBasePacketInterface;
        String preSQL = "update redeem set code=?,state=?,amount=?,create_time=?,used_time=?,type=?,append_inf=? where id=?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,redeem.code);
            st.setShort(2,redeem.state);
            st.setInt(3,redeem.amount);
            st.setLong(4,redeem.create_time);
            st.setLong(5,redeem.used_time);
            st.setShort(6,redeem.type);
            st.setString(7,redeem.append_inf);
            st.setInt(8,redeem.id);
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
        Redeem redeem = (Redeem)dataBasePacketInterface;
        String preSQL = "delete from redeem where id=?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,redeem.append_inf);
            st.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean set(DataBasePacketInterface dataBasePacketInterface) {
        Redeem redeem = (Redeem)dataBasePacketInterface;
        String preSQL = "select * from redeem where id=?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setInt(1,redeem.id);
            ResultSet result = st.executeQuery();
            return redeem.setWithResultSet(result);
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean setWithCode(DataBasePacketInterface dataBasePacketInterface) {
        Redeem redeem = (Redeem)dataBasePacketInterface;
        String preSQL = "select * from redeem where code=?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,redeem.code);
            ResultSet result = st.executeQuery();
            return redeem.setWithResultSet(result);
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}