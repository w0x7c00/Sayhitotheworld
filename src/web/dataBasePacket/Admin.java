package web.dataBasePacket;

import sql.SafeSQLInterface;
import web.dataBasePacket.sql.SQLRunnerAdmin;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin extends BasicDataBasePacket {
    public String admin_name;
    public String password;
    public short type;
    public long create_time;

    @Override
    public boolean setWithResultSet(ResultSet rs) {
        try {
            if(!rs.next()){
                return false;
            }
            this.admin_name = rs.getString("admin_name");
            this.password = rs.getString("password");
            this.type = rs.getShort("type");
            this.create_time = rs.getLong("create_time");
            return true;
        }
        catch (SQLException e){
            return false;
        }
    }

    @Override
    public boolean insert() {
        SQLRunnerAdmin sqlRunnerAdmin = new SQLRunnerAdmin();
        boolean result = sqlRunnerAdmin.insert(this);
        sqlRunnerAdmin.close();
        return result;
    }

    @Override
    public boolean update() {
        SQLRunnerAdmin sqlRunnerAdmin = new SQLRunnerAdmin();
        boolean result = sqlRunnerAdmin.update(this);
        sqlRunnerAdmin.close();
        return result;
    }

    @Override
    public boolean delete() {
        SQLRunnerAdmin sqlRunnerAdmin = new SQLRunnerAdmin();
        boolean result = sqlRunnerAdmin.delete(this);
        sqlRunnerAdmin.close();
        return result;
    }

    @Override
    public boolean set() {
        SQLRunnerAdmin sqlRunnerAdmin = new SQLRunnerAdmin();
        boolean result = sqlRunnerAdmin.set(this);
        sqlRunnerAdmin.close();
        return result;
    }
}