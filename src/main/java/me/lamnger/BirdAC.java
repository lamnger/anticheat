package me.lamnger;

import me.lamnger.Listener.BukkitListener;
import me.lamnger.Listener.PacketListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BirdAC extends JavaPlugin {
    public static BirdAC INSTANCE;
    public static final String PLUGIN_NAME = "BirdAC";
    public static final String PLUGIN_DISPLAY_NAME = "Bird Anti-Cheat";

    @Override
    public void onEnable() {
        INSTANCE = this;
        start();
        getLogger().info(PLUGIN_DISPLAY_NAME + " has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info(PLUGIN_DISPLAY_NAME + " has been disabled!");
        INSTANCE = null;
    }

    private void start() {
        // Register Bukkit events
        Bukkit.getPluginManager().registerEvents(new BukkitListener(), this);

        // Register PacketEvents if available
        registerPacketEvents();
    }

    private void registerPacketEvents() {
        try {
            Class.forName("com.github.retrooper.packetevents.PacketEvents");
            PacketListener.register();
            getLogger().info("PacketEvents registered successfully!");
        } catch (ClassNotFoundException e) {
            getLogger().warning("PacketEvents not found! Packet-based checks will be disabled.");
        }
    }
}