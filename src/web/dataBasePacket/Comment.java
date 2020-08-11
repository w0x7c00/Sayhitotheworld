package web.dataBasePacket;

import web.dataBasePacket.sql.SQLRunnerComment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Comment extends BasicDataBasePacket{
    public int order_id;
    public String context;
    public long create_time;
    public short level;

    @Override
    public boolean setWithResultSet(ResultSet rs) {
        try {
            if(!rs.next()){
                return false;
            }
            this.order_id = rs.getInt("order_id");
            this.level = rs.getShort("level");
            this.create_time = rs.getLong("create_time");
            this.context = rs.getString("context");
            return true;
        }
        catch (SQLException e){
            return false;
        }
    }

    @Override
    public boolean set() {
        SQLRunnerComment sqlRunnerComment = new SQLRunnerComment();
        boolean result = sqlRunnerComment.set(this);
        sqlRunnerComment.close();
        return result;
    }

    @Override
    public boolean insert() {
        SQLRunnerComment sqlRunnerComment = new SQLRunnerComment();
        boolean result = sqlRunnerComment.insert(this);
        sqlRunnerComment.close();
        return result;
    }

    @Override
    public boolean update() {
        SQLRunnerComment sqlRunnerComment = new SQLRunnerComment();
        boolean result = sqlRunnerComment.update(this);
        sqlRunnerComment.close();
        return result;
    }

    @Override
    public boolean delete() {
        SQLRunnerComment sqlRunnerComment = new SQLRunnerComment();
        boolean result = sqlRunnerComment.delete(this);
        sqlRunnerComment.close();
        return result;
    }
}