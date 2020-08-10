package web.dataBasePacket;

import web.dataBasePacket.sql.SQLRunnerOrder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Order extends BasicDataBasePacket {
    public int order_id;
    public String user_name;
    public int amount;
    public short state;
    public long create_time;
    public String teacher_name;
    public String email;
    @Override
    public boolean setWithResultSet(ResultSet rs) {
        try {
            if(!rs.next()){
                return false;
            }
            this.user_name = rs.getString("user_name");
            this.order_id = rs.getInt("order_id");
            this.amount = rs.getInt("amount");
            this.state = rs.getShort("state");
            this.create_time = rs.getLong("create_time");
            this.teacher_name = rs.getString("teacher_name");
            this.email = rs.getString("email");
            return true;
        }
        catch (SQLException e){
            return false;
        }
    }

    @Override
    public boolean insert() {
        SQLRunnerOrder sqlRunnerOrder = new SQLRunnerOrder();
        boolean result = sqlRunnerOrder.insert(this);
        sqlRunnerOrder.close();
        return result;
    }

    @Override
    public boolean update() {
        SQLRunnerOrder sqlRunnerOrder = new SQLRunnerOrder();
        boolean result = sqlRunnerOrder.update(this);
        sqlRunnerOrder.close();
        return result;
    }

    @Override
    public boolean delete() {
        SQLRunnerOrder sqlRunnerOrder = new SQLRunnerOrder();
        boolean result = sqlRunnerOrder.delete(this);
        sqlRunnerOrder.close();
        return result;
    }

    @Override
    public boolean set() {
        SQLRunnerOrder sqlRunnerOrder = new SQLRunnerOrder();
        boolean result = sqlRunnerOrder.set(this);
        sqlRunnerOrder.close();
        return result;
    }
}
