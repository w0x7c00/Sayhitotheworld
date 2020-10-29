package tool;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logging {
    private static String getTime(){
        String re;
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy ");
        re = date.format(formatter);
        formatter = DateTimeFormatter.ofPattern(" HH:mm:ss");
        re+=time.format(formatter);
        return re;
    }
    private static void doLog(String text){
        System.out.println(text);
    }
    public static void INFO(String sender,String event_desc){
        String time = getTime();
        String text;
        text = "[INFO]["+sender+"]"+"["+time+"]"+event_desc;
        doLog(text);
    }
    public static void WARNING(String sender,String event_desc){
        String time = getTime();
        String text;
        text = "[WARNING]["+sender+"]"+"["+time+"]"+event_desc;
        doLog(text);
    }
    public static void ERROR(String sender,String event_desc){
        String time = getTime();
        String text;
        text = "[ERROR]["+sender+"]"+"["+time+"]"+event_desc;
        doLog(text);
    }
}