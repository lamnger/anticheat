package me.lamnger.Listener;

import me.lamnger.data.UserData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BukkitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Pre-load user data when player joins
        UserData.getData(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UserData.removeData(event.getPlayer().getUniqueId());
    }
}