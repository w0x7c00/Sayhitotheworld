package web.dataBasePacket;

import web.dataBasePacket.sql.SQLRunnerUser;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User implements DataBasePacketInterface{
    //所有字段都不能为null
    public String user_name;
    public String password;
    public String email;
    public String name;
    public int balance;
    public long create_time;

    //rs为空或者不正确则返回false 否则返回true
    @Override
    public boolean setWithResultSet(ResultSet rs) {
        try {
            if(!rs.next()){
                return false;
            }
            this.user_name = rs.getString("user_name");
            this.password = rs.getString("password");
            this.email = rs.getString("email");
            this.name = rs.getString("name");
            this.balance = rs.getInt("balance");
            this.create_time = rs.getLong("create_time");
            return true;
        }
        catch (SQLException e){
            return false;
        }
    }

    //插入成功返回true 失败返回false
    @Override
    public boolean insert() {
        SQLRunnerUser sqlRunnerUser = new SQLRunnerUser();
        boolean result = sqlRunnerUser.insertUser(this);
        sqlRunnerUser.close();
        return result;
    }

    //一直返回true
    @Override
    public boolean update() {
        SQLRunnerUser sqlRunnerUser = new SQLRunnerUser();
        boolean result = sqlRunnerUser.updateUser(this);
        sqlRunnerUser.close();
        return result;
    }

    //一直返回true
    @Override
    public boolean delete() {
        SQLRunnerUser sqlRunnerUser = new SQLRunnerUser();
        boolean result = sqlRunnerUser.deleteUser(this);
        sqlRunnerUser.close();
        return result;
    }

    //成功返回true 失败返回false
    @Override
    public boolean set() {
        SQLRunnerUser sqlRunnerUser = new SQLRunnerUser();
        boolean result = sqlRunnerUser.setUser(this);
        sqlRunnerUser.close();
        return result;
    }
}