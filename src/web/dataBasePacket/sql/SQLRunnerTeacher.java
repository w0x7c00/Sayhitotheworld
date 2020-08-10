package web.dataBasePacket.sql;

import sql.BasicSQLRunner;
import sql.SafeSQLInterface;
import web.dataBasePacket.DataBasePacketInterface;
import web.dataBasePacket.Teacher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//public String teacher_name;
//public String password;
//public short sex;
//public String name;
//public int balance;
//public int price;  //半小时价格
//public long create_time;
//public short state;
public class SQLRunnerTeacher extends BasicSQLRunner implements SafeSQLInterface {
    @Override
    public boolean insert(DataBasePacketInterface dataBasePacketInterface) {
        Teacher teacher = (Teacher)dataBasePacketInterface;
        String preSQL = "insert into teacher (teacher_name,password,sex,name,balance,price,create_time,state) values (?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,teacher.teacher_name);
            st.setString(2,teacher.password);
            st.setShort(3,teacher.sex);
            st.setString(4,teacher.name);
            st.setInt(5,teacher.balance);
            st.setInt(6,teacher.price);
            st.setLong(7,teacher.create_time);
            st.setShort(8,teacher.state);
            st.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(DataBasePacketInterface dataBasePacketInterface) {
        Teacher teacher = (Teacher)dataBasePacketInterface;
        String preSQL = "update teacher set password=?,sex=?,name=?,balance=?,price=?,create_time=?,state=? where teacher_name=?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,teacher.password);
            st.setShort(2,teacher.sex);
            st.setString(3,teacher.name);
            st.setInt(4,teacher.balance);
            st.setInt(5,teacher.price);
            st.setLong(6,teacher.create_time);
            st.setShort(7,teacher.state);
            st.setString(8,teacher.teacher_name);
            st.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(DataBasePacketInterface dataBasePacketInterface) {
        Teacher teacher = (Teacher)dataBasePacketInterface;
        String preSQL = "delete from teacher where teacher_name=?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,teacher.teacher_name);
            st.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean set(DataBasePacketInterface dataBasePacketInterface) {
        Teacher teacher = (Teacher)dataBasePacketInterface;
        String preSQL = "select * from teacher where teacher_name=?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,teacher.teacher_name);
            ResultSet rs = st.executeQuery();
            return teacher.setWithResultSet(rs);
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
