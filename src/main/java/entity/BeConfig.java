package entity;

/**
 * @author admin
 */
public class BeConfig {

    private static String beIP;
    private static Integer bePort;
    private static String bePass;
    private static String serverQueryPort;
    private static String qqGroup;
    private static String roBotQQ;
    private static String qqServer;

    public static String getQqServer() {
        return qqServer;
    }

    public static void setQqServer(String qqServer) {
        BeConfig.qqServer = qqServer;
    }

    public static String getRoBotQQ() {
        return roBotQQ;
    }

    public static void setRoBotQQ(String roBotQQ) {
        BeConfig.roBotQQ = roBotQQ;
    }

    public static String getQqGroup() {
        return qqGroup;
    }

    public static void setQqGroup(String qqGroup) {
        BeConfig.qqGroup = qqGroup;
    }

    public static String getServerQueryPort() {
        return serverQueryPort;
    }

    public static void setServerQueryPort(String serverQueryPort) {
        BeConfig.serverQueryPort = serverQueryPort;
    }

    public static String getBeIP() {
        return beIP;
    }

    public static void setBeIP(String beIP) {
        BeConfig.beIP = beIP;
    }

    public static Integer getBePort() {
        return bePort;
    }

    public static void setBePort(Integer bePort) {
        BeConfig.bePort = bePort;
    }

    public static String getBePass() {
        return bePass;
    }

    public static void setBePass(String bePass) {
        BeConfig.bePass = bePass;
    }
}
