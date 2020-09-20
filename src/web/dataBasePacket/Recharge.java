package web.dataBasePacket;

import web.dataBasePacket.sql.SQLRunnerOrder;
import web.dataBasePacket.sql.SQLRunnerRecharge;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Recharge extends BasicDataBasePacket{
    public int id;
    public String user_name;
    public int amount;
    public long create_time;
    public short state;

    @Override
    public boolean setWithResultSet(ResultSet rs) {
        try {
            if(!rs.next()){
                return false;
            }
            this.user_name = rs.getString("user_name");
            this.id = rs.getInt("order_id");
            this.amount = rs.getInt("amount");
            this.state = rs.getShort("state");
            this.create_time = rs.getLong("create_time");
            return true;
        }
        catch (SQLException e){
            return false;
        }
    }

    @Override
    public boolean set() {
        SQLRunnerRecharge sqlRunnerRecharge = new SQLRunnerRecharge();
        boolean result = sqlRunnerRecharge.set(this);
        sqlRunnerRecharge.close();
        return result;
    }

    @Override
    public boolean update() {
        SQLRunnerRecharge sqlRunnerRecharge = new SQLRunnerRecharge();
        boolean result = sqlRunnerRecharge.update(this);
        sqlRunnerRecharge.close();
        return result;
    }

    @Override
    public boolean delete() {
        SQLRunnerRecharge sqlRunnerRecharge = new SQLRunnerRecharge();
        boolean result = sqlRunnerRecharge.delete(this);
        sqlRunnerRecharge.close();
        return result;
    }

    @Override
    public boolean insert() {
        SQLRunnerRecharge sqlRunnerRecharge = new SQLRunnerRecharge();
        boolean result = sqlRunnerRecharge.insert(this);
        sqlRunnerRecharge.close();
        return result;
    }
}
