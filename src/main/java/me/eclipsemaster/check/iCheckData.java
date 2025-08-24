package me.eclipsemaster.check;

import java.util.List;

public interface iCheckData {
    boolean isEnabled();
    boolean isPunishable();
    boolean isBroadcastPunishment();
    boolean isHotbarShuffle();
    boolean isRandomRotation();
    int getMaxViolations();
    int getAlertInterval();
    int getMinimumVlToNotify();
    int getMaxPing();
    int getMinimumVlToRandomlyRotate();
    int getMinimumVlToShuffleHotbar();
    double getMinimumTps();
    double getMaxBuffer();
    double getBufferDecay();
    double getBufferMultiple();
    List getPunishmentCommands();
    int getRandomRotationInterval();
}

