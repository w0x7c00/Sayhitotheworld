package tool;

public class FormatCheckTool extends BasicTool{
    public static boolean checkUserName(String userName){
        if(userName==null){
            System.out.println("user_name");
            return false;
        }
        return true;
    }
    public static boolean checkAdminName(String adminName){
        if(adminName==null){
            System.out.println("admin_name");
            return false;
        }
        return true;
    }
    public static boolean checkTeacherName(String teacherName){
        if(teacherName==null){
            System.out.println("teacherName");
            return false;
        }
        return true;
    }
    public static boolean checkPassword(String password){
        if (password==null){
            System.out.println("password");
            return false;
        }
        return true;
    }
    public static boolean checkName(String name){
        if(name==null){
            System.out.println("name");
            return false;
        }
        return true;
    }
    public static boolean checkEmail(String email){
        if(email==null){
            System.out.println("email");
            return false;
        }
        return true;
    }
    public static boolean checkEmailCode(String emailCode){
        if(emailCode==null){
            System.out.println("emailcode");
            return false;
        }
        return true;
    }
    public static boolean checkEducation(String education){
        if(education==null){
            System.out.println("education");
            return false;
        }
        return true;
    }
    public static boolean checkPic(String pic){
        if(pic==null){
            System.out.println("pic");
            return false;
        }
        return true;
    }
    public static boolean checkSelfIntroduction(String self_introduction){
        if(self_introduction==null){
            System.out.println("self");
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
        if(language==-1){
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