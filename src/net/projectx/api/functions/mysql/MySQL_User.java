package net.projectx.api.functions.mysql;

import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;


/**
 * Created by Yannick who could get really angry if somebody steal his code!
 * ~Yannick on 11.06.2019 at 11:17 o´ clock
 */
public class MySQL_User {


    public static void createUser(UUID uuid) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = Date.from(Instant.now());
        MySQL.update("INSERT INTO `user` VALUES ('" + uuid.toString() + "', 0, 0, '" + sdf.format(time) + "', -1)");
    }

    public static void deleteUser(UUID uuid) {
        MySQL.update("DELETE * WHERE uuid = '" + uuid + "'");
    }

    public static boolean isUserExists(UUID uuid) {
        try {
            ResultSet rs = MySQL.querry("SELECT * FROM user WHERE uuid = '" + uuid + "'");
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<UUID> getUsers() {
        ArrayList<UUID> list = new ArrayList<>();
        try {
            ResultSet rs = MySQL.querry("SELECT uuid FROM `user` WHERE 1");
            UUID uuid;
            while (rs.next()) {
                list.add(UUID.fromString(rs.getString("uuid")));
            }
            return list;
        } catch (SQLException e) {
            return null;
        }
    }

    public static int getFruits(UUID uuid) {
        try {
            ResultSet rs = MySQL.querry("SELECT fruits FROM user WHERE uuid = '" + uuid + "'");
            while (rs.next()) {
                return rs.getInt("fruits");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void setFruits(UUID uuid, int fruits) {
        MySQL.update("UPDATE user SET fruits = " + fruits);
    }

    public static void addFruits(UUID uuid, int fruits) {
        setFruits(uuid, getFruits(uuid) + fruits);
    }

    public static void removeFruits(UUID uuid, int fruits) {
        setFruits(uuid, getFruits(uuid) - fruits);
    }

    public static void setPlaytime(UUID uuid, long playtime) {
        MySQL.update("UPDATE user SET playtime = " + playtime);
    }

    public static int getPlaytime(UUID uuid) {
        try {
            ResultSet rs = MySQL.querry("SELECT onlinetime FROM user WHERE uuid = '" + uuid + "'");
            while (rs.next()) {
                return rs.getInt("onlinetime");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static LocalDateTime getFirstTime(UUID uuid) {
        ResultSet rs = MySQL.querry("SELECT  firstJoin FROM user WHERE uuid = '" + uuid.toString() + "'");
        try {
            while (rs.next()) {
                return LocalDateTime.of(rs.getDate("firstJoin").toLocalDate(), rs.getTime("firstJoin").toLocalTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LocalDateTime getFirstTime(Player pp) {
        ResultSet rs = MySQL.querry("SELECT  firstJoin FROM `user` WHERE uuid = '" + pp.getUniqueId().toString() + "'");
        try {
            while (rs.next()) {
                return LocalDateTime.of(rs.getDate("firstJoin").toLocalDate(), rs.getTime("firstJoin").toLocalTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getLastTime(UUID uuid) {
        try {
            ResultSet rs = MySQL.querry("SELECT lastseen FROM `user` WHERE uuid = '" + uuid + "'");
            return rs.getLong("lastseen");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static long getLastTime(Player pp) {
        UUID uuid = pp.getUniqueId();
        try {
            ResultSet rs = MySQL.querry("SELECT lastseen FROM `user` WHERE uuid = '" + uuid + "'");
            while (rs.next()) {
                return rs.getLong("lastseen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void setLastTime(UUID uuid, long time) {
        MySQL.update("UPDATE `user` SET lastseen = " + time + " WHERE uuid = '" + uuid + "'");
    }

    public static void setLastTime(Player pp, long time) {
        UUID uuid = pp.getUniqueId();
        MySQL.update("UPDATE `user` SET lastseen = " + time + " WHERE uuid = '" + uuid + "'");
    }

    public static HashMap<String, Integer> getTop5() {
        HashMap<String, Integer> map = new HashMap<>();
        try {
            ResultSet rs = MySQL.querry("SELECT  name, playtime FROM user ORDER BY playtime DESC LIMIT 5");
            while (rs.next()) {
                map.put(rs.getString("name"), rs.getInt("playtime"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
