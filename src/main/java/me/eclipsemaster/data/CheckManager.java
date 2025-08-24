package me.eclipsemaster.data;

import me.eclipsemaster.check.Check;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;

import java.util.ArrayList;
import java.util.List;

public class CheckManager {
    public final UserData data;
    public List<Check> checksList = new ArrayList<>();

    public CheckManager(UserData data) {
        this.data = data;
    }

    public void onReceive(PacketReceiveEvent event) {
        for (Check check : checksList) {
            try {
                check.receive(event);
            } catch (Exception e) {
                // Handle exception to prevent one check from breaking others
                e.printStackTrace();
            }
        }
    }

    public void onSend(PacketSendEvent event) {
        for (Check check : checksList) {
            try {
                check.send(event);
            } catch (Exception e) {
                // Handle exception to prevent one check from breaking others
                e.printStackTrace();
            }
        }
    }

    public void initializeChecks() {
        // Example: initCheck(new SpeedCheck(data));
        // TODO: Add actual checks here
    }

    protected void initCheck(Check check) {
        checksList.add(check);
    }

    // Utility methods
    public void addCheck(Check check) {
        checksList.add(check);
    }

    public void removeCheck(Check check) {
        checksList.remove(check);
    }

    public void clearChecks() {
        checksList.clear();
    }
}