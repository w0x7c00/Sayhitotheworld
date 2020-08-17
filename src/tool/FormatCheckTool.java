package tool;

public class FormatCheckTool extends BasicTool{
    public static boolean checkUserName(String userName){
        if(userName==null){
            return false;
        }
        return true;
    }
    public static boolean checkAdminName(String adminName){
        if(adminName==null){
            return false;
        }
        return true;
    }
    public static boolean checkTeacherName(String teacherName){
        if(teacherName==null){
            return false;
        }
        return true;
    }
    public static boolean checkPassword(String password){
        if (password==null){
            return false;
        }
        return true;
    }
    public static boolean checkName(String name){
        if(name==null){
            return false;
        }
        return true;
    }
    public static boolean checkEmail(String email){
        if(email==null){
            return false;
        }
        return true;
    }
    public static boolean checkEmailCode(String emailCode){
        if(emailCode==null){
            return false;
        }
        return true;
    }
    public static boolean checkEducation(String education){
        if(education==null){
            return false;
        }
        return true;
    }
    public static boolean checkPic(String pic){
        if(pic==null){
            return false;
        }
        return true;
    }
    public static boolean checkSelfIntroduction(String self_introduction){
        if(self_introduction==null){
            return false;
        }
        return true;
    }
    public static boolean checkTeacherEmail(String email){
        return email!=null;
    }
    public static boolean checkComment(String comment){
        return comment!=null;
    }
    public static boolean checkAmount(int amount){
        if(amount==-1){
            return false;
        }
        return true;
    }
    public static boolean checkLevel(int level){
        if(level==-1){
            return false;
        }
        if(level>=1 || level<=5){
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean checkAge(int age){
        if(age==-1){
            return false;
        }
        return true;
    }
    public static boolean checkSex(short sex){
        if(sex==1||sex==0||sex==2){
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean checkLanguage(short language){
        if(language!=1){
            return false;
        }
        return true;
    }
    public static boolean checkPrice(int price){
        if(price==-1){
            return false;
        }
        return true;
    }
    public static boolean checkCountry(short country){
        if(country==-1){
            return false;
        }
        return true;
    }
}