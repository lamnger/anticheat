package me.lamnger.check;

import me.lamnger.data.UserData;
import net.md_5.bungee.api.chat.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public abstract class Check implements iCheck {
    private final String name;
    private final CheckType type;
    protected double violationLevel;
    protected double buffer;

    private final LinkedList<UserData> alertsEnabled = new LinkedList<>();
    protected final UserData data;

    // Constants for chat components
    private static final TextComponent LEFT_BRACKET = createTextComponent("[", net.md_5.bungee.api.ChatColor.DARK_GRAY);
    private static final TextComponent RIGHT_BRACKET = createTextComponent("]", net.md_5.bungee.api.ChatColor.DARK_GRAY);
    private static final TextComponent PREFIX = createTextComponent("BirdAC", net.md_5.bungee.api.ChatColor.DARK_RED, true);
    private static final TextComponent SPACE = createTextComponent(" ", null);
    private static final TextComponent MESSAGE = createTextComponent("has failed", net.md_5.bungee.api.ChatColor.GRAY);
    private static final TextComponent INFO_PREFIX = createTextComponent("Information: ", net.md_5.bungee.api.ChatColor.GOLD);

    public Check(UserData data, String name, CheckType type) {
        this.data = data;
        this.name = name;
        this.type = type;
    }

    public void flag(String info, Object... formatArgs) {
        String formattedInfo = String.format(info, formatArgs);
        Player player = data.getPlayer();

        if (player == null) {
            return;
        }

        TextComponent alertMessage = createAlertMessage(player, formattedInfo);

        sendAlertToEnabledUsers(alertMessage);
    }

    private TextComponent createAlertMessage(Player player, String formattedInfo) {
        TextComponent playerName = createTextComponent(player.getName(), net.md_5.bungee.api.ChatColor.WHITE);
        TextComponent checkName = createTextComponent(name, net.md_5.bungee.api.ChatColor.WHITE);
        TextComponent violationCount = createTextComponent(String.valueOf(Math.round(violationLevel)), net.md_5.bungee.api.ChatColor.RED);

        // Build the main alert message
        TextComponent alertMessage = new TextComponent(LEFT_BRACKET);
        alertMessage.addExtra(PREFIX);
        alertMessage.addExtra(RIGHT_BRACKET);
        alertMessage.addExtra(SPACE);
        alertMessage.addExtra(playerName);
        alertMessage.addExtra(SPACE);
        alertMessage.addExtra(MESSAGE);
        alertMessage.addExtra(SPACE);
        alertMessage.addExtra(checkName);
        alertMessage.addExtra(SPACE);
        alertMessage.addExtra(LEFT_BRACKET);
        alertMessage.addExtra(violationCount);
        alertMessage.addExtra(RIGHT_BRACKET);

        // Create hover information
        TextComponent information = createTextComponent(
                ChatColor.translateAlternateColorCodes('&', formattedInfo),
                net.md_5.bungee.api.ChatColor.WHITE
        );

        BaseComponent[] hoverText = new BaseComponent[] { INFO_PREFIX, SPACE, information };
        alertMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
        alertMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + player.getName()));

        return alertMessage;
    }

    private void sendAlertToEnabledUsers(TextComponent alertMessage) {
        alertsEnabled.forEach(user -> {
            Player alertPlayer = user.getPlayer();
            if (alertPlayer != null && alertPlayer.isOnline()) {
                alertPlayer.spigot().sendMessage(alertMessage);
            }
        });
    }

    private static TextComponent createTextComponent(String text, net.md_5.bungee.api.ChatColor color) {
        return createTextComponent(text, color, false);
    }

    private static TextComponent createTextComponent(String text, net.md_5.bungee.api.ChatColor color, boolean bold) {
        TextComponent component = new TextComponent(text);
        component.setColor(color);
        component.setBold(bold);
        return component;
    }

    public abstract void receive(Object packet);
    public abstract void send(Object packet);

    // iCheck implementation with improved readability
    @Override
    public String getCategory() {
        return type.name();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public char getType() {
        return 'A';
    }

    @Override
    public char getDisplayType() {
        return 'A';
    }

    @Override
    public int getMaxVl() {
        return 20;
    }

    @Override
    public int getVl() {
        return (int) violationLevel;
    }

    @Override
    public double getMaxBuffer() {
        return 10.0;
    }

    @Override
    public boolean isExperimental() {
        return false;
    }

    @Override
    public double getBufferDecay() {
        return 0.5;
    }

    @Override
    public double getBufferMultiple() {
        return 1.0;
    }

    @Override
    public int getMinimumVlToNotify() {
        return 5;
    }

    @Override
    public int getAlertInterval() {
        return 1;
    }

    @Override
    public double getBuffer() {
        return buffer;
    }

    @Override
    public void setVl(int vl) {
        this.violationLevel = vl;
    }

    @Override
    public boolean isPunishable() {
        return true;
    }

    @Override
    public void setBuffer(double buffer) {
        this.buffer = buffer;
    }

    @Override
    public String getDescription() {
        return "BirdAC Check";
    }

    @Override
    public String getComplexType() {
        return "STANDARD";
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    // Additional utility methods
    public void addAlertEnabled(UserData userData) {
        alertsEnabled.add(userData);
    }

    public void removeAlertEnabled(UserData userData) {
        alertsEnabled.remove(userData);
    }

    public void clearAlertsEnabled() {
        alertsEnabled.clear();
    }

    public boolean hasAlertsEnabled(UserData userData) {
        return alertsEnabled.contains(userData);
    }
}