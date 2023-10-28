package ltd.dreamcraft.antiaddiction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerJoinEvent implements Listener {
    @EventHandler
    public void playerJoinEvent(AsyncPlayerPreLoginEvent event) {
        //检查是否在时间段 且功能是否开启 在时间段且不在白名单中需要踢出服务器
        System.out.println(method.isWithinTimeRange(ConfigManager.getStartTime(), ConfigManager.getEndTime()));
        if (method.isWithinTimeRange(ConfigManager.getStartTime(), ConfigManager.getEndTime()) && ConfigManager.getFunctionEnable()) {
            if (ConfigManager.getPluginMode().equalsIgnoreCase("whitelist")) {
                //白名单模式
                if (!ConfigManager.getPlayerList().contains(event.getName().toLowerCase())) {
                    //不在白名单中 需要踢出服务器
                    String kickMessage = method.parseKickMessage(ConfigManager.getKickMessage());
                    event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, kickMessage);
                } else {
                    //在白名单当中，允许进入服务器
                }
            } else if (ConfigManager.getPluginMode().equalsIgnoreCase("blacklist")) {
                //黑名单模式
                if (ConfigManager.getPlayerList().contains(event.getName().toLowerCase())) {
                    //在黑名单中 不可以进入服务器
                    String kickMessage = method.parseKickMessage(ConfigManager.getKickMessage());
                    event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, kickMessage);
                } else {
                    //不在黑名单当中，允许进入服务器
                }
            } else {
                AntiAddiction.instance.getLogger().warning("请检查插件模式的配置项 - Setting.mode");
            }
        }
    }
}
