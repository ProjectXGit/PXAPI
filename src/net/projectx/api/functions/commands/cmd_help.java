package net.projectx.api.functions.commands;

import net.projectx.api.main.Data;
import net.projectx.api.util.Command.PXCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Created by Yannick who could get really angry if somebody steal his code!
 * ~Yannick on 08.06.2019 at 21:02 o´ clock
 */
public class cmd_help {
    StringBuilder builder = new StringBuilder();

    @PXCommand(
            name = "help",
            aliases = {"h"},
            description = "Zeigt alle nützlich Kommandos!",
            permissions = "px.chat.help"
    )

    public void execute(CommandSender sender) {
        builder.append(Data.prefix + "§7§lHilfsübersicht:§r\n");
        add("color", "Zeigt alle Farben mit Code");
        add("gamemode", "Ändert deinen Spielmodus");
        add("onlinetime", "Zeigt deine Onlinetime");
        add("onlinetop", "Zeigt die Spieler mit der längsten Spielzeit!");
        sender.sendMessage(builder.toString());

    }


    private void add(String command, String usage) {
        builder.append("\n" + Data.symbol + "§e/" + command + "§8: §7 " + usage + ChatColor.RESET + "\n");
    }
}
