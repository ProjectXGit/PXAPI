package net.projectx.api.main;

import com.google.common.base.Joiner;
import net.projectx.api.functions.Tablist;
import net.projectx.api.functions.commands.*;
import net.projectx.api.functions.listener.ChatListener;
import net.projectx.api.functions.listener.JoinListener;
import net.projectx.api.functions.mysql.MySQL;
import net.projectx.api.util.Command.BukkitCommand;
import net.projectx.api.util.Command.PXCommand;
import net.projectx.api.util.executor.ThreadExecutor;
import net.projectx.api.util.executor.types.BukkitExecutor;
import net.projectx.api.util.menu.PopupMenuAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.projectx.api.main.Data.instance;
import static net.projectx.api.main.Data.tablist;

/**
 * ~Yannick on 18.09.2019 at 22:15 o´ clock
 */
public class PXAPI extends JavaPlugin {

    @Override
    public void onLoad() {
        super.onLoad();
        instance = this;
        ThreadExecutor.setExecutor(new BukkitExecutor());
        MySQL.connect();

    }

    @Override
    public void onEnable() {
        registerListener();
        registerCommands();
        tablist = new Tablist();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MySQL.close();
    }

    public void registerListener() {
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PopupMenuAPI(), this);
    }

    public void registerCommands() {
        register(cmd_color.class, this);
        register(cmd_gamemode.class, this);
        register(cmd_help.class, this);
        register(cmd_onlinetime.class, this);
        register(cmd_onlinetop.class, this);
    }

    public static void register(Class functionClass, JavaPlugin plugin) {
        try {
            register(functionClass, functionClass.newInstance(), plugin);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * Registriert Listener und Commands aus einer Klasse für ein Plugin
     *
     * @param function Object der Klasse welche registriert werden soll
     * @param plugin   Plugin für welches die Klasse registriert wird
     */
    public static void register(Object function, JavaPlugin plugin) {
        register(function.getClass(), function, plugin);
    }

    /**
     * Registriert Listener und Commands aus einer Klasse für ein Plugin
     *
     * @param functionClass Klasse welche registriert werden soll
     * @param function      Object der Klasse welche registriert werden soll
     * @param plugin        Plugin für welches die Klasse registriert wird
     */
    public static void register(Class functionClass, Object function, JavaPlugin plugin) {
        Method[] methods = functionClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(PXCommand.class))
                registerCommand(function, method, plugin);
        }

        if (function instanceof Listener) {
            Bukkit.getPluginManager().registerEvents((Listener) function, plugin);
        }
    }

    private static Map<String, Command> commandMap = new HashMap<>();
    private static List<Object[]> unavailableSubcommands = new ArrayList<>();

    private static void registerCommand(Object function, Method method, JavaPlugin plugin) {
        PXCommand cmd = method.getAnnotation(PXCommand.class);

        if (cmd.parent().length == 0) {
            BukkitCommand tBukkitCommand = new BukkitCommand(plugin, function, method, cmd);
            tBukkitCommand.register();

            commandMap.put(tBukkitCommand.getName(), tBukkitCommand);

            for (Object[] unavailableSubcommand : unavailableSubcommands) {
                Method oldMethod = (Method) unavailableSubcommand[1];
                PXCommand old = oldMethod.getAnnotation(PXCommand.class);
                if (old.parent()[0].equalsIgnoreCase(cmd.name()))
                    registerCommand(unavailableSubcommand[0], oldMethod, plugin);
            }
        } else {
            Command pluginCommand = commandMap.get(cmd.parent()[0]);
            if (pluginCommand == null) {
                unavailableSubcommands.add(new Object[]{function, method});
                Joiner.on(" ").join(cmd.parent() + " " + cmd.name(), cmd.parent()[0]);
            } else {
                if (pluginCommand instanceof BukkitCommand) {
                    ((BukkitCommand) pluginCommand).getProcessor().addSubCommand(cmd, function, method);
                } else {
                    Joiner.on(" ").join(cmd.parent() + " " + cmd.name(), cmd.parent()[0]);
                }
            }
        }
    }
}
