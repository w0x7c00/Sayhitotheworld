package web.servlet.logAndRegister.sql;

import sql.BasicSQLRunner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSearchEmail extends BasicSQLRunner {
    public boolean checkEmailIsAvailable(String email){
        String preSQL = "select * from user where email = ?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,email);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                //已经存在
                return false;
            }
            else {
                //不存在
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}