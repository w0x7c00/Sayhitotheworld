package web.dataBasePacket;

import web.dataBasePacket.sql.SQLRunnerTeacher;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Teacher extends BasicDataBasePacket{
    public String teacher_name;
    public String password;
    public short sex;
    public String name;
    public int balance;
    public int price;  //半小时价格
    public long create_time;
    public short state;

    public String email;
    public String education;
    public short language;
    public String pic;
    public int age;
    public String self_introduction;

    public short second_language;
    public short country;
    public short country_to_live;

    public String append_inf;
    public String self_video;

    @Override
    public boolean setWithResultSet(ResultSet rs) {
        try {
            if(!rs.next()){
                return false;
            }
            this.teacher_name = rs.getString("teacher_name");
            this.password = rs.getString("password");
            this.sex = rs.getShort("sex");
            this.name = rs.getString("name");
            this.balance = rs.getInt("balance");
            this.price = rs.getInt("price");
            this.create_time = rs.getLong("create_time");
            this.state = rs.getShort("state");

            this.email = rs.getString("email");
            this.education = rs.getString("education");
            this.language = rs.getShort("language");
            this.pic = rs.getString("pic");
            this.age = rs.getInt("age");
            this.self_introduction = rs.getString("self_introduction");

            this.country = rs.getShort("country");
            this.second_language = rs.getShort("second_language");
            this.country_to_live = rs.getShort("country_to_live");
            this.append_inf = rs.getString("append_inf");
            this.self_video = rs.getString("self_video");
            return true;
        }
        catch (SQLException e){
            return false;
        }
    }

    @Override
    public boolean set() {
        SQLRunnerTeacher sqlRunnerTeacher = new SQLRunnerTeacher();
        boolean result = sqlRunnerTeacher.set(this);
        sqlRunnerTeacher.close();
        return result;
    }

    public boolean setWithEmail(){
        SQLRunnerTeacher sqlRunnerTeacher = new SQLRunnerTeacher();
        boolean result = sqlRunnerTeacher.setWithEmail(this);
        sqlRunnerTeacher.close();
        return result;
    }

    @Override
    public boolean insert() {
        SQLRunnerTeacher sqlRunnerTeacher = new SQLRunnerTeacher();
        boolean result = sqlRunnerTeacher.insert(this);
        sqlRunnerTeacher.close();
        return result;
    }

    @Override
    public boolean update() {
        SQLRunnerTeacher sqlRunnerTeacher = new SQLRunnerTeacher();
        boolean result = sqlRunnerTeacher.update(this);
        sqlRunnerTeacher.close();
        return result;
    }

    @Override
    public boolean delete() {
        SQLRunnerTeacher sqlRunnerTeacher = new SQLRunnerTeacher();
        boolean result = sqlRunnerTeacher.delete(this);
        sqlRunnerTeacher.close();
        return result;
    }
}