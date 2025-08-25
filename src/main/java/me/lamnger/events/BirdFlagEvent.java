package me.lamnger.events;

import me.lamnger.check.iCheck;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BirdFlagEvent extends Event implements Cancellable {
   private boolean cancelled;
   private final Player player;
   private final iCheck check;
   private final String info;
   private final long timestamp = System.currentTimeMillis();
   private static final HandlerList handlers = new HandlerList();

   public BirdFlagEvent(Player player, iCheck check, String info) {
      super(true);
      this.player = player;
      this.check = check;
      this.info = info;
   }

   public HandlerList getHandlers() {
      return handlers;
   }

   public static HandlerList getHandlerList() {
      return handlers;
   }

   public boolean isCancelled() {
      return this.cancelled;
   }

   public void setCancelled(boolean cancelled) {
      this.cancelled = cancelled;
   }

   public Player getPlayer() {
      return this.player;
   }

   public iCheck getCheck() {
      return this.check;
   }

   public String getInfo() {
      return this.info;
   }

   public long getTimestamp() {
      return this.timestamp;
   }
}