package me.eclipsemaster.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserData {
    public static Map<UUID, UserData> dataMap = new ConcurrentHashMap<>(); // Changed to UUID key
    public final UUID uuid;
    private Player player;
    public CheckManager checkManager;

    public UserData(UUID uuid) {
        this.uuid = uuid;
        this.checkManager = new CheckManager(this);
        this.checkManager.initializeChecks();
    }

    public static UserData getData(UUID uuid) {
        return dataMap.computeIfAbsent(uuid, key -> new UserData(uuid));
    }

    public Player getPlayer() {
        if (player == null || !player.isOnline()) {
            this.player = Bukkit.getPlayer(uuid);
            if (this.player == null) {
                // Remove data if player is no longer online
                removeData(uuid);
                return null;
            }
        }
        return player;
    }

    public static void removeData(UUID uuid) {
        dataMap.remove(uuid);
    }

    // Additional utility methods
    public static UserData getData(Player player) {
        return getData(player.getUniqueId());
    }

    public static boolean hasData(UUID uuid) {
        return dataMap.containsKey(uuid);
    }
}