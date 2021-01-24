package Service;

import SteamServerQuery.SteamServerQuery;
import SteamServerQuery.SteamServerInfo;
import com.alibaba.fastjson.JSONObject;
import enumType.sendMsgType;
import entity.BeConfig;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import sk.mlobb.be.rcon.BERconClient;
import sk.mlobb.be.rcon.model.BELoginCredential;
import sk.mlobb.be.rcon.model.configuration.BERconConfiguration;
import sk.mlobb.be.rcon.model.exception.BERconException;
import utils.GetDate;
import utils.HttpClientUtil;
import utils.TextFilterUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Future;

/**
 * @author admin
 */
public class BeServiceImpl implements Runnable {
    private Charset charset= Charset.forName("utf-8");


    @Override
    public void run() {
         BERconClient BE = null;
        InetSocketAddress hostAddress = new InetSocketAddress(BeConfig.getBeIP(), BeConfig.getBePort());
        BELoginCredential loginCredential = new BELoginCredential(hostAddress, BeConfig.getBePass());
        BERconConfiguration beRconConfiguration = new BERconConfiguration();
        String url = BeConfig.getQqServer()+"/v1/LuaApiCaller?qq="+ BeConfig.getRoBotQQ()+"&funcname=SendMsg&timeout=10";

        try{
            //设置连接延迟
           //   beRconConfiguration.setConnectionDelay((long)3);
            // 设置保持活动时间
         //   beRconConfiguration.setKeepAliveTime(20000L);
            //设置be超时
         //    beRconConfiguration.setTimeoutTime((long)10);
            BE = new BERconClient(beRconConfiguration);
            BE.connect(loginCredential);
            for (int i = 0;;i++){
                Thread.sleep(1000);
                System.out.println("等待连接...");
                if (BE.getConnected().get()) {
                    System.out.println("已连接!");
                    break;
                }
                if (i >=10) {
                    throw new BERconException("连接失败!");
                }
            }

            BE.addResponseHandler(response -> {
                try {

                    String msg = TextFilterUtil.TextualSubstitution(response);
                    String data = "";
                    JSONObject jsonObject =  new JSONObject();

                    if (!msg.contains("已登录") && !msg.contains("验证 GUID") && !msg.contains("- BE GUID")) {
                        if (msg.contains("已连接") || msg.contains("断开服务器") || msg.contains("被反作弊踢出") || msg.contains("查询超时")){
                            SteamServerQuery ServerQuery = new SteamServerQuery(BeConfig.getBeIP()+":"+BeConfig.getServerQueryPort());
                            SteamServerInfo ServerInfo = ServerQuery.getInfo();
                            data = " ("+(ServerInfo.getPlayers()+1)+"/"+ServerInfo.getMaxPlayers()+")";
                        }

                        jsonObject.put("toUser",Long.parseLong(BeConfig.getQqGroup()));
                        jsonObject.put("sendToType",2);
                        jsonObject.put("sendMsgType","TextMsg");
                        jsonObject.put("content",msg+data);
                        jsonObject.put("groupid",0);
                        jsonObject.put("atUser",0);

                        System.out.println(url);
                        System.out.println(jsonObject.toJSONString());
                        String sendData = jsonObject.toJSONString();
                        if (jsonObject.size()<=1) {
                            sendData = msg;
                        }
                        HttpClientUtil.HttpClientRequest post = HttpClientUtil.getInstance().setContentType("application/json").post(url,sendData);
                        System.out.println("HttpClientRequest:"+post.getReponseContent());
                    }

                    System.out.println(GetDate.GetMUDate()+msg+data);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            //死循环检测,被T下线自动重连
            while(true){
                Thread.sleep(1000);
                if (!BE.getConnected().get() &&
                        (!"RUNNABLE".equals(Thread.currentThread().getState().toString())||
                                !"TIMED_WAITING".equals(Thread.currentThread().getState().toString()))){
                    System.out.println(GetDate.GetMDate()+"已断线!");
                    throw new BERconException("下线或线程问题");
                }
            }

        }catch (Exception e){
            System.out.println("异常:"+e.getMessage());
           HttpClientUtil.getInstance().setContentType("application/json").post(url,"[严重]程序出现错误!请联系1179163813解决.堆栈信息:"+getStackTrace(e));
            e.printStackTrace();
        }
    }

    /**
     * 获取堆栈信息
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }

}
