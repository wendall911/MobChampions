package mobchampions;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

import mobchampions.network.LaunchFireworksPacket;
import mobchampions.util.FireworksHelper;

public class MobChampionsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(LaunchFireworksPacket.TYPE,
            ((payload, context) -> {
                Minecraft mc = Minecraft.getInstance();
                ClientLevel level = mc.level;

                mc.execute(() -> FireworksHelper.launchFireworks(level, payload.location()));
            })
        );
    }

}
