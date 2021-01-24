import Service.BeServiceImpl;

import entity.BeConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;


/**
 * @author admin
 */
public class DestinyMain {


    public static void main(String[] args)   {
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void start() throws Exception {
        Yaml yaml = new Yaml();
        String property = System.getProperty("user.dir");
        String l = "";
        if ( System.getProperty("os.name").toLowerCase().contains("win")){
            l = "\\";
        }else{
            l = "//";
        }
        InputStream input = new FileInputStream(property+l+"config.yml");
        Map<String, Object> object = (Map<String, Object>) yaml.load(input);
        Map<String, Object>  setting = (Map<String, Object>) object.get("setting");
        BeConfig.setBeIP(setting.get("BeIP").toString());
        BeConfig.setBePass(setting.get("BePass").toString());
        BeConfig.setBePort(Integer.parseInt( setting.get("BePort").toString()));
        BeConfig.setServerQueryPort(setting.get("ServerQueryPort").toString());
        BeConfig.setQqGroup(setting.get("QqGroup").toString());
        BeConfig.setRoBotQQ(setting.get("RoBotQQ").toString());
        BeConfig.setQqServer(setting.get("QqServer").toString());
        BeServiceImpl beService = new BeServiceImpl();
        Thread thread = new Thread(beService);
        thread.start();

        while(true){
            Thread.sleep(10000);
            System.out.println(thread.getState());
            if (!thread.getState().toString().equals("RUNNABLE") &&
                    !thread.getState().toString().equals("TIMED_WAITING")){
                System.out.println("创建新新线程...");
                thread.interrupt();
                beService = new BeServiceImpl();
                thread = new Thread(beService);
                thread.start();
            }
        }

    }


}
