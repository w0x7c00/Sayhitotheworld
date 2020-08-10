package web.dataBasePacket;

import java.sql.ResultSet;

public class BasicDataBasePacket implements DataBasePacketInterface{
    @Override
    public boolean setWithResultSet(ResultSet rs) {
        return false;
    }

    @Override
    public boolean insert() {
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public boolean set() {
        return false;
    }
}
