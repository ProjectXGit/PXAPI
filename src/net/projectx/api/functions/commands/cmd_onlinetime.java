package net.projectx.api.functions.commands;

import net.projectx.api.util.Command.PXCommand;
import org.bukkit.entity.Player;

/**
 * ~Yannick on 23.09.2019 at 19:29 o´ clock
 */
public class cmd_onlinetime {
    @PXCommand(
            name = "onlinetime",
            aliases = {"ot"},
            permissions = "px.onlinetime"
    )
    public void perform(Player player) {
        player.sendMessage("TODO");
    }
}
