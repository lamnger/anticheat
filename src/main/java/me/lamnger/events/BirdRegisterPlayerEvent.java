package me.lamnger.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BirdRegisterPlayerEvent extends Event implements Cancellable {
   private boolean cancelled;
   private final Player player;
   private static final HandlerList handlers = new HandlerList();

   public BirdRegisterPlayerEvent(Player var1) {
      super(true);
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



   public void setCancelled(boolean var1) {
      this.cancelled = var1;
   }
}
