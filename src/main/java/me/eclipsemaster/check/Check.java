package me.eclipsemaster.check;

import me.eclipsemaster.data.UserData;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Check implements iCheck {
    private String name;
    private CheckType type;
    protected double vl;
    protected double buffer;

    private LinkedList<UserData> alertsEnabled = new LinkedList<>();
    protected final UserData data;

    private static TextComponent lbracket = new TextComponent("["),
            rbracket = new TextComponent("]"),
            prefix = new TextComponent("BirdAC"),
            space = new TextComponent(" "),
            message = new TextComponent("has failed"),
            infoPrefix = new TextComponent("Information: ");

    public Check(UserData data, String name, CheckType type) {
        this.data = data;
        this.name = name;
        this.type = type;
    }

    public void flag(String info, Object... objects) {
        String formattedInfo = String.format(info, objects);
        Player player = data.getPlayer();

        if (player == null) return;

        TextComponent playerName = new TextComponent(player.getName());
        playerName.setColor(net.md_5.bungee.api.ChatColor.WHITE);

        TextComponent checkName = new TextComponent(name);
        checkName.setColor(net.md_5.bungee.api.ChatColor.WHITE);

        TextComponent violationCount = new TextComponent(String.valueOf(Math.round(vl)));
        violationCount.setColor(net.md_5.bungee.api.ChatColor.RED);

        TextComponent alertMsg = new TextComponent(lbracket, prefix, rbracket, space, playerName,
                space, message, space, checkName, space, lbracket, violationCount, rbracket);

        TextComponent information = new TextComponent(ChatColor.translateAlternateColorCodes('&', formattedInfo));
        information.setColor(net.md_5.bungee.api.ChatColor.WHITE);

        alertMsg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new BaseComponent[] {infoPrefix, space, information}));

        alertMsg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + player.getName()));

        alertsEnabled.forEach(user -> {
            Player alertPlayer = user.getPlayer();
            if (alertPlayer != null && alertPlayer.isOnline()) {
                alertPlayer.spigot().sendMessage(alertMsg);
            }
        });
    }

    public abstract void receive(Object packet);
    public abstract void send(Object packet);

    static {
        lbracket.setColor(net.md_5.bungee.api.ChatColor.DARK_GRAY);
        rbracket.setColor(net.md_5.bungee.api.ChatColor.DARK_GRAY);
        prefix.setColor(net.md_5.bungee.api.ChatColor.DARK_RED);
        prefix.setBold(true);
        message.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        infoPrefix.setColor(net.md_5.bungee.api.ChatColor.GOLD);
    }

    // iCheck implementation
    @Override public String getCategory() { return type.name(); }
    @Override public String getName() { return name; }
    @Override public char getType() { return 'A'; }
    @Override public char getDisplayType() { return 'A'; }
    @Override public int getMaxVl() { return 20; }
    @Override public int getVl() { return (int) vl; }
    @Override public double getMaxBuffer() { return 10.0; }
    @Override public boolean isExperimental() { return false; }
    @Override public double getBufferDecay() { return 0.5; }
    @Override public double getBufferMultiple() { return 1.0; }
    @Override public int getMinimumVlToNotify() { return 5; }
    @Override public int getAlertInterval() { return 1; }
    @Override public double getBuffer() { return buffer; }
    @Override public void setVl(int vl) { this.vl = vl; }
    @Override public boolean isPunishable() { return true; }
    @Override public void setBuffer(double buffer) { this.buffer = buffer; }
    @Override public String getDescription() { return "BirdAC Check"; }
    @Override public String getComplexType() { return "STANDARD"; }
    @Override public String getDisplayName() { return name; }
}