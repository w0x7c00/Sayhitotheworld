package tool;

public class FormatCheckTool extends BasicTool{
    public static boolean checkUserName(String userName){
        return true;
    }
    public static boolean checkPassword(String password){
        return true;
    }
    public static boolean checkName(String name){
        return true;
    }
    public static boolean checkEmail(String email){
        return true;
    }
    public static boolean checkLevel(int level){
        if(level>=1 || level<=5){
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean checkComment(String comment){
        return true;
    }
}
