package mobchampions;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import mobchampions.network.LaunchFireworksPacket;
import mobchampions.network.MobChampionData;
import mobchampions.network.SyncMobChampionData;
import mobchampions.platform.Services;
import mobchampions.util.FireworksHelper;

public class MobChampionsClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(LaunchFireworksPacket.TYPE,
            ((payload, context) -> {
                Minecraft mc = Minecraft.getInstance();
                ClientLevel level = mc.level;

                mc.execute(() -> FireworksHelper.launchFireworks(level, payload.location()));
            })
        );
        ClientPlayNetworking.registerGlobalReceiver(SyncMobChampionData.TYPE,
            ((payload, context) -> {
                ClientLevel level = Minecraft.getInstance().level;
                MobChampionData data = payload.getMobChampionData();

                if (level != null) {
                    Entity entity = level.getEntity(data.entityId);

                    if (entity instanceof LivingEntity livingEntity) {
                        Services.PLATFORM.getMobChampionData(livingEntity).ifPresent(mobChampionData -> {
                            mobChampionData.setRank(data.rank);
                        });
                    }
                }
            })
        );
    }

}
