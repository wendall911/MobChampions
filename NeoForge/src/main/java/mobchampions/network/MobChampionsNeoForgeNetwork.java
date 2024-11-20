package mobchampions.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

import net.neoforged.neoforge.network.handling.IPayloadContext;

import mobchampions.util.FireworksHelper;

public class MobChampionsNeoForgeNetwork {

    public static final MobChampionsNeoForgeNetwork INSTANCE = new MobChampionsNeoForgeNetwork();

    public static MobChampionsNeoForgeNetwork getInstance() {
        return INSTANCE;
    }

    public void handleFireworksPacket(LaunchFireworksPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;

            if (level != null) {
                FireworksHelper.launchFireworks(level, msg.location());
            }
        });
    }

}
