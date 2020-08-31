package web.dataBasePacket;

import java.sql.ResultSet;

public interface DataBasePacketInterface {
    boolean setWithResultSet(ResultSet rs);
    boolean insert();
    boolean update();
    boolean delete();
    boolean set();
}