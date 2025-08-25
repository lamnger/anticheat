package me.lamnger.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BirdBanWaveStartEvent extends Event implements Cancellable {
   private boolean cancelled;
   private static final HandlerList handlers = new HandlerList();

   public BirdBanWaveStartEvent() {
      super(true);
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



   public void setCancelled(boolean var1) {
      this.cancelled = var1;
   }
}
