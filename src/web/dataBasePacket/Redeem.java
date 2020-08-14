package web.dataBasePacket;

import web.dataBasePacket.sql.SQLRunnerRedeem;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Redeem extends BasicDataBasePacket {
    public int id;
    public String code;  //255-1最大长度
    public short state;
    public int amount;
    public long create_time;
    public long used_time;
    public short type;
    public String append_inf;

    @Override
    public boolean setWithResultSet(ResultSet rs) {
        try {
            if(!rs.next()){
                return false;
            }
            this.id = rs.getInt("id");
            this.code = rs.getString("code");
            this.state = rs.getShort("state");
            this.amount = rs.getInt("amount");
            this.create_time = rs.getLong("create_time");
            this.used_time = rs.getLong("used_time");
            this.type = rs.getShort("type");
            this.append_inf = rs.getString("append_inf");
            return true;
        }
        catch (SQLException e){
            return false;
        }
    }

    @Override
    public boolean set() {
        SQLRunnerRedeem sqlRunnerRedeem = new SQLRunnerRedeem();
        boolean result = sqlRunnerRedeem.set(this);
        sqlRunnerRedeem.close();
        return result;
    }

    //扩展功能
    //通过兑换码查询数据
    public boolean setWithCode(){
        SQLRunnerRedeem sqlRunnerRedeem = new SQLRunnerRedeem();
        boolean result = sqlRunnerRedeem.setWithCode(this);
        sqlRunnerRedeem.close();
        return result;
    }

    @Override
    public boolean insert() {
        SQLRunnerRedeem sqlRunnerRedeem = new SQLRunnerRedeem();
        boolean result = sqlRunnerRedeem.insert(this);
        sqlRunnerRedeem.close();
        return result;
    }

    @Override
    public boolean update() {
        SQLRunnerRedeem sqlRunnerRedeem = new SQLRunnerRedeem();
        boolean result = sqlRunnerRedeem.update(this);
        sqlRunnerRedeem.close();
        return result;
    }

    @Override
    public boolean delete() {
        SQLRunnerRedeem sqlRunnerRedeem = new SQLRunnerRedeem();
        boolean result = sqlRunnerRedeem.delete(this);
        sqlRunnerRedeem.close();
        return result;
    }
}