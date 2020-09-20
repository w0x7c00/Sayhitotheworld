package tool;

import sql.BasicSQLRunner;
import web.email.BasicEmailTool;
import web.pay.AliPay;

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
        BasicSQLRunner.initSQL(properties.getProperty("SQLConnectStr"));

        //初始化目录：
        //1-EMAIL
        //2-ALYPAY
        //3-WECHATPAY
        return BasicEmailTool.initEmail(properties.getProperty("Email.smtphost"),properties.getProperty("Email.from"),properties.getProperty("Email.username"),properties.getProperty("Email.password"),properties.getProperty("Email.port"))&&
                AliPay.initAlipay(properties);
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