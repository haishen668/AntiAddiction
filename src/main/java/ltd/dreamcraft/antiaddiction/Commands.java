package ltd.dreamcraft.antiaddiction;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class Commands implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command c, String s, String[] args) {
        AntiAddiction.instance.reloadConfig();
        sender.sendMessage("°Ï7[°ÏaNoTime°Ï7] °Ïf∑¿≥¡√‘≤Âº˛“—÷ÿ‘ÿ.");
        return true;
    }
}
