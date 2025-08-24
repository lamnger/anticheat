package me.eclipsemaster.Listener;

import me.eclipsemaster.data.UserData;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.player.User;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PacketListener extends PacketListenerAbstract {
    public static ExecutorService thread = Executors.newFixedThreadPool(4);

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        User user = event.getUser();
        if (user == null) return;

        UUID uuid = user.getUUID();
        UserData data = UserData.getData(uuid);

        thread.execute(() -> {
            if (data != null && data.checkManager != null) {
                data.checkManager.onReceive(event);
            }
        });
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        User user = event.getUser();
        if (user == null) return;

        UUID uuid = user.getUUID();
        UserData data = UserData.getData(uuid);

        thread.execute(() -> {
            if (data != null && data.checkManager != null) {
                data.checkManager.onSend(event);
            }
        });
    }

    public static void register() {
        com.github.retrooper.packetevents.PacketEvents.getAPI().getEventManager().registerListener(new PacketListener());
    }
}