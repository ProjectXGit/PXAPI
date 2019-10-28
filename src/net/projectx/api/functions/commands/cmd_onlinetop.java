package net.projectx.api.functions.commands;

import net.projectx.api.util.Command.PXCommand;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;

import static net.projectx.api.functions.mysql.MySQL_User.getTop5;
import static net.projectx.api.main.Data.prefix;


/**
 * Created by Yannick who could get really angry if somebody steal his code!
 * ~Yannick on 14.06.2019 at 00:05 o´ clock
 */
public class cmd_onlinetop {
    @PXCommand(
            name = "onlinetop",
            permissions = "px.onlinetop",
            maxArgs = 0,
            minArgs = 0,
            usage = "/onlinetop (Player)"
    )
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            HashMap<String, Integer> top5 = getTop5();
            ArrayList<String> players = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            top5.forEach((player, time) -> {
                players.add(player);
            });
            builder.append(prefix + "Top 5:\n");

            for (int i = 0; i < players.size(); i++) {
                builder.append(prefix + i + ": §3" + players.get(i) + "§7 - §3" + toUnits(top5.get(players.get(i))));
            }
        } else {

        }
    }


    public static String toUnits(long zeit) {
        //System.out.println("zeit = " + zeit);
        long years = zeit / (60 * 60 * 24 * 7 * 31 * 12);
        //System.out.println("years = " + years);
        zeit = zeit % (60 * 60 * 24 * 7 * 31 * 12);
        //System.out.println("zeit = " + zeit);
        long months = zeit / (60 * 60 * 24 * 7 * 31);
        //System.out.println("months = " + months);
        zeit = zeit % (60 * 60 * 24 * 7 * 31);
        //System.out.println("zeit = " + zeit);
        long weeks = zeit / (60 * 60 * 24 * 7);
        //System.out.println("weeks = " + weeks);
        zeit = zeit % (60 * 60 * 24 * 7);
        //System.out.println("zeit = " + zeit);
        long days = zeit / (60 * 60 * 24);
        //System.out.println("days = " + days);
        zeit = zeit % (60 * 60 * 24);
        //System.out.println("zeit = " + zeit);
        long hours = zeit / (60 * 60);
        //System.out.println("hours = " + hours);
        zeit = zeit % (60 * 60);
        //System.out.println("zeit = " + zeit);
        long minutes = zeit / 60;
        //System.out.println("minutes = " + minutes);
        zeit = zeit % 60;
        //System.out.println("zeit = " + zeit);

        String message = "";
        if (years != 0) message = message + years + "y, ";
        if (months != 0) message = message + months + "M, ";
        if (weeks != 0) message = message + weeks + "w, ";
        if (days != 0) message = message + days + "d, ";
        if (hours != 0) message = message + hours + "h, ";
        if (minutes != 0) message = message + minutes + "m, ";
        if (zeit != 0) message = message + zeit + "s, ";

        return message;
    }
}
