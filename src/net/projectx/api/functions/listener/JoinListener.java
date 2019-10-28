package net.projectx.api.functions.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static net.projectx.api.main.Data.tablist;

/**
 * Created by Yannick who could get really angry if somebody steal his code!
 * ~Yannick on 09.06.2019 at 15:10 o´ clock
 */
public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        tablist.setPlayer(e.getPlayer());
        tablist.setTablist(e.getPlayer().getUniqueId());
        e.setJoinMessage("§3» §8[§2+§8] " + e.getPlayer().getDisplayName());
        System.out.println("IP von " + e.getPlayer().getName() + ": " + e.getPlayer().getAddress().toString());
    }
}
