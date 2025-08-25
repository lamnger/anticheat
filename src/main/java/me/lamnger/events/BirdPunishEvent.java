package me.lamnger.events;

import me.lamnger.check.iCheck;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BirdPunishEvent extends Event implements Cancellable {
   private boolean cancelled;
   private final Player player;
   private final iCheck check;
   private static final HandlerList handlers = new HandlerList();

   public BirdPunishEvent(Player var1, iCheck var2) {
      super(true);
      this.player = var1;
      this.check = var2;
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



   public Player getPlayer() {
      return this.player;
   }



   public iCheck getCheck() {
      return this.check;
   }
}
