package ltd.dreamcraft.antiaddiction;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class AntiAddiction extends JavaPlugin {
    public static AntiAddiction instance;

    public AntiAddiction() {
        instance = this;
    }

    static int TICKS_PER_SECOND = 20;
    int taskId;

    @Override
    public void onEnable() {
        // 注册插件命令和事件监听器
        Bukkit.getPluginCommand("AntiAddiction").setExecutor(new Commands());
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);

        // 保存默认配置文件
        saveDefaultConfig();
        getLogger().info("插件成功加载");

        // 检查是否在防沉迷时间范围内
        if (method.isWithinTimeRange(ConfigManager.getStartTime(), ConfigManager.getEndTime())) {
            // 如果在时间范围内，检查插件模式和是否开启“宵禁模式”
            method.kickOnlinePlayer();
        }

        // 计算距离下次宵禁开始的时间差
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date nowTime = sdf.parse(sdf.format(new Date()));
            Date nextTime = sdf.parse(ConfigManager.getStartTime());

            // 如果开始时间已经过去，加上一天的时间
            if (nowTime.after(nextTime)) {
                nextTime.setTime(nextTime.getTime() + 24 * 60 * 60 * 1000); // 一天的毫秒数
            }

            long timeDifference = nextTime.getTime() - nowTime.getTime();

            // 使用 Bukkit 调度器安排任务
            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            taskId = scheduler.scheduleSyncDelayedTask(this, () -> {
                // 在宵禁时间执行的方法
                method.kickOnlinePlayer();
            }, timeDifference / 1000 * TICKS_PER_SECOND); // 将时间差转换为 ticks
            getLogger().info("服务器还有 " + timeDifference / 1000 + " 秒开启防沉迷");
        } catch (ParseException e) {
            getLogger().warning("请检查配置文件中时间格式是否错误");
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // 在插件禁用时取消任务
        Bukkit.getServer().getScheduler().cancelTask(taskId);
    }
}
