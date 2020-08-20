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
//
//public String email;
//public String education;
//public String language;
//public String pic;
//public int age;
//public String self_introduction;
//
//public short second_language;
//public short country;
//public short country_to_live;
public class SQLRunnerTeacher extends BasicSQLRunner implements SafeSQLInterface {
    @Override
    public boolean insert(DataBasePacketInterface dataBasePacketInterface) {
        Teacher teacher = (Teacher)dataBasePacketInterface;
        String preSQL = "insert into teacher (teacher_name,password,sex,name,balance,price,create_time,state,email,education,language,pic,age,self_introduction,second_language,country,country_to_live) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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

            st.setString(9,teacher.email);
            st.setString(10,teacher.education);
            st.setShort(11,teacher.language);
            st.setString(12,teacher.pic);
            st.setInt(13,teacher.age);
            st.setString(14,teacher.self_introduction);
            st.setShort(15,teacher.second_language);
            st.setShort(16,teacher.country);
            st.setShort(17,teacher.country_to_live);

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
        String preSQL = "update teacher set password=?,sex=?,name=?,balance=?,price=?,create_time=?,state=?,email=?,education=?,language=?,pic=?,age=?,self_introduction=?,second_language=?,country=?,country_to_live=? where teacher_name=?";
        try{
            PreparedStatement st = con.prepareStatement(preSQL);
            st.setString(1,teacher.password);
            st.setShort(2,teacher.sex);
            st.setString(3,teacher.name);
            st.setInt(4,teacher.balance);
            st.setInt(5,teacher.price);
            st.setLong(6,teacher.create_time);
            st.setShort(7,teacher.state);

            st.setString(8,teacher.email);
            st.setString(9,teacher.education);
            st.setShort(10,teacher.language);
            st.setString(11,teacher.pic);
            st.setInt(12,teacher.age);
            st.setString(13,teacher.self_introduction);
            st.setShort(14,teacher.second_language);
            st.setShort(15,teacher.country);
            st.setShort(16,teacher.country_to_live);
            st.setString(17,teacher.teacher_name);
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
