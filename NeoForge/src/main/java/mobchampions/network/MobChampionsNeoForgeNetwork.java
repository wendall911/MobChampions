package mobchampions.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import mobchampions.platform.Services;
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

    public void processMobChampionData(SyncMobChampionData payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            MobChampionData data = payload.getMobChampionData();

            if (level != null) {
                Entity entity = level.getEntity(data.entityId);

                if (entity instanceof LivingEntity livingEntity) {
                    Services.PLATFORM.getMobChampionData(livingEntity).ifPresent(mobChampionData -> {
                        mobChampionData.setRank(data.rank);
                        mobChampionData.setPrefix(data.prefix);
                        mobChampionData.setSuffix(data.suffix);
                    });
                }
            }
        });

    }

}
