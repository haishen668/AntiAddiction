package ltd.dreamcraft.antiaddiction;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class Commands implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command c, String s, String[] args) {
        AntiAddiction.instance.reloadConfig();
        sender.sendMessage("��7[��aNoTime��7] ��f�����Բ��������.");
        return true;
    }
}
