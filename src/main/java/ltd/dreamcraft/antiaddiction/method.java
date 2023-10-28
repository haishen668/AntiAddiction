package ltd.dreamcraft.antiaddiction;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public class method {
    /**
     * ����Ƿ���ʱ�䷶Χ��
     *
     * @param startTime ��ʼʱ��
     * @param endTime   ����ʱ��
     * @return ��ʱ�䷶Χ�ڷ���true, ��֮����false
     */
    public static boolean isWithinTimeRange(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        try {
            ZoneId zone = ZoneId.of("Asia/Shanghai"); // ѡ���������ʱ��
            ZonedDateTime currentTime = ZonedDateTime.now(zone);
            Date startTimeParsed = sdf.parse(startTime);
            Date endTimeParsed = sdf.parse(endTime);

            // ת����ǰʱ��Ϊ LocalTime
            LocalTime currentTimeLocal = currentTime.toLocalTime();

            // ת����ʼʱ��ͽ���ʱ��Ϊ LocalTime
            LocalTime startTimeLocal = startTimeParsed.toInstant().atZone(zone).toLocalTime();
            LocalTime endTimeLocal = endTimeParsed.toInstant().atZone(zone).toLocalTime();

            // ��鵱ǰʱ���Ƿ���ʱ�䷶Χ�ڣ������Խ��ҹ�����
            if (startTimeLocal.isBefore(endTimeLocal)) {
                // ����Խ��ҹ�����
                return currentTimeLocal.isAfter(startTimeLocal) && currentTimeLocal.isBefore(endTimeLocal);
            } else {
                // ��Խ��ҹ�����
                return currentTimeLocal.isAfter(startTimeLocal) || currentTimeLocal.isBefore(endTimeLocal);
            }

        } catch (ParseException e) {
            return false; // �����쳣ʱ��Ĭ�ϲ��ڷ�Χ��
        }
    }


    public static String parseKickMessage(String kickMessage) {
        kickMessage = kickMessage.replace("%start%", ConfigManager.getStartTime());
        kickMessage = kickMessage.replace("%end%", ConfigManager.getEndTime());
        return ChatColor.translateAlternateColorCodes('&', kickMessage);
    }

    public static void kickOnlinePlayer() {
        //�����ʱ�䷶Χ�� �Ǿͼ������ģʽ �� �Ƿ���������ģʽ��
        if (ConfigManager.getPluginMode().equalsIgnoreCase("whitelist") && ConfigManager.getFunctionEnable()) {
            List listId = ConfigManager.getPlayerList();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!listId.contains(player.getPlayer().getName())) {
                    player.kickPlayer(ConfigManager.getKickMessage());
                }
            }
        } else if (ConfigManager.getPluginMode().equalsIgnoreCase("blacklist") && ConfigManager.getFunctionEnable()) {
            List listId = ConfigManager.getPlayerList();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (listId.contains(player.getPlayer().getName())) {
                    player.kickPlayer(ConfigManager.getKickMessage());
                }
            }
        }
    }

    public static void calculateCurfewTimestamp(String nextTimeTemp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date date = sdf.parse(nextTimeTemp);
            date.getTime();
        } catch (ParseException e) {
            AntiAddiction.instance.getLogger().warning("���鿪ʼʱ���Ƿ�Ϸ�");
            throw new RuntimeException(e);
        }
    }
}
