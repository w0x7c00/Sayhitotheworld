package web.servlet.data.sql;

import sql.BasicSQLRunner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLRunnerForGetTeacherList extends BasicSQLRunner {
    //成功返回rs 数据库失败返回null（一般不可能的情况）
    public ResultSet getTeacherListRS(String preSQL,int begin_int,int length_int){
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setInt(1,begin_int);
            st.setInt(2,length_int);
            return st.executeQuery();
        }
        catch (SQLException e){
            return null;
        }
    }
}
