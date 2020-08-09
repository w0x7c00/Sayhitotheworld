package web.sessionPacket;

import web.dataBasePacket.User;
import web.dataBasePacket.sql.SQLRunnerUser;

public class UserSessionPacket extends User implements SessionPacketInterface {
    public boolean setByEmail() {
        SQLRunnerUser sqlRunnerUser = new SQLRunnerUser();
        boolean result = sqlRunnerUser.setByEmailUser(this);
        sqlRunnerUser.close();
        return result;
    }
}