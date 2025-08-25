package me.lamnger.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BirdDisablerAlertsEvent extends Event implements Cancellable {
   private boolean cancelled;
   private final Player player;
   private final long timestamp = System.currentTimeMillis();
   private static final HandlerList handlers = new HandlerList();





   public BirdDisablerAlertsEvent(Player var1) {
      this.player = var1;
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



   public Player getPlayer() {
      return this.player;
   }



   public long getTimestamp() {
      return this.timestamp;
   }



   public void setCancelled(boolean var1) {
      this.cancelled = var1;
   }
}
