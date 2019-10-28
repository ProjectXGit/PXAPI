package net.projectx.api.main;

import net.projectx.api.functions.Tablist;
import org.bukkit.plugin.Plugin;

/**
 * ~Yannick on 19.09.2019 at 14:20 o´ clock
 */
public class Data {
    public static String symbol = "§8 » §7";
    public static String prefix = "§5Project§6X " + symbol;
    public static String noperm = prefix + "§4Dazu hast du keine Berechtigung!";
    public static String consoleonly = "§4Dieser Befehl kann nur von der Knsole ausgeführt werden!";

    public static Plugin instance;
    public static Tablist tablist;
}
