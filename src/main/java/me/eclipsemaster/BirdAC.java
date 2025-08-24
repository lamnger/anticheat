package me.eclipsemaster;

import me.eclipsemaster.Listener.BukkitListener;
import me.eclipsemaster.Listener.PacketListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BirdAC extends JavaPlugin {
    public static BirdAC INSTANCE;
    public static String namename = "anticheat";

    @Override
    public void onEnable() {
        INSTANCE = this;
        start();
        getLogger().info(namename+" has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info(namename+" has been enabled!");
        INSTANCE = null;
    }

    private void start(){
        Bukkit.getPluginManager().registerEvents(new BukkitListener(), this);

//packet evnts
        try {
            Class.forName("com.github.retrooper.packetevents.PacketEvents");
            PacketListener.register();
        } catch (ClassNotFoundException e) {
            getLogger().warning("PacketEvents not found! Packet-based checks will be disabled.");
        }
    }
}