package web.dataBasePacket.sql;

import sql.BasicSQLRunner;
import sql.SafeSQLInterface;
import web.dataBasePacket.Comment;
import web.dataBasePacket.DataBasePacketInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLRunnerComment extends BasicSQLRunner implements SafeSQLInterface {

    @Override
    public boolean set(DataBasePacketInterface dataBasePacketInterface) {
        Comment comment = (Comment)dataBasePacketInterface;
        int order_id = comment.order_id;
        String preSQL = "select * from comment where order_id=?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setInt(1,order_id);
            ResultSet result = st.executeQuery();
            return comment.setWithResultSet(result);
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean insert(DataBasePacketInterface dataBasePacketInterface) {
        Comment comment = (Comment)dataBasePacketInterface;
        String preSQL = "insert into comment (order_id,level,comment,create_time) values (?,?,?,?) ";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setInt(1,comment.order_id);
            st.setShort(2,comment.level);
            st.setString(3,comment.context);
            st.setLong(4,comment.create_time);
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
        Comment comment = (Comment)dataBasePacketInterface;
        String preSQL = "update comment set level=?,comment=?,create_time=? where order_id=? ";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setShort(1,comment.level);
            st.setString(2,comment.context);
            st.setLong(3,comment.create_time);
            st.setInt(4,comment.order_id);
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
        Comment comment = (Comment)dataBasePacketInterface;
        String preSQL = "delet from comment where order_id=? ";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setInt(1,comment.order_id);
            st.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
