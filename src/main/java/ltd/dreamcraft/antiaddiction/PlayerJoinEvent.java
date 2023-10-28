package ltd.dreamcraft.antiaddiction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerJoinEvent implements Listener {
    @EventHandler
    public void playerJoinEvent(AsyncPlayerPreLoginEvent event) {
        //����Ƿ���ʱ��� �ҹ����Ƿ��� ��ʱ����Ҳ��ڰ���������Ҫ�߳�������
        System.out.println(method.isWithinTimeRange(ConfigManager.getStartTime(), ConfigManager.getEndTime()));
        if (method.isWithinTimeRange(ConfigManager.getStartTime(), ConfigManager.getEndTime()) && ConfigManager.getFunctionEnable()) {
            if (ConfigManager.getPluginMode().equalsIgnoreCase("whitelist")) {
                //������ģʽ
                if (!ConfigManager.getPlayerList().contains(event.getName().toLowerCase())) {
                    //���ڰ������� ��Ҫ�߳�������
                    String kickMessage = method.parseKickMessage(ConfigManager.getKickMessage());
                    event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, kickMessage);
                } else {
                    //�ڰ��������У�������������
                }
            } else if (ConfigManager.getPluginMode().equalsIgnoreCase("blacklist")) {
                //������ģʽ
                if (ConfigManager.getPlayerList().contains(event.getName().toLowerCase())) {
                    //�ں������� �����Խ��������
                    String kickMessage = method.parseKickMessage(ConfigManager.getKickMessage());
                    event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, kickMessage);
                } else {
                    //���ں��������У�������������
                }
            } else {
                AntiAddiction.instance.getLogger().warning("������ģʽ�������� - Setting.mode");
            }
        }
    }
}
