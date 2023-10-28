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
        // ע����������¼�������
        Bukkit.getPluginCommand("AntiAddiction").setExecutor(new Commands());
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);

        // ����Ĭ�������ļ�
        saveDefaultConfig();
        getLogger().info("����ɹ�����");

        // ����Ƿ��ڷ�����ʱ�䷶Χ��
        if (method.isWithinTimeRange(ConfigManager.getStartTime(), ConfigManager.getEndTime())) {
            // �����ʱ�䷶Χ�ڣ������ģʽ���Ƿ���������ģʽ��
            method.kickOnlinePlayer();
        }

        // ��������´�������ʼ��ʱ���
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date nowTime = sdf.parse(sdf.format(new Date()));
            Date nextTime = sdf.parse(ConfigManager.getStartTime());

            // �����ʼʱ���Ѿ���ȥ������һ���ʱ��
            if (nowTime.after(nextTime)) {
                nextTime.setTime(nextTime.getTime() + 24 * 60 * 60 * 1000); // һ��ĺ�����
            }

            long timeDifference = nextTime.getTime() - nowTime.getTime();

            // ʹ�� Bukkit ��������������
            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            taskId = scheduler.scheduleSyncDelayedTask(this, () -> {
                // ������ʱ��ִ�еķ���
                method.kickOnlinePlayer();
            }, timeDifference / 1000 * TICKS_PER_SECOND); // ��ʱ���ת��Ϊ ticks
            getLogger().info("���������� " + timeDifference / 1000 + " �뿪��������");
        } catch (ParseException e) {
            getLogger().warning("���������ļ���ʱ���ʽ�Ƿ����");
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // �ڲ������ʱȡ������
        Bukkit.getServer().getScheduler().cancelTask(taskId);
    }
}
