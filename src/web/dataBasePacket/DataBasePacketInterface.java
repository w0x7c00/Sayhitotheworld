package web.dataBasePacket;

import java.sql.ResultSet;

public interface DataBasePacketInterface {
    public boolean setWithResultSet(ResultSet rs);
    public boolean insert();
    public boolean update();
    public boolean delete();
    public boolean set();
}