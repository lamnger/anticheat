package me.lamnger.check.Apple;

import me.lamnger.check.Check;
import me.lamnger.check.CheckType;
import me.lamnger.data.UserData;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;

public class AimAA extends Check {
    private float suspiciousYaw;
    private long lastAttackTime;
    private int buffer;

    public AimAA(UserData data) {
        super(data, "Auto Aim", CheckType.COMBAT);
    }

    @Override
    public void receive(Object packet) {
        if (!(packet instanceof PacketReceiveEvent)) return;
        
        PacketReceiveEvent event = (PacketReceiveEvent) packet;
        
        // Only process flying packets that contain rotation
        if (event.getPacketType() == PacketType.Play.Client.PLAYER_FLYING ||
            event.getPacketType() == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION ||
            event.getPacketType() == PacketType.Play.Client.PLAYER_ROTATION) {
            
            WrapperPlayClientPlayerFlying flyingPacket = new WrapperPlayClientPlayerFlying(event);
            
            if (flyingPacket.hasRotationChanged()) {
                handleRotation(flyingPacket.getLocation().getYaw(), flyingPacket.getLocation().getPitch());
            }
        }
    }

    @Override
    public void send(Object packet) {
        // Not needed for this check
    }

    private void handleRotation(float yaw, float pitch) {
        // Skip check if not in combat
        if (System.currentTimeMillis() - lastAttackTime > 10000L) {
            return;
        }

        // Calculate yaw difference (normalized to 0-360)
        float yawDiff = Math.abs(yaw - data.getPlayer().getLocation().getYaw()) % 360.0F;
        
        // Check for suspicious patterns
        if (yawDiff > 0.8F && Math.round(yawDiff) == yawDiff) {
            if (yawDiff == suspiciousYaw) {
                // Same suspicious pattern repeated
                buffer += 2;
                
                if (buffer > 6) {
                    flag("Yaw consistency: %.1f (Buffer: %d)", yawDiff, buffer);
                    buffer /= 2; // Reduce buffer after flagging
                }
            } else {
                // New suspicious pattern
                suspiciousYaw = Math.round(yawDiff);
                buffer++;
            }
        } else {
            // Reset if pattern breaks
            suspiciousYaw = 0.0F;
            buffer = Math.max(0, buffer - 1);
        }
    }

    // This method should be called when the player attacks
    public void onAttack() {
        lastAttackTime = System.currentTimeMillis();
    }

    // Override iCheck methods with specific values for this check
    @Override
    public int getMaxVl() {
        return 15;
    }
    
    @Override
    public double getMaxBuffer() {
        return 10.0;
    }
    
    @Override
    public double getBufferDecay() {
        return 0.75;
    }
    
    @Override
    public String getDescription() {
        return "Detects suspicious aim patterns consistent with aim assistance";
    }
    
    @Override
    public String getComplexType() {
        return "ROTATION_CONSISTENCY";
    }
}