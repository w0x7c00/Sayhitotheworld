package web.servlet.init;

import sql.BasicSQLRunner;
import tool.BasicTool;
import tool.ServerConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/init")
public class Init extends HttpServlet {
    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicTool.setGetPageEncoding(req, resp);
        String filePath = getServletContext().getRealPath("/WEB-INF/")+"server.properties";
        if(new ServerConfig(filePath).loadConfigToServer()) {
            resp.getWriter().write("Init Success！");
        }
        else{
            resp.getWriter().write("ERRO:Load Config Fail！");
        }
    }
}