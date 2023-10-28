package ltd.dreamcraft.antiaddiction;

import java.util.List;

public class ConfigManager {
    public static String getPluginMode() {
        return AntiAddiction.instance.getConfig().getString("Setting.mode");
    }

    public static boolean getFunctionEnable() {
        return AntiAddiction.instance.getConfig().getBoolean("Setting.enable");
    }

    public static boolean isTimeLimitEnabled() {
        return AntiAddiction.instance.getConfig().getBoolean("Setting.time-limit");
    }

    public static int getDuration() {
        return AntiAddiction.instance.getConfig().getInt("Setting.duration");
    }

    public static String getTimeoutMessage() {
        return AntiAddiction.instance.getConfig().getString("Setting.timeout-message");
    }

    public static String getStartTime() {
        return AntiAddiction.instance.getConfig().getString("Setting.start");
    }

    public static String getEndTime() {
        return AntiAddiction.instance.getConfig().getString("Setting.end");
    }

    public static String getKickMessage() {
        return AntiAddiction.instance.getConfig().getString("Setting.kick-message");
    }

    public static List getPlayerList() {
        return AntiAddiction.instance.getConfig().getList("List");
    }
}
