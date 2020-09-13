package tool;

import sql.BasicSQLRunner;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerConfig {
    private Properties properties;
    private String filePath;
    public ServerConfig(String FilePath){
        filePath = FilePath;
        properties = new Properties();
        try{
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
            this.properties.load(in);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public boolean loadConfigToServer(){
        return BasicSQLRunner.initSQL(properties.getProperty("SQLConnectStr"));
    }
    public boolean saveConfig(String comment){
        try{
            OutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));
            this.properties.store (out,comment);
            return true;
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    private void initLogger(){
        //只记录warnning以上的日志
        Logger logger = Logger.getGlobal();
        logger.setLevel(Level.WARNING);
    }
}