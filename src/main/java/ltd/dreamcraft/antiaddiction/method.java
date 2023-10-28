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
     * 检查是否在时间范围内
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 在时间范围内返回true, 反之返回false
     */
    public static boolean isWithinTimeRange(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        try {
            ZoneId zone = ZoneId.of("Asia/Shanghai"); // 选择你所需的时区
            ZonedDateTime currentTime = ZonedDateTime.now(zone);
            Date startTimeParsed = sdf.parse(startTime);
            Date endTimeParsed = sdf.parse(endTime);

            // 转换当前时间为 LocalTime
            LocalTime currentTimeLocal = currentTime.toLocalTime();

            // 转换起始时间和结束时间为 LocalTime
            LocalTime startTimeLocal = startTimeParsed.toInstant().atZone(zone).toLocalTime();
            LocalTime endTimeLocal = endTimeParsed.toInstant().atZone(zone).toLocalTime();

            // 检查当前时间是否在时间范围内，处理跨越午夜的情况
            if (startTimeLocal.isBefore(endTimeLocal)) {
                // 不跨越午夜的情况
                return currentTimeLocal.isAfter(startTimeLocal) && currentTimeLocal.isBefore(endTimeLocal);
            } else {
                // 跨越午夜的情况
                return currentTimeLocal.isAfter(startTimeLocal) || currentTimeLocal.isBefore(endTimeLocal);
            }

        } catch (ParseException e) {
            return false; // 出现异常时，默认不在范围内
        }
    }


    public static String parseKickMessage(String kickMessage) {
        kickMessage = kickMessage.replace("%start%", ConfigManager.getStartTime());
        kickMessage = kickMessage.replace("%end%", ConfigManager.getEndTime());
        return ChatColor.translateAlternateColorCodes('&', kickMessage);
    }

    public static void kickOnlinePlayer() {
        //如果在时间范围内 那就检查插件的模式 和 是否开启“宵禁模式”
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
            AntiAddiction.instance.getLogger().warning("请检查开始时间是否合法");
            throw new RuntimeException(e);
        }
    }
}
