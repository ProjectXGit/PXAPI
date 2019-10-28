package net.projectx.api.functions;

import net.projectx.api.main.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.UUID;

import static net.projectx.api.main.Data.instance;

/**
 * Created by Yannick who could get really angry if somebody steal his code!
 * ~Yannick on 09.06.2019 at 11:47 o´ clock
 */
public class Tablist {
    static String header;
    static String footer;
    static String port;
    static String ranks;

    public final Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
    private Team a;
    private Team b;
    private Team c;
    private Team d;
    private Team e;
    private Team f;
    private Team g;
    private Team h;


    private HashMap<Player, String> rankColor = new HashMap<>();


    public Tablist() {
        header = "§8« " + Data.prefix.replaceAll(Data.symbol, "") + "§8 »";
        footer = "§7Server: §e" + instance.getDataFolder().getParentFile().getName();
        port = "\n§7Port: §e" + Bukkit.getPort();
        ranks = "§1Admin §4Mod §9Dev §cSup §2Builder\n §5YouTuber §3Premium";

        this.a = sb.getTeam("1a") == null ? sb.registerNewTeam("1a") : sb.getTeam("1a");
        this.b = sb.getTeam("2b") == null ? sb.registerNewTeam("2b") : sb.getTeam("2b");
        this.c = sb.getTeam("3c") == null ? sb.registerNewTeam("3c") : sb.getTeam("3c");
        this.d = sb.getTeam("4d") == null ? sb.registerNewTeam("4d") : sb.getTeam("4d");
        this.e = sb.getTeam("5e") == null ? sb.registerNewTeam("5e") : sb.getTeam("5e");
        this.f = sb.getTeam("6f") == null ? sb.registerNewTeam("6f") : sb.getTeam("6f");
        this.g = sb.getTeam("7g") == null ? sb.registerNewTeam("7g") : sb.getTeam("7g");
        this.h = sb.getTeam("8h") == null ? sb.registerNewTeam("8h") : sb.getTeam("8h");


        this.a.setPrefix("§7[§1Admin§7]§1 ");
        this.b.setPrefix("§7[§4Mod§7]§4 ");
        this.c.setPrefix("§7[§9Dev§7]§9 ");
        this.d.setPrefix("§7[§cSup§7]§c ");
        this.e.setPrefix("§7[§2Builder§7]§2 ");
        this.f.setPrefix("§5 ");
        this.g.setPrefix("§3 ");
        this.h.setPrefix("§b ");
    }


    public void setTablist(UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);

        if (Bukkit.getPlayer(uuid).hasPermission("aw.tab.serversettings")) {
            p.setPlayerListHeader(header + port);
        } else p.setPlayerListHeader(header);

        p.setPlayerListFooter(ranks + "\n" + footer);
    }


    public void setPlayer(Player p) {
        String team = "";
        team = "";
        if (p.hasPermission("px.color.admin")) {
            System.out.println(p.getName() + ": Admin");
            team = "1a";
        } else if (p.hasPermission("px.color.mod")) {
            System.out.println(p.getName() + ": Mod");
            team = "2b";
        } else if (p.hasPermission("px.color.dev")) {
            System.out.println(p.getName() + ": Dev");
            team = "3c";
        } else if (p.hasPermission("px.color.sup")) {
            System.out.println(p.getName() + ": Sup");
            team = "4d";
        } else if (p.hasPermission("px.color.builder")) {
            System.out.println(p.getName() + ": Builder");
            team = "5e";
        } else if (p.hasPermission("px.color.youtuber")) {
            System.out.println(p.getName() + ": YT");
            team = "6f";
        } else if (p.hasPermission("px.color.premium")) {
            System.out.println(p.getName() + ": Premium");
            team = "7g";
        } else {
            System.out.println(p.getName() + ": User");
            team = "8h";
        }
        if (!sb.getTeam(team).hasPlayer(Bukkit.getOfflinePlayer(p.getUniqueId())))
            sb.getTeam(team).addPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()));
        if (!sb.getTeam(team).hasEntry(p.getName())) sb.getTeam(team).addEntry(p.getName());
        rankColor.put(p, sb.getTeam(team).getPrefix());

        String name = "";
        name = String.valueOf(sb.getTeam(team).getPrefix()) + p.getName();
        System.out.println(name);
        ChatColor.translateAlternateColorCodes('§', name);

        p.setPlayerListName(name);
        p.setDisplayName(name);
        p.setCustomName(name);
        p.setCustomNameVisible(true);
        p.setScoreboard(sb);
        Bukkit.getScheduler().runTaskTimer(instance, () -> {
            Bukkit.getOnlinePlayers().forEach(pl -> pl.setScoreboard(sb));
        }, 1, 1);
    }

    public String getPrefix(Player p) {
        if (rankColor.containsKey(p)) {
            return rankColor.get(p);
        } else {
            String team;
            if (p.hasPermission("px.color.admin")) {
                team = "1a";
            } else if (p.hasPermission("px.color.mod")) {
                team = "2b";
            } else if (p.hasPermission("px.color.dev")) {
                team = "3c";
            } else if (p.hasPermission("px.color.sup")) {
                team = "4d";
            } else if (p.hasPermission("px.color.builder")) {
                team = "5e";
            } else if (p.hasPermission("px.color.youtuber")) {
                team = "6f";
            } else if (p.hasPermission("px.color.premium")) {
                team = "7g";
            } else {
                team = "8h";
            }
            return sb.getTeam(team).getPrefix();
        }
    }


}
