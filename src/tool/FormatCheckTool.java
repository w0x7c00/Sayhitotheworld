package tool;
import java.util.regex.Pattern;
//注意 必须要检测null 否则会产生exception
public class FormatCheckTool extends BasicTool{
    private final static int MAX_PASSWORD_LENGTH = 25;
    private final static int MAX_PRICE = 20;
    private final static int MAX_LANGUAGE_CODE = 79;
    private final static int MAX_COUNTRY_CODE = 244;

    public static boolean checkUserName(String userName){
        return checkNotNull(userName)&&Pattern.matches("^[a-zA-Z0-9_]{6,25}$",userName);
    }
    public static boolean checkAdminName(String adminName){
        return checkUserName(adminName);
    }
    public static boolean checkTeacherName(String teacherName){
        return checkUserName(teacherName);
    }
    public static boolean checkPassword(String password){
        return checkNotNull(password)&&Pattern.matches("^.*(?=.{6,})(?=.*\\d)(?=.*[A-Za-z])(?=.*[!@#$%^&*?]*).*$",password)&&(password.length()<=MAX_PASSWORD_LENGTH);
    }
    public static boolean checkName(String name){
        return checkNotNull(name)&&Pattern.matches("^[^\\d]{2,254}$",name);
    }
    public static boolean checkEmail(String email){
        return checkNotNull(email)&&Pattern.matches("^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$",email);
    }
    public static boolean checkEmailCode(String emailCode){
        return checkNotNull(emailCode)&&Pattern.matches("^\\d{6}$",emailCode);
    }
    public static boolean checkEducation(String education){
        return checkNotNull(education)&&Pattern.matches("^.{1,100}$",education);
    }
    public static boolean checkPic(String pic){
        return checkNotNull(pic);
    }
    public static boolean checkSelfIntroduction(String self_introduction){
        return checkNotNull(self_introduction)&&Pattern.matches("^.{1,1000}$",self_introduction);
    }
    public static boolean checkTeacherEmail(String email){
        return checkEmail(email);
    }

    //评论不能为空
    public static boolean checkComment(String comment){
        return checkNotNull(comment)&&checkNotEmpty(comment);
    }
    public static boolean checkAmount(int amount){
        return amount != -1;
    }
    public static boolean checkLevel(int level){
        if(level==-1){
            return false;
        }
        return level >= 1 && level <= 5;
    }
    public static boolean checkAge(int age){
        return age >= 0 && age <= 120;
    }
    public static boolean checkSex(short sex){
        return sex == 1 || sex == 0 || sex == 2;
    }
    public static boolean checkLanguage(short language){
        return language >=0 && language<MAX_LANGUAGE_CODE;
    }
    public static boolean checkPrice(int price){
        return price>0&&price<=MAX_PRICE;
    }
    public static boolean checkCountry(short country){
        return country >=0&&country<MAX_COUNTRY_CODE;
    }
    public static boolean checkNotNull(String input){
        return input!=null;
    }
    public static boolean checkMaxLength(String input,int length){
        return input.length() <= length;
    }
    public static boolean checkNotEmpty(String input){
        return !input.equals("");
    }
    public static boolean checkTeacherApplyAppendInf(String input){
        return checkNotNull(input)&&checkMaxLength(input,1000);
    }
}