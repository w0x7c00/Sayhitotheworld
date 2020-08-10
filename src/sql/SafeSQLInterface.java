package sql;

import web.dataBasePacket.DataBasePacketInterface;

public interface SafeSQLInterface {
    public boolean insert(DataBasePacketInterface dataBasePacketInterface);
    public boolean update(DataBasePacketInterface dataBasePacketInterface);
    public boolean delete(DataBasePacketInterface dataBasePacketInterface);
    public boolean set(DataBasePacketInterface dataBasePacketInterface);
}
