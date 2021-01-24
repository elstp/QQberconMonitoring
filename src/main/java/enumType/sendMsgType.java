package enumType;


/**
 * 机器人枚举类 群消息类型
 * @author admin
 */
public enum sendMsgType {

    /**
     * 发送好友文本信息
     */
    SendFriendTextMessage("{\n" +
            "    \"toUser\": %s,\n" +
            "    \"sendToType\": 1,\n" +
            "    \"groupid\": 0,\n" +
            "    \"content\": \"%s\",\n" +
            "    \"atUser\": 0,\n" +
            "    \"sendMsgType\": \"TextMsg\"\n" +
            "}"),

    /**
     * 发送好友图片信息
     */
    SendFriendPictureInformation("{\n" +
            "    \"toUser\": %s,\n" +
            "    \"sendToType\": 1,\n" +
            "    \"sendMsgType\": \"PicMsg\",\n" +
            "    \"content\": \"%s\",\n" +
            "    \"groupid\": 0,\n" +
            "    \"atUser\": 0,\n" +
            "    \"picUrl\": \"%s\",\n" +
            "    \"picBase64Buf\": \"\",\n" +
            "    \"fileMd5\": \"\"\n" +
            "}"),
    /**
     * 发送好友语音信息
     */
    SendFriendVoiceMessage("{\n" +
            "    \"toUser\": %s,\n" +
            "    \"sendToType\": 1,\n" +
            "    \"sendMsgType\": \"VoiceMsg\",\n" +
            "    \"content\": \"\",\n" +
            "    \"groupid\": 0,\n" +
            "    \"atUser\": 0,\n" +
            "    \"voiceUrl\": \"%s\",\n" +
            "    \"voiceBase64Buf\": \"\"\n" +
            "}"),

    /**
     * 发送群文本信息
     */
    SendGroupTextMessage("{\n" +
            " \"toUser\": %s,\n" +
            " \"sendToType\": 2,\n" +
            " \"sendMsgType\": \"TextMsg\",\n" +
            " \"content\": \"%s\",\n" +
            " \"groupid\": 0,\n" +
            " \"atUser\": 0\n" +
            "}"),
    /**
     * 发送群图片信息URL
     */
    SendGroupPictureInformation("{\n" +
            " \"toUser\": %s,\n" +
            " \"sendToType\": 2,\n" +
            " \"sendMsgType\": \"PicMsg\",\n" +
            " \"content\": \"%s\",\n" +
            " \"groupid\": 0,\n" +
            " \"atUser\": 0,\n" +
            " \"picUrl\": \"%s\",\n" +
            " \"picBase64Buf\": \"\",\n" +
            " \"fileMd5\": \"\"\n" +
            "}"),
    /**
     * 发送群图片信息URL
     */
    SendGroupBase64PictureInformation("{\n" +
            " \"toUser\": %s,\n" +
            " \"sendToType\": 2,\n" +
            " \"sendMsgType\": \"PicMsg\",\n" +
            " \"content\": \"%s\",\n" +
            " \"groupid\": 0,\n" +
            " \"atUser\": 0,\n" +
            " \"picUrl\": \"\",\n" +
            " \"picBase64Buf\": \"%s\",\n" +
            " \"fileMd5\": \"\",\n" +
            " \"flashPic\":false\n"+
            "}"),
    /**
     * 发送群语音信息
     */
    SendGroupVoiceMessage("{\n" +
            " \"toUser\": %s,\n" +
            " \"sendToType\": 2,\n" +
            " \"sendMsgType\": \"VoiceMsg\",\n" +
            " \"content\": \"\",\n" +
            " \"groupid\": 0,\n" +
            " \"atUser\": 0,\n" +
            " \"voiceUrl\": \"%s\",\n" +
            " \"voiceBase64Buf\": \"\"\n" +
            "}"),

    /**
     * 群内文本私聊
     */
    SendGroupPrivateChatTextMessage("{\n" +
            " \"toUser\": %s,\n" +
            " \"sendToType\": 3,\n" +
            " \"sendMsgType\": \"TextMsg\",\n" +
            " \"content\": \"%s\",\n" +
            " \"groupid\": %s,\n" +
            " \"atUser\": 0\n" +
            "}"),

    /**
     * 发送群内私聊语音消息
     */
    SendGroupPrivateVoiceMessage("{\n" +
            " \"toUser\": %s,\n" +
            " \"sendToType\": 3,\n" +
            " \"sendMsgType\": \"VoiceMsg\",\n" +
            " \"content\": \"\",\n" +
            " \"groupid\": %s,\n" +
            " \"atUser\": 0,\n" +
            " \"voiceUrl\": \"%s\",\n" +
            " \"voiceBase64Buf\": \"\"\n" +
            "}"),
    /**
     * 发送群内私聊图片
     */
    SendGroupPrivateChatPictureMessage("{\n" +
            "    \"toUser\": %s,\n" +
            "    \"sendToType\": 3,\n" +
            "    \"sendMsgType\": \"PicMsg\",\n" +
            "    \"content\": \"%s\",\n" +
            "    \"groupid\": 123456789,\n" +
            "    \"atUser\": 0,\n" +
            "    \"picUrl\": \"%s\",\n" +
            "    \"picBase64Buf\": \"\",\n" +
            "    \"fileMd5\": \"\"\n" +
            "}");


    private final String value;

    public String getValue() {
        return value;
    }

    sendMsgType(String value) {
        this.value = value;
    }
}

