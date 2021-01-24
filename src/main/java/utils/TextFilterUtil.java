package utils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author admin
 */
public class TextFilterUtil {
    private static IPSeekerUtil ipSeeker;

    private final static String[] TF= {
            "disconnected",
            "connected",
            "Player",
            "has been kicked by BattlEye",
            "Client not responding",
            "Count Restriction",
            "Verified",
            "RCon admin",
            "logged in"
    };

    public static String TextualSubstitution(String text) throws Exception {
        String t = text;
        String regex = "(?<=\\()[^\\)]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(t);
        String data="";
        while (matcher.find()) {
            data=matcher.group(0);
        }
        String[] arr = data.split(":");

        if (arr.length>=2 && data.length()>=1){
            String ipAddress;
            if(ipSeeker == null){
                String property = System.getProperty("user.dir");
                String l = "";
                if ( System.getProperty("os.name").toLowerCase().contains("win")){
                    l = "\\";
                }else{
                    l = "//";
                }
                File file = new File(property+l+"qqwry.dat");
               // File file = new File("D:\\IP"+l+"qqwry.dat");
                ipSeeker = new IPSeekerUtil(file);
            }
            ipAddress = ipSeeker.getAddress(arr[0]);
            if (ipAddress == null || arr[0].length() <=1){
                ipAddress = "*.*.*.*";
            }
            t = t.replaceAll(data,ipAddress);
        }

        for (String str:TF) {
            switch (str){
                case "disconnected":
                    t = t.replaceAll(str,"断开服务器");
                    break;
                case "connected":
                    t = t.replaceAll(str,"已连接");
                    break;
                case "Player":
                    t = t.replaceAll(str,"玩家");
                    break;
                case "has been kicked by BattlEye":
                    t = t.replaceAll(str,"被反作弊踢出");
                    break;
                case "Client not responding":
                    t = t.replaceAll(str,"玩家客户端未回应!");
                    break;
                case "Count Restriction":
                    t = t.replaceAll(str,"函数调用超出限制!");
                    break;
                case "Verified":
                    t = t.replaceAll(str,"验证");
                    break;
                case "RCon admin":
                    t = t.replaceAll(str,"后台管理员");
                    break;
                case "logged in":
                    t = t.replaceAll(str,"已登录");
                    break;
                case "Query Timeout":
                    t = t.replaceAll(str,"查询超时");
                    break;
                default:

            }
        }
        return t;
    }
}
