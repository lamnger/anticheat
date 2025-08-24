package me.eclipsemaster.check;

public interface iCheck {
    String getCategory();
    String getName();
    char getType();
    char getDisplayType();
    int getMaxVl();
    int getVl();
    double getMaxBuffer();
    boolean isExperimental();
    double getBufferDecay();
    double getBufferMultiple();
    int getMinimumVlToNotify();
    int getAlertInterval();
    double getBuffer();
    void setVl(int var1);
    boolean isPunishable();
    void setBuffer(double var1);
    String getDescription();
    String getComplexType();
    String getDisplayName();
}

