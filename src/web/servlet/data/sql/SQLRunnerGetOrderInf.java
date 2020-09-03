package web.servlet.data.sql;

import sql.BasicSQLRunner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLRunnerGetOrderInf extends BasicSQLRunner {
    public ResultSet getAllOrderByTeacherName(String teacher_name){
        String preSQL = "select * from order where teacher_name=?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,teacher_name);
            return st.executeQuery();
        }
        catch (SQLException e){
            return null;
        }
    }
}
