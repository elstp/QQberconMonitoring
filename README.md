# QQberconMonitoring

## # Arma3 OPQ QQ框架机器人 BERcon消息监听插件源代码

本插件遵循并使用BattlEye RCon协议规范v2

> 监听服务器消息 玩家退出，加入，聊天自动发送到QQ群
BE RCON断线重连
服务器信息查询等功能实时推送


##### 开始使用:
1.首先去OPQ机器人的github下载机器人 然后配置好机器人
然后打包本程序然后再创建一个名为config.yml的配置文件
配置节点:
```yaml
setting:
     beIP: BE的IP地址
     bePort: BE的端口
     bePass: 被的密码
     serverQueryPort: 服务器查询端口
     qqGroup: QQ群
     roBotQQ: 机器人QQ
     qqServer: QQ服务器HTTP API地址(???/v1/LuaApiCaller)
```
本程序需要Java环境11版本以上才可以运行!


将配置文件放置与插件jar包同一目录下启动无报错插件即各种正常!
