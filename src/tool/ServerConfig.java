package tool;

import sql.BasicSQLRunner;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerConfig {
    public static void initGlobalConfig(){
        BasicSQLRunner.initSQL();
    }
    private void initLogger(){
        //只记录warnning以上的日志
        Logger logger = Logger.getGlobal();
        logger.setLevel(Level.WARNING);
    }
}